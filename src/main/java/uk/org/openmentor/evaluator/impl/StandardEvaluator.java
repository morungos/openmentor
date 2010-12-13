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
package uk.org.openmentor.evaluator.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.openmentor.evaluator.Evaluator;
import uk.org.openmentor.evaluator.EvaluationScheme;
import uk.org.openmentor.model.Submission;
import uk.org.openmentor.model.DataSheet;
import uk.org.openmentor.model.Comment;

/**
 * @author Stuart Watt
 *
 * This evaluates a set of submissions - strictly this belongs in the business
 * logic, but rather than placing it in that package, it is implemented here
 * as a component. Here, we return a feedback object for a submission that
 * can be presented in a view. More properties can be set to customise and
 * clarify the feedback to be presented.
 */

public class StandardEvaluator implements Evaluator {

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(StandardEvaluator.class);

    /**
     * Detail of the display categories to use when creating a
     * DataSheet Object; initialised froma configuration file.
     */
    private EvaluationScheme scheme;

    //    private Map properties = new HashMap();

    /**
     * A private <code>StandardEvaluator</code> to ensure this can't
     * be used without initialisation.
     */
    private StandardEvaluator() {
        // This can't be used; so we are always properly initialised.
    }

    /**
     * Creates a new <code>StandardEvaluator</code> instance,
     * typically created by Spring.
     *
     * @param evaluationScheme the essential scheme.
     */
    public StandardEvaluator(final EvaluationScheme evaluationScheme) {
        this.scheme = evaluationScheme;
    }

    /**
     * Sets a key which can be used to influence the way data is
     * extracted or summarised
     *
     * @param key
     * @param value
     */
     //    public void setProperty(String key, String value) {
     //        properties.put((String) key, (String) value);
     //    }

    /**
     * Analysis and returns feedback on a subset of the submission
     * data.
     *
     * @param submission the input on which feedback is required;
     * @return an Object which can be modelled for viewing.
     */
    public final Object getFeedback(final Submission submission) {
        if (log.isDebugEnabled()) {
            log.debug("Requested submission feedback");
        }

        DataSheet table = new DataSheet();
        table.setColumnLabels(Arrays.asList(new String[]
            {"Actual", "Ideal", "Comments"}));
        List<String> rowLabels = new ArrayList<String>();
        rowLabels.addAll(scheme.getCategoryDescriptions());
        rowLabels.add(scheme.getDefaultDescriptor());
        if (log.isDebugEnabled()) {
            for (Iterator i = rowLabels.iterator(); i.hasNext();) {
                log.debug(" " + (String) i.next());
            }
            log.debug(" is the rowLabels");
        }
        table.setRowLabels(rowLabels);

        // Now store the row number in the tables for each Category.
        List categories = scheme.getCategories();
        Map<String, Integer> rowNumber = new HashMap<String, Integer>();
        int rowCount = 0; // the row number of the corresponding categeory
        for (Iterator i = categories.iterator(); i.hasNext();) {
            String category = (String) i.next();
            rowNumber.put(category, new Integer(rowCount++));
        }
        String defaultCategory = scheme.getDefaultCategory();
        rowNumber.put(defaultCategory, new Integer(rowCount));
        /*  the concrete version of the above is:-
                  rowNumber.put("A", new Integer(0));
                  rowNumber.put("B", new Integer(1));
                  rowNumber.put("C", new Integer(2));
                  rowNumber.put("D", new Integer(3));
                  rowNumber.put("E", new Integer(4));
        */
        Set comments = submission.getComments();
        for (Iterator i = comments.iterator(); i.hasNext();) {
            Comment c = (Comment) i.next();

            Set<String> classes = c.getClasses();
            Set<String> newClasses = new HashSet<String>();
            for (Iterator j = classes.iterator(); j.hasNext();) {
                String ruleCategory = (String) j.next();
                String category = scheme.ruleCategoryAsCategory(ruleCategory);
                newClasses.add(category);
            }
            if (newClasses.isEmpty()) {
                newClasses.add(defaultCategory);
            }
            for (Iterator j = newClasses.iterator(); j.hasNext();) {
                String category = (String) j.next();

                // First increment the category counts
                int row = ((Integer) rowNumber.get(category)).intValue();
                Integer current = (Integer) table.getValue(0, row);
                if (current == null) {
                    current = new Integer(0);
                }
                table.setValue(0, row, new Integer(1 + current.intValue()));

                // Now add a new comment
                List<String> commentList = 
                    (List) table.getValue(2, row);
                if (commentList == null) {
                    commentList = new ArrayList<String>();
                }
                commentList.add(c.getText());
                table.setValue(2, row, (List) commentList);
            }
        }

        return table;
    }
}
