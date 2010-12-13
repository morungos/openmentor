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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.openmentor.businesslogic.Categorization;
import uk.org.openmentor.businesslogic.Category;
import uk.org.openmentor.businesslogic.DescriptorFactory;
import uk.org.openmentor.businesslogic.Grade;
import uk.org.openmentor.service.AssignmentManager;
import uk.org.openmentor.model.Comment;
import uk.org.openmentor.model.Submission;

/**
 * This interface defines the holder of the outcome of an application
 * of business logic, namely the result of categorising comments made
 * on (one of more) submissions.  Arguably the nub of this whole
 * application is to start with annotated text and produce the
 * comments or anotations partitioned into distinct categories,
 * characterised both by the way the comment has been classified, and
 * by the original grade assigned.  In this interface we model this as
 * a pair of tables, indexed by grade and catageory, one of which
 * provides a list of the comments in that particular cell of the
 * table; the other just provides the associated counts.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class CategorizationImpl implements Categorization {

    /**
     * Our usual logger.
     */
    private Log log = LogFactory.getLog( CategorizationImpl.class);

    /**
     * Avoid creating this each time, and then discarding it.
     */
    private static final List<String> emptyList = new ArrayList<String>();

    /**
     * Index first by <code>Grade</code> and then by
     * <code>Category</code> because a given submisssion is associated
     * with a single Grade but may have comments in all categories.
     **/
    private Map<Grade,Map> gradeMap = new HashMap<Grade,Map>();

    /**
     * Stores the number of submissions of each grade.
     **/
    private Map<Grade,Integer> submissionCount = new HashMap<Grade,Integer>();

    /**
     * Constructor sets submissions counts to zero.
     */
    CategorizationImpl() {
        for (Grade grade: DescriptorFactory.getGrades()) {
            submissionCount.put(grade,0);
        }
    }

    /**
     * Get the size of the comment list associated with the cell
     * (category, grade,), ensuring that 0 is returned when
     * appropriate.
     *
     * @param category one cell index;
     * @param grade    the other cell index;
     * @return the required count.
     */
    public int getCommentCount(Category category, Grade grade) {
        Map<Category,List> categoryMap = gradeMap.get(grade);
        if (categoryMap == null) {
            return 0;
        }
        List<String> commentList = categoryMap.get(category);
        if (commentList == null) {
            return 0;
        }
        return commentList.size();
    }
    

    /**
     * Calculate the total number of comments of a given catgeory,
     * summing over over all possible grades.
     *
     * @param category the given category;
     * @return the total number.
     */
    public int getCommentCount(Category category) {
        int sum = 0;
        for (Grade grade : DescriptorFactory.getGrades()) {
            sum += getCommentCount(category,grade);
        }
        return sum;
    }

    /**
     * Describe <code>getComments</code> method here.
     *
     * @param category a <code>Category</code> value
     * @return a <code>List</code> value
     */
    public final List<String> getComments(Category category) {
        List list = new ArrayList<String>();
        for (Grade grade : DescriptorFactory.getGrades()) {
            list.addAll(getComments(category,grade));
        }
        return list;
    }

    /**
     * Get the list of comments associated with the cell (category,
     * grade,), ensuring that an empty list is returned when
     * appropriate.
     *
     * @param category one cell index;
     * @param grade    the other cell index;
     * @return the required count.
     */
    public final List<String> getComments(Category category, Grade grade) {
        Map<Category,List> categoryMap = gradeMap.get(grade);
        if (categoryMap == null) {
            return emptyList;
        }
        List<String> commentList = categoryMap.get(category);
        if (commentList == null) {
            return emptyList;
        }
        return commentList;
    }

    /**
     * Go through the comments associated with the given submission,
     * each of which has zero or more associated rule categries, and
     * store them as items in the list associated with the cell
     * (grade,category).  The same comment may be stored in lists
     * associated with more than one cell, but won't be in the same
     * list twice.  Note that we treat the same actual text, from a
     * different comment (perhaps from a different submission, as
     * being different.
     *
     * @param submission a <code>Submission</code> value
     * @return the <code>Categorization</code> created or processed so
     *         the results from several submissions can be accumulated.
     */
    public Categorization addComments(Submission submission) {

        String gradeName = submission.getGrade();
        Grade g = DescriptorFactory.getGrade(gradeName);
        if (log.isDebugEnabled()) {
            log.debug("Found submission with Id " + submission.getId()
                      + " and grade " + gradeName);
        }
        // Increment the appropriate submission count
        submissionCount.put(g, submissionCount.get(g) + 1);
        HashMap<Category,List> categoryMap = (HashMap) gradeMap.get(g);
        if (categoryMap == null ) { 
            categoryMap = new HashMap<Category,List>();
        }
        Set<Comment> comments = submission.getComments();
        for (Comment comment: comments) {
            Set<String> ruleCategories = comment.getClasses();
            // Different ruleCategories may generate the same category;
            // avoid adding the comment twice to the same list.
            Set<Category> categorySet = new HashSet<Category>();
            for (String ruleCategoryName: ruleCategories) {
                Category category = DescriptorFactory.
                    getCategoryFromRuleCategoryName(ruleCategoryName);
                List<String> commentList = categoryMap.get(category);
                if (commentList == null) { //First such comment)
                    commentList = new ArrayList<String>();
                }
                if (categorySet.contains(category)) {
                    continue; // do nothing more this loop
                }
                categorySet.add(category);
                commentList.add(comment.getText());
                if (log.isDebugEnabled()) {
                    String tmp = comment.getText();
                    String commentText;
                    if (tmp.length() > 30 ) {
                        commentText = tmp.substring(0,27) + "...";
                    } else {
                        commentText = tmp;
                    }
                    log.debug("Adding \"" + commentText
                              + "\" to (" + g + "," + category + ")");
                }
                categoryMap.put(category,commentList);
            }
        }
        gradeMap.put(g,categoryMap);
        return this;
    }

    /**
     * Convenience method to add a set of Comments to this.
     *
     * @param comments the set of comments to be added.
     * @return a <code>Categorization</code> value
     */
    public Categorization addComments(Set<Submission> submissions) {
        for (Submission submission : submissions) {
            this.addComments(submission);
        }
        return this;
    }

    /**
     * Set all the internal data to initial values.
     *
     */
    public void clear() {
        gradeMap.clear();
        for (Grade grade: DescriptorFactory.getGrades()) {
            submissionCount.put(grade,0);
        }
    }

    /**
     * Return a map which gives the number of submissions of each
     * grade which have been included in the categorization.
     *
     * @return the map;
     */
    public final Map<Grade,Integer> getSubmissionCounts() {
        if (log.isDebugEnabled()) {
            log.debug("submissionCount is" + submissionCount);
        }
        return submissionCount;
    }

    /**
     * This is here primarily for use in debug statements.
     *
     * @return information about the current contents of the
     *         categorization.
     */
    public String toString() {
        StringBuilder b = new StringBuilder();
        List<Grade> grades = DescriptorFactory.getGrades();
        b.append("\n");
        for (Grade g : grades) {
            b.append("Grade ");
            b.append(g.getLabel());
            b.append(" has");
            List<Category> categories = DescriptorFactory.getAllCategories();
            boolean firstTime = true;
            for (Category c : categories) {
                b.append(firstTime ? " " : ", ");
                b.append(this.getCommentCount(c,g));
                b.append(" in ");
                b.append(c.getLabel());
                firstTime = false;
            }
            b.append(".\n");
        }
        return b.toString();
    }

}
