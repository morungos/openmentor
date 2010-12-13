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

package uk.org.openmentor.evaluator;

import java.util.List;
import java.util.Map;

import uk.org.openmentor.businesslogic.Category;
import uk.org.openmentor.businesslogic.Grade;
/**
 * The Evaluator interface defines the interaction with an evaluator
 * object, responsible for assessing and giving feedback at the
 * numerical level and at a qualitative level from the comment
 * analysis results.
 *
 * @author Stuart Watt
 */
public interface EvaluationScheme {

    /**
     * Returns a List of the grading bands, in order.
     *
     * @return a List of the bands
     */
    List<String> getBands();

    /**
     * Returns a List of the corresponding band descriptions, in order.
     *
     * @return a List of the band descriptions.
     */
    List<String> getBandDescriptions();

    /**
     * Returns a List of the categories, in order.
     *
     * @return a List of the categories
     */
    List<String> getCategories();

    /**
     * Get the name of the category used to represent strings which
     * have been uncategorised.
     *
     * @return the category name.
     */
    String getDefaultCategory();

    /**
     * Get the description of the category used to represent strings
     * which have been uncategorised.
     *
     * @return the category name.
     */
    String getDefaultDescriptor();

    /**
     * Returns a List of the corresponding catageory descriptions, in order.
     *
     * @return a List of the categories descriptions.
     */
    List<String> getCategoryDescriptions();

    /**
     * Returns a Map associating a grading category with a number between 0 and
     * 1, representing the proportion of each different mapping category for
     * that band.
     *
     * @param band      the given band name
     * @return a Map associating categories with proportions for a band
     */
    Map getBandProportions(String band);

    int getIdealProportion(Category c, Grade g);

    /**
     * Get the display category name of the category to which the
     * given rule category should be assigned.
     *
     * @param ruleCategory (rule) category to be aggregated;
     * @return the (display) category to which it belongs.
     */
    String ruleCategoryAsCategory(String ruleCategory);
}
