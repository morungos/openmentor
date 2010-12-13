/* ====================================================================
 Copyright 2005     Openability Consulting and The Robert Gordon University

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ====================================================================
 */

package uk.org.openmentor.evaluator.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import uk.org.openmentor.evaluator.EvaluationScheme;
import uk.org.openmentor.businesslogic.Category;
import uk.org.openmentor.businesslogic.Grade;

/**
 * Reads details of current category and grade names etc from a
 * configuration file and makes them available to OpenMentor.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class FileEvaluationScheme implements EvaluationScheme {

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(FileEvaluationScheme.class);

    /**
     * The source of the configuration data that this class represents
     * to the rest of the application.
     */
    private String sourceFile;

    /**
     * The names of the bands or grades one of which is to be
     * assigned to a submission.
     */
    private List<String> bands = new ArrayList<String>();

    /**
     * Descriptions of the bands or grades listed in the same order as
     * the corresponding names.
     */
    private List<String> bandDescriptions = new ArrayList<String>();

    /**
     * The names of the (display) categories to be used.
     */
    private List<String> categories = new ArrayList<String>();

    /**
     * Descriptions of the (display) categories to be used, listed in
     * the same order as the corresponding names.
     */
    private List<String> categoryDescriptions = new ArrayList<String>();

    /**
     * Holds the expected proportion of each category of comment for
     * each grade or band.
     *
     */
    private Map<String, Map> bandMap = new HashMap<String, Map>();

    /**
     * A map translating rule categories to display categories.  Both
     * the rule set and the display use the cocept of a category in
     * there configuration file.  Several rule set categories can be
     * aggregated into one display category.  This map does the
     * translation.
     */
    private Map<String, String> translator = new HashMap<String, String>();

    /**
     * A category to represent strings which have been uncategorised;
     * set in the configuration file.
     *
     */
    private String defaultCategory;

    /**
     * A description of the category to represent strings which have
     * been uncategorised; set in the configuration file.  It might be
     * "Other Comments"
     *
     */
    private String defaultDescriptor;

    /**
     * Get the list of bands or grades.
     *
     * @return the bands
     */
    final public List<String> getBands() {
        return bands;
    }

     /**
     * Get the list of band or grade descriptions arranged in the same
     * oredr as the bands themselves.
     *
     * @return the band descriptions
     */
    final public List<String> getBandDescriptions() {
        return bandDescriptions;
    }

    /**
     * Get the list of (display) categories.
     *
     * @return the categories.
     */
    final public List<String> getCategories() {
        return categories;
    }

     /**
     * Get the list of (display) category descriptions arranged in the
     * same order as the categories themselves.
     *
     * @return the category descriptions
     */
    final public List<String> getCategoryDescriptions() {
        return categoryDescriptions;
    }

    /**
     * Get the name of the category used to represent strings which
     * have been uncategorised.
     *
     * @return the category name.
     */
    final public String getDefaultCategory() {
        return defaultCategory;
    }

    /**
     * Get the description of the category used to represent strings
     * which have been uncategorised.
     *
     * @return the category name.
     */
    final public String getDefaultDescriptor() {
        return defaultDescriptor;
    }

    /**
     * Get the display category name of the category to which the
     * given rule category should be assigned.
     *
     * @param ruleCategory (rule) category to be aggregated;
     * @return the (display) category to which it belongs.
     */
    final public String ruleCategoryAsCategory(final String ruleCategory) {
        return (String) translator.get(ruleCategory);
    }

    /**
     * Get the list of expected proportions of the various (display)
     * categories for the given band.
     *
     * @param band a <code>String</code> value
     * @return a <code>Map</code> value
     */
    final public Map getBandProportions(final String band) {
        return (Map) bandMap.get(band);
    }

    /**
     * These are the relative proportions expected in each of the
     * categories at the given grade.  To avoid compatibility problems
     * but still return an integer, the given proportion is scaled up
     * by 1000 and cast to an int.  At some point we should require
     * the proprortions to be specified in "mils" and drop this
     * internal factor.
     *
     * @param category a <code>Category</code> value
     * @param grade a <code>Grade</code> value
     * @return an <code>int</code> value
     */
    final public int getIdealProportion(final Category category,
                                        final Grade grade) {
        String band = grade.getLabel();
        String categoryLabel = category.getLabel();
        double p = ((Number) 
            bandMap.get(band).get(categoryLabel)).doubleValue();
        int n = (int) (p*1000);
        if (log.isDebugEnabled()) {
            log.debug("Ideal proportion for (" + grade.getLabel() 
                      + ", " + category + ") is " + n);
        }
        return n;
    }

    /**
     * Loads the configuration data from file and initialises the
     * internal maps.
     *
     * @param source the given input;
     * @exception Exception if an error occurs.
     */
    final public void setSourceFile(final String source) throws Exception {
        this.sourceFile = source;
        if (log.isDebugEnabled()) {
            log.debug("Using evaluator source file: " + this.sourceFile);
        }
        InputStream in = this.getClass().getClassLoader()
            .getResourceAsStream(sourceFile);
        if (in == null) {
            throw new Exception("Can't open evaluator resource: "
                                + sourceFile);
        }
        Reader theReader = new InputStreamReader(in);
        InputSource theInput = new InputSource(theReader);
        setRulesFromInput(theInput);
    }

    /**
     * Does the actual work of interpreting the xml input and setting
     * the internal maps.
     *
     * @param theInput an <code>InputSource</code> value
     * @exception Exception if an error occurs
     */
    private void setRulesFromInput(final InputSource theInput)
        throws Exception {
        Document document;
        NodeList all;
        int length;

        if (log.isDebugEnabled()) {
            log.debug("Setting evaluator rules");
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(theInput);

            // First of all, get the bands in order
            all = document.getElementsByTagName("band");
            length = all.getLength();
            for (int i = 0; i < length; i++) {
                Element elem = (Element) all.item(i);
                bands.add(elem.getAttribute("name"));
                bandDescriptions.add(elem.getAttribute("description"));
            }

            // Next, get the categories in order
            all = document.getElementsByTagName("category");
            length = all.getLength();
            for (int i = 0; i < length; i++) {
                Element elem = (Element) all.item(i);
                String category = elem.getAttribute("name");
                if (log.isDebugEnabled()) {
                    log.debug("Category read: " + category);
                }
                categories.add(category);
                String categoryDescription = elem.getAttribute("description");
                categoryDescriptions.add(categoryDescription);
                if (log.isDebugEnabled()) {
                    log.debug("Category description read: " 
                              + categoryDescription);
                }
                NodeList values = elem.getElementsByTagName("rulecategory");
                int count = values.getLength();
                if (count == 0) { // Both names are the same
                    translator.put(category, category);
                } else {
                    for (int j = 0; j < count; j++) {
                        Element rule = (Element) values.item(j);
                        String ruleCategory = rule.getAttribute("name");
                        translator.put(ruleCategory, category);
                    }
                }
            }

            // Handle the default category
            all = document.getElementsByTagName("defaultcategory");
            if (all.getLength() != 1) {
                log.warn("There should be a unique default descriptor.");
            }
            Element element = (Element) all.item(0);
            defaultCategory = element.getAttribute("name");
            defaultDescriptor = element.getAttribute("description");
            translator.put("",defaultCategory);
            // Now we can add the band proportion data
            all = document.getElementsByTagName("proportions");
            length = all.getLength();
            for (int i = 0; i < length; i++) {
                Element elem = (Element) all.item(i);
                String band = elem.getAttribute("band");

                NodeList values = elem.getElementsByTagName("proportion");
                int count = values.getLength();
                Map<String, Double> proportions = 
                    new HashMap<String, Double>();

                for (int j = 0; j < count; j++) {
                    Element prop = (Element) values.item(j);
                    String category = prop.getAttribute("category");
                    String value = prop.getAttribute("value");
                    double k = Double.parseDouble(value);
                    proportions.put(category, new Double(k));
                }

                bandMap.put(band, proportions);
            }
        } catch (SAXParseException spe) {
            throw spe;
        }
    }
}
