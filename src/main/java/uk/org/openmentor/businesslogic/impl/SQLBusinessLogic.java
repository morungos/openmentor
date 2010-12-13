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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.openmentor.businesslogic.BusinessLogic;
import uk.org.openmentor.businesslogic.BusinessLogicException;
import uk.org.openmentor.evaluator.EvaluationScheme;
import uk.org.openmentor.model.DataBook;
import uk.org.openmentor.model.DataBookImpl;


/* Further big revisions by Stuart. It's still not brilliantly
 * structured, but at least the functionality is now more or
 * less complete. Note that we no longer do different reporting
 * calculations for different kinds of report, more or less
 * everything now at least looks similar.
 */

/**
 * Implementation of the business logic component, incorporated
 * as a Spring component, and with all the calculations of ideal
 * values done properly.
 *
 * @author Hassan Sheikh and Stuart Watt
 */
public class SQLBusinessLogic implements BusinessLogic {

    /** Used in SQL code to delimit comments. */
    private static final String SQL_COMMENT_SEPARATOR = "__SEP__";

    /** Logger for log4j. */
    private static Log log = LogFactory.getLog(SQLBusinessLogic.class);

    /** Number of categories. */
    private static final int CATEGORY_COUNT = 4;

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

    /** A list of the comment categories. */
    private List commentsCategories;

    /** A DataBook to store data for later display. */
    private DataBook dataBook;

    /** A reference to the SQLWrapper which handles the queries. */
    private SQLWrapper sqlwObj;

    /** The EvaluationScheme, set through dependency injection. */
    private EvaluationScheme evaluationScheme = null;

    /**
     * The java JDBC class name to use when accessing the database in
     * SQLWrapper.  It will be set by Spring and is just used to
     * initialise the SQLWrapper object.
     */
    private String driverClassName;

    /**
     * The url to use when accessing the database in SQLWrapper.  It
     * will be set by Spring and is just used to initialise the
     * SQLWrapper object.
     */
    private String url;

    /**
     * The name to use when accessing the database in SQLWrapper.  It
     * will be set by Spring and is just used to initialise the
     * SQLWrapper object.
     */
    private String username;

    /**
     * The password to use when accessing the database in SQLWrapper.
     * It will be set by Spring and is just used to initialise the
     * SQLWrapper object.
     */

    private String password;

    /**
     * Set the DriverClassName value.
     * @param newDriverClassName The new DriverClassName value.
     */
    public final void setDriverClassName(final String newDriverClassName) {
        this.driverClassName = newDriverClassName;
    }

    /**
     * Set the Url value.
     * @param newUrl The new Url value.
     */
    public final void setUrl(final String newUrl) {
        this.url = newUrl;
    }

    /**
     * Set the Username value.
     * @param newUsername The new Username value.
     */
    public final void setUsername(final String newUsername) {
        this.username = newUsername;
    }

    /**
     * Set the Password value.
     * @param newPassword The new Password value.
     */
    public final void setPassword(final String newPassword) {
        this.password = newPassword;
    }

    /** Constructs a new component for calculating the data for
     * charting.
     */
    public SQLBusinessLogic() {
        setCategoryCounts();
    }

    /**
     * Constructs a new component for calculating the data for
     * charting.
     *
     * @param newRequestType        the request type
     */
    public SQLBusinessLogic(final String newRequestType) {
        this();
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
     * Initialises the category counts.
     */
    private void setCategoryCounts() {
        this.commentsCategories = new ArrayList(CATEGORY_COUNT);
    }

    /**
     * Builds a DataBook for charting purposes.
     * @return  the DataBook instance
     * @throws BusinessLogicException if there is a calculation problem
     */
    public final DataBook buildDataBook() throws BusinessLogicException {
        // If possible this should use Hibernate and correct the
        // database problems.
        if (log.isDebugEnabled()) {
            log.debug("Building a plain databook");
        }

        dataBook = new DataBookImpl();
        sqlwObj = new SQLWrapper(driverClassName, url, username, password);

        //getting list of Bales categories from the database
        commentsCategories = getCommentsCategories();
        dataBook.setDataPoints(commentsCategories);

        //calling the appropriate function to prepare data
        //adding the actual comments to data collection
        calculateCommentsCounts(dataBook, commentsCategories);

        sqlwObj.closeDB();
        return dataBook;
    }

    /**
     * Returns a List of comment categories.
     * @return      the List of comment categories
     */
    private List getCommentsCategories() {
        List categories = evaluationScheme.getCategories();
        return categories;
    }

    /**
     * This method should store per-category values for the actual
     * comments selected according to the passed IDs, which may actually
     * vary in type according to the objects concerned. This is a key
     * part of the analysis system. The results should be stored
     * in the actualRangeValues vector.
     * @param databook a <code>DataBook</code> value
     * @param categories a <code>List</code> value
     * @exception BusinessLogicException if an error occurs
     */
    private synchronized void calculateCommentsCounts(final DataBook databook,
                                                      final List categories)
        throws BusinessLogicException {
        // use RequestType and ID values to cover all the cases
        // i.e. students, teachers, courses and assignments these
        // should be percentage values get ideal range from database
        // based on average marks

        List<Float> actualRangeValues = new ArrayList<Float>();
        List<Float> idealRangeValues = new ArrayList<Float>();
        List<String> actualCommentTexts = new ArrayList<String>();

        ResultSet rs = null;
        String theSQL = "";

        if (log.isDebugEnabled()) {
            log.debug("Setting actual comments for request type "
                      + requestType + " and id " + id);
        }

        if (this.requestType == null) {
            throw new BusinessLogicException("Missing ReportFor parameter");
        } else if (this.requestType.equals("tutor")) {
            theSQL = sqlwObj.sqlTeacherCommentsCount(courseId, id);
        } else if (this.requestType.equals("student")) {
            theSQL = sqlwObj.sqlStudentCommentsCount(courseId, id);
        } else if (this.requestType.equals("course")) {
            theSQL = sqlwObj.sqlCourseCommentsCount(id);
        } else if (this.requestType.equals("assignment")) {
            theSQL = sqlwObj.
                sqlAssignmentCommentsCount(Integer.parseInt(this.id));
        } else {
            throw new BusinessLogicException("Invalid ReportFor parameter:- "
                                             + this.requestType);
        }

        if (log.isDebugEnabled()) {
            log.debug("Calculating comment counts.  SQL query: " + theSQL);
        }

        // Build a band proportion table from the evaluationScheme
        // Note this isn't robust; it depends on their being a
        // declared proportion for each of the categories; better
        // would have been to set that proportion to zero.
        List bands = evaluationScheme.getBands();
        Map<String, Map> bandMap = new HashMap<String, Map>();
        for (Iterator i = bands.iterator(); i.hasNext();) {
            String band = (String) i.next();
            Map bandProportions = evaluationScheme.getBandProportions(band);
            bandMap.put(band, bandProportions);
            if (log.isDebugEnabled()) {
                log.debug("Adding band proportions for " + band);
            }
        }

        try {
            int totalComments = 0;
            Map<String, Number> actualCounts = new HashMap<String, Number>();
            Map<String, Number> idealCounts = new HashMap<String, Number>();
            Map<String, String> actualComments = new HashMap<String, String>();

            PreparedStatement stmt = sqlwObj.
                getConnection().prepareStatement(theSQL);
            rs = stmt.executeQuery();

            // getting records count to generate dynamic array later on
            while (rs.next()) {
                int theComments = rs.getInt("CommentsCounter");
                String theGrade = rs.getString("Grade");
                String theCategory = rs.getString("CCode");
                String theTexts = rs.getString("Comments");
                if (log.isDebugEnabled()) {
                    log.debug("Found: " + theComments + " x " + theCategory);
                }

                String texts = (String) actualComments.get(theCategory);
                if (texts == null) {
                    texts = theTexts;
                } else {
                    texts = texts + SQL_COMMENT_SEPARATOR + theTexts;
                }
                actualComments.put(theCategory, texts);

                float previousActual = 0;
                Number value = (Number) actualCounts.get(theCategory);
                if (value != null) {
                    previousActual = value.intValue();
                }
                actualCounts.put(theCategory,
                                 new Float(theComments + previousActual));
                totalComments = totalComments + theComments;

                Map gradeMap = (Map) bandMap.get(theGrade);

                List cats = evaluationScheme.getCategories();
                for (Iterator i = cats.iterator(); i.hasNext();) {
                    String cat = (String) i.next();
                    Number idealProportion = (Number) gradeMap.get(cat);

                    float previousIdeal = 0;
                    value = (Number) idealCounts.get(cat);
                    if (value != null) {
                        previousIdeal = value.intValue();
                    }
                    float add = idealProportion.floatValue() * theComments;
                    idealCounts.put(cat, new Float(add + previousIdeal));
                }
            }

            stmt.close();

            if (totalComments == 0) {
                totalComments = 1;
            }

            // Go through the categories in order, independent of the
            // SQL query results - added code to handle what happens
            // when there are no comments for a category.
            for (Iterator i = categories.iterator(); i.hasNext();) {
                String category = (String) i.next();
                Float actual = (Float) actualCounts.get(category);
                Float ideal = (Float) idealCounts.get(category);
                if (actual == null) {
                    actual = new Float(0);
                }
                if (ideal == null) {
                    ideal = new Float(0);
                }
                actualRangeValues.add(actual);
                idealRangeValues.add(ideal);
                actualCommentTexts.add(actualComments.get(category));
            }
            // Now take this list apart and build it as a list of
            // lists of comments - as we used to do in
            // RepoprtController.
            List<List<String>> commentsList = new ArrayList<List<String>>();
            for (Iterator i = actualCommentTexts.iterator(); i.hasNext();) {
                String comments = (String) i.next();
                List<String> values;
                if (comments == null) {
                    values = new ArrayList<String>();
                } else {
                    values = Arrays.asList(comments.
                                           split(SQL_COMMENT_SEPARATOR));
                }
                commentsList.add(values);
            }

            dataBook.setDataSeries("ActualRange",
                                    calculatePercentages(actualRangeValues));

            // Calculation of ideal values is a bit more complex,
            // largely because nobody really thought this through
            // until now. It should be a case of handling this,
            // probably by grade as well as by comment category.

            // Calculate the ideal counts from the percentage, and round
            List idealPercentages = calculatePercentages(idealRangeValues);
            List<Integer> normalizedIdealValues = new ArrayList<Integer>();
            for (Iterator i = idealPercentages.iterator(); i.hasNext();) {
                Number next = ((Number) i.next());
                float value = next.floatValue() * totalComments / 100;
                normalizedIdealValues.add(new Integer(Math.round(value)));
            }

            // Just round all the counts
            List<Integer> normalizedActualValues = new ArrayList<Integer>();
            for (Iterator i = actualRangeValues.iterator(); i.hasNext();) {
                Number next = ((Number) i.next());
                normalizedActualValues.add(
                        new Integer(Math.round(next.floatValue())));
            }
            dataBook.setDataSeries("IdealRange", idealPercentages);
            dataBook.setDataSeries("ActualCounts", normalizedActualValues);
            dataBook.setDataSeries("IdealCounts", normalizedIdealValues);

            //            dataBook.setDataSeries("ActualComments", actualCommentTexts);
            dataBook.setDataSeries("ActualComments", commentsList);

        } catch (SQLException ex) {
            if (log.isDebugEnabled()) {
                log.debug("SQL exception: " + ex.getMessage());
            }
            throw new BusinessLogicException("Database error: "
                                             + ex.getMessage());
        }

    }

    /**
     * Converts a List from values to percentages, rounded to Integer
     * objects.
     * @param theValues the List of values to be converted
     * @return  a new List of percentages
     */
    private List<Integer> calculatePercentages(final List theValues) {
        float theTotal = 0;
        List<Integer> result = new ArrayList<Integer>();

        for (Iterator i = theValues.iterator(); i.hasNext();) {
            Number next = ((Number) i.next());
            if (next == null) {
                continue;
            }
            theTotal += next.floatValue();
        }

        // To save us from division by zero, if there are no comments
        // pretend we have some. Note that all subdivisions must be
        // zero, so 0 / 1 = 0 and we get 0% in all categories.

        if (theTotal == 0) {
            theTotal = 1;
        }

        for (Iterator i = theValues.iterator(); i.hasNext();) {
            Number next = ((Number) i.next());
            float theValue = next.floatValue();
            result.add(new Integer(Math.round((theValue * 100) / theTotal)));
        }
        return result;
    }
}
