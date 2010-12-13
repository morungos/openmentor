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
package uk.org.openmentor.businesslogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.openmentor.evaluator.EvaluationScheme;

/**
 * One of the most implementation-dependant aspects of Openmentor is
 * the choice of terms to describe grades and categories.  Yet it
 * seems helpful to have such things as (immutable) classes.  This
 * factory aims to generate them from the text strings used in the
 * database to describe them.  To guard against corruption, only
 * strings which are known to the EvaluationScheme interface are
 * accepted; otherwise a null class will be returned.  It is (of
 * course) the responsibility of the calling class to check the
 * return, but this seems more helpful than raising an Exception.
 *
 * Note that the class is, in effect, completely static.  We use the
 * single non-static variable to enable Spring to wire in the
 * configuration information.  I guess there is a better way.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class DescriptorFactory {

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(DescriptorFactory.class);

    /**
     * The scheme to specify allowable grades and categories.  In
     * other words, the configuration information.
     */
    private static EvaluationScheme evaluationScheme;

    /**
     * Once initialised, this list will contain all the available
     * <code>Grade</code>s.
     */
    private static List<Grade> gradeList;

    /**
     * Once initialised, this list will contain all the available
     * <code>Categories</code>s, except for the uncategorised one.
     */
    private static List<Category> categoryList;

    /**
     * Stores a copy of the default <code>Category</code>.
     */
    private static Category defaultCategory;

    /**
     * Once initialised, this map will produce <code>Grade</code>s on
     * demand.
     */
    private static Map<String, Grade> gradeMap =
        new HashMap<String, Grade>();

    /**
     * Once initialised, this map will produce <code>Category</code>s on
     * demand.
     */
    private static Map<String, Category> categoryMap =
        new HashMap<String, Category>();

    /**
     * Creates a new <code>DescriptorFactory</code> instance.
     */
    private DescriptorFactory() {
        // This is never used; so we are always properly initialised.
    }

    /**
     * Creates what will in fact be the only
     * <code>DescriptorFactory</code> instance, and then initialises
     * the two maps which store the singleton Grade and Category
     * instances.
     *
     * @param scheme specifies allowable grades and categories.
     */
    public DescriptorFactory(EvaluationScheme scheme) {
        this.evaluationScheme = scheme;
        gradeList = new ArrayList<Grade>();
        categoryList = new ArrayList<Category>();
        List<String> bands = evaluationScheme.getBands();
        List<String> descriptors = evaluationScheme.getBandDescriptions();
        for (Iterator i = bands.iterator(), j = descriptors.iterator();
             i.hasNext() && j.hasNext();) {
            String grade = (String) i.next();
            String descriptor = (String) j.next();
            Grade g = new Grade(grade,descriptor);
            this.gradeMap.put(grade, g);
            this.gradeList.add(g);
        }

        List<String> categories = evaluationScheme.getCategories();
        descriptors = evaluationScheme.getCategoryDescriptions();
        for (Iterator i = categories.iterator(), j = descriptors.iterator();
             i.hasNext() && j.hasNext();) {
            String category = (String) i.next();
            String descriptor = (String) j.next();
            Category c = new Category(category,descriptor);
            this.categoryMap.put(category, c);
            this.categoryList.add(c);
            if (log.isDebugEnabled()) {
                log.debug("Adding " + category);
            }

        }
        // But we can also have uncategorised comments!
        String category = evaluationScheme.getDefaultCategory();
        String descriptor = evaluationScheme.getDefaultDescriptor();
        this.defaultCategory = new Category(category,descriptor);
        categoryMap.put(category, defaultCategory);
    }

    /**
     * Returns an existing <code>Grade</code> whose name is given.
     *
     * @param gradeName the given name;
     * @return the required <code>Grade</code>.
     */
    public static Grade getGrade(String gradeName) {
        Grade g = gradeMap.get(gradeName);
        if (g == null) {
            log.warn("Returning a null grade " + gradeName);
        }
        return g;
    }

    /**
     * Returns an existing <code>Category</code> whose name is given.
     *
     * @param categoryName the given name;
     * @return the required <code>Category</code>.
     */
    public static Category getCategory(String categoryName) {
        Category c = categoryMap.get(categoryName);
        if (c == null) {
            log.warn("Returning a null category " + categoryName);
        }
        return c;
    }

    /**
     * Returns an existing <code>Category</code> whose ruleCategory
     * name is given.
     *
     * @param name the given name;
     * @return the required <code>Category</code>.
     */
    public static Category getCategoryFromRuleCategoryName
        (final String name) {
        String categoryName = evaluationScheme.ruleCategoryAsCategory(name);
        return getCategory(categoryName);
    }

    /**
     * Get all known grades.
     *
     * @return a <code>List</code> of distinct grades.
     */
    public static final List<Grade> getGrades() {
        return gradeList;
    }

    /**
     * Get all known categories, excluding the default (uncategorized)
     * category.
     *
     * @return a <code>List</code> of categories.
     */
    public static final List<Category> getCategories() {
        return categoryList;
    }

    /**
     * Get all known categories, including the default (uncategorized)
     * category.
     *
     * @return a <code>List</code> of all the categories.
     */
    public static final List<Category> getAllCategories() {
        List l = new ArrayList<Category>(categoryList);
        l.add(defaultCategory);
        return l;
    }
}
