/* =======================================================================
 * Copyright 2004-2006 The OpenMentor Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================================
 */
package uk.org.openmentor.businesslogic.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.openmentor.businesslogic.BusinessLogic;
import uk.org.openmentor.businesslogic.BusinessLogicException;
import uk.org.openmentor.businesslogic.Categorization;
import uk.org.openmentor.businesslogic.impl.CategorizationImpl;
import uk.org.openmentor.businesslogic.DescriptorFactory;
import uk.org.openmentor.businesslogic.Category;
import uk.org.openmentor.businesslogic.Grade;
import uk.org.openmentor.evaluator.EvaluationScheme;
import uk.org.openmentor.model.DataBook;
import uk.org.openmentor.model.DataBookImpl;
import uk.org.openmentor.model.Submission;
import uk.org.openmentor.service.ModelManager;

/**
 * This version replaces SQLBUsinessLogic (and so also SQLWrapper),
 * although the latter can be switched back in applicationContext.xml.
 * The intention was to provide a significantly different way of
 * doing things, which removes the very detailed SQL statements of the
 * alternative.  Of courtes this way has many more database hits, so
 * at some point could well prove not to scale.  So at present this is
 * only a demo version; there has been no opportunity for any form of
 * stress testing, and as such no attempt to start tuning the caching
 * startegy eg in hibernate, which <strong>may</strong> overcome some
 * of these problems.
 *
 * @author Ian Craw
 */
public class HibernateBusinessLogic implements BusinessLogic {

    /** Logger for log4j. */
    private static Log log = 
        LogFactory.getLog(HibernateBusinessLogic.class);

    /**
     * Current request type, to find out if the request is for a student,
     * teacher, course or an assignment. */
    private String requestType;

    /**
     * ID of student, teacher, course or assignment for whom report is
     * requested.
     */
    private String id;

    /** The current course identifier. */
    private String courseId;

    /** The EvaluationScheme, set through dependency injection. */
    private EvaluationScheme evaluationScheme = null;

    /** The Categorization, set through dependency injection. */
    private Categorization ctgz = null;

    /**
     * The <code>ModelManager</code> bean is set by Spring.
     */
    private ModelManager mmgr = null;

    /**
     * Usual <code>ModelManager</code> mutator.
     *
     * @param newModelManager the <code>ModelManager</code> value.
     */
    public final void setModelManager(final ModelManager
                                      newModelManager) {
        this.mmgr = newModelManager;
    }

    /** Constructs a new component for calculating the data for
     * charting.
     */
    public HibernateBusinessLogic() {
    }

    /**
     * Constructs a new component for calculating the data for
     * charting.
     *
     * @param newRequestType        the request type
     */
    public HibernateBusinessLogic(final String newRequestType) {
        setRequestType(newRequestType);
    }

    /**
     * Setter for the request type.
     * @param newRequestType        the request type
     */
    public final void setRequestType(final String newRequestType) {
        this.requestType = newRequestType;
    }

    /**
     * Setter for the course identifier.
     * @param newCourseId           the course identifier
     */
    public final void setCourseId(final String newCourseId) {
        this.courseId = newCourseId;
    }

    /**
     * Getter for the course identifier.
     * @return                      the course identifier
     */
    public final String getCourseId() {
        return this.courseId;
    }

    /**
     * Setter for the identifier of the object to calculate
     * chart data for.
     * @param newId                 the object identifier
     */
    public final void setId(final String newId) {
        this.id = newId;
    }

    /**
     * Setter for the evaluation scheme.
     * @param newEvaluationScheme   the evaluation scheme
     */
    public final void setEvaluationScheme(final EvaluationScheme
                                            newEvaluationScheme) {
        this.evaluationScheme = newEvaluationScheme;
    }

    /**
     * Setter for the categorization object;
     * @param caegorization   the categorization object.
     */
    public final void setCategorization(final Categorization
                                            categorization) {
        this.ctgz = categorization;
    }

    /**
     * Builds a DataBook for charting purposes.
     * @return  the DataBook instance
     */
    public final DataBook buildDataBook() {
        DataBook dataBook = new DataBookImpl();
        // The categorization really does want to be a local variable,
        // so why set it as an instance variable through dependency
        // injection and then risk forgetting to clear it?  In
        // contrast, I think one use case calls this method 3 times
        // running with the same arguments, so a saving is possible.
        ctgz.clear();
        ctgz.addComments(selectSubmissions()); 
        // Set up containers
        List<List<String>> comments = new ArrayList<List<String>>();
        Map<Category,Integer> actualCounts = new HashMap<Category,Integer>();
        int commentCount = 0;
        for (Category category: DescriptorFactory.getCategories()) {
            int n = ctgz.getCommentCount(category);
            commentCount += n;
            actualCounts.put(category,n);
            comments.add(ctgz.getComments(category));
            if (log.isDebugEnabled()) {
                log.debug("commentCount for " + category
                          + " is " + commentCount);
            }
        }
        Map<Grade,Integer> submissionCounts = ctgz.getSubmissionCounts();
        Map<Category,Integer> 
            idealCounts = 
            rescale(weightedIdealCounts(submissionCounts),commentCount);
        dataBook.setDataPoints(evaluationScheme.getCategories());
        dataBook.setDataSeries("IdealCounts", toList(idealCounts));
        dataBook.setDataSeries("IdealRange", 
                               toList(rescale(idealCounts,100)));
        dataBook.setDataSeries("ActualCounts", toList(actualCounts));
        dataBook.setDataSeries("ActualRange",
                               toList(rescale(actualCounts,100)));
        dataBook.setDataSeries("ActualComments", comments);
        return dataBook;
    }

    /**
     * Returns a list of Submissions which will be used to build the
     * report.  This is the *only* place the submissionId, id and
     * reportFor parameters are now to be used.
     *
     * @return a <code>List</code> value
     */
    private Set<Submission> selectSubmissions() {
        Set<Submission> set = new HashSet<Submission>();
        if (this.requestType == null) {
            log.warn("Report attempted with no requestType parameter");
        } else if (this.requestType.equals("tutor")) {
            set.addAll(mmgr.getTutorsSubmissions(courseId,id));
        } else if (this.requestType.equals("student")) {
            set.addAll(mmgr.getStudentsSubmissions(courseId,id));    
        } else if (this.requestType.equals("course")) {
            set.addAll(mmgr.getSubmissions(courseId));
        } else if (this.requestType.equals("assignment")) {
            // easy to do if it is needed.
        } else {
            log.warn("Invalid ReportFor parameter:- " + this.requestType);
        }
        return set;
    }

    /**
     * Returns a list of Submissions which will be used to build the
     * report.  This is the *only* place the submissionId, id and
     * reportFor parameters are now to be used.
     *
     * @return a <code>List</code> value
     */
    private Map<Category,Integer> 
        weightedIdealCounts(Map<Grade,Integer> count) {
        Map<Category, Integer> expected = new HashMap<Category, Integer>();
        for (Category category : DescriptorFactory.getCategories() ) {
            int sum = 0;
            for (Grade grade : DescriptorFactory.getGrades()) {
                sum += count.get(grade)
                    * evaluationScheme.getIdealProportion(category, grade);
            }
            expected.put(category,sum);
            if (log.isDebugEnabled()) {
                log.debug("weightedIdealCounts: " + category + ", " + sum);
            }
        }
        return expected;        
    }

   /**
     * We work internally with maps, indexed by Category; externally
     * we prefer a list arranged in the same Category order as that
     * provided by the DescriptorFactory; this methods does the
     * conversion.
     *
     * @param map the given map indexed by Categories;
     * @return the corresponding list.
     */
    private List<Integer> toList(Map <Category, Integer> map) {
        List l = new ArrayList<Integer>();
        for (Category category : DescriptorFactory.getCategories() ) {
            l.add(map.get(category));
        }
        return l;
    }

   /**
     * Rescale a collection of proportions, one for each Category so
     * they sum to a given total.  It is assumed that each proportion
     * is a non-negative integer.
     *
     * @param newTotal the sum of the proportions after rescaling;
     * @return the new collections of proportions as a Category - indexed map.
     */
    private Map<Category,Integer> 
        rescale(final Map<Category,Integer> proportions, final int newTotal) {
        Map<Category,Integer> newMap = new HashMap<Category,Integer>();
        float oldTotal = 0; 
        for (Category category : DescriptorFactory.getCategories() ) {
            oldTotal += proportions.get(category);
        }
        if (oldTotal == 0) { // So all counts are zero
            oldTotal = 1;
        }
        if (log.isDebugEnabled()) {
            log.debug("rescale --- oldTotal = " + oldTotal
                      + "; newTotal = " + newTotal);
        }
        for (Category category : DescriptorFactory.getCategories() ) {
            int oldValue = proportions.get(category);
            int newValue = Math.round((oldValue * newTotal) / oldTotal);
            if (log.isDebugEnabled()) {
                log.debug("rescale " + category + "; old "
                          + oldValue + " becomes " + newValue);
            }
            newMap.put(category, newValue) ;
        }
        return newMap;
    }
}
