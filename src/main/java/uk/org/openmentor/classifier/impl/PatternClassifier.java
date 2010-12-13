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

package uk.org.openmentor.classifier.impl;

import uk.org.openmentor.classifier.Classifier;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

/**
 * @author Stuart Watt, Gordon Eccleston, Hannah Cumming, Sree Nuti
 *
 * <p>
 * This class contains the pattern classifier, which is the first of the main
 * classification engines in OpenMentor. It is not necessarily the only one,
 * and future engines will be needed as machine learning and other
 * classification techniques become more important.
 * </p>
 * <p>
 * The pattern classifier uses a set of regular expressions, each of which is
 * associated with some categories to include, and possibly others to exclude.
 * The algorithm essentially tries all the patterns one at a time, and then
 * builds up a list of matching (and non-matching) categories. When complete,
 * all the non-matching ones are removed, and the remaining matching categories
 * are turned into a comma-separated string.
 * </p>
 */
public class PatternClassifier implements Classifier {

    /**
     * The name of the classpath resource containing the rules.
     */
    private String sourceFile;

    /** List (in order) of rules to try for classification. */
    private List<Rule> vRules;

    /** Table of excluded categories. */
    private Set<String> vExclude = new HashSet<String>();

    /** Table of included categories. */
    private Set<String> vInclude = new HashSet<String>();

    /** Our standard logger. */
    private static Log log = LogFactory.getLog(PatternClassifier.class);

    /**
     * Constructor for the pattern classifier. Doesn't do much by
     * itself.
     */
    public PatternClassifier () {
    }

    /**
     * This simply sets the corresponding instance variable.  It is
     * called by Spring each time an applicationContext is set up.
     *
     * @param newSourceFile the required file name.
     */
    public final void setSourceFile(final String newSourceFile) {
        this.sourceFile = newSourceFile;
    }

    /**
     * Loads the set of rules from the classpath resource described in
     * the instance variable <code>sourceFile</code>.  This was
     * originally done in <code>setSourceFile</code>; the two tasks
     * were separated to avoid reloading rules frequently during units
     * tests, when they were never being used.  This is now called the
     * first time an attempt is made to use <code>vRules</code>.
     *
     * @exception Exception if the rule set is invalid.
     */
    public final void setRules() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Using source file: " + sourceFile);
        }
        InputStream in = this.getClass().getClassLoader().
                            getResourceAsStream(sourceFile);
        if (in == null) {
            throw new Exception("Can't open rule resource: " + sourceFile);
        }
        Reader theReader = new InputStreamReader(in);
        InputSource theInput = new InputSource(theReader);
        setRulesFromInput(theInput);
    }

    /**
     * Classifies a string and returns a comma-separated list of
     * matching categories. This is by far the most important part of
     * the classifier API.  The method uses whatever rules or training
     * are provided to classify the text that is passed, and returns a
     * comma-separated list of matching categories.
     *
     * @param theText        the text to be classified
     * @return                a comma-separated list of categories
     */
    public final Set<String> classifyString(final String theText) {
        if (vRules == null ) {
            vRules = new ArrayList<Rule>();
            try {
                setRules();
            } catch (Exception e) {
                log.warn("Could not load rules " + sourceFile);
            }
        }
        int theCount = vRules.size();
        vInclude.clear();
        vExclude.clear();
        for (int theIndex = 0; theIndex < theCount; theIndex++) {
            Rule theRule = (Rule) vRules.get(theIndex);
            if (theRule.tryRule(theText)) {
                theRule.applyRule();
            }
        }
        for (Iterator i = vExclude.iterator(); i.hasNext();) {
            vInclude.remove(i.next());
        }

        Set<String> theResult = new HashSet<String>();
        for (Iterator i = vInclude.iterator(); i.hasNext();) {
            theResult.add((String) i.next());
        }

        return theResult;
    }

    /**
     * Clears the current rule data.
     */
    public final void clearRules() {
        vRules.clear();
    }

    /**
     * Adds a rule to the current rule set. This is called as the XML is
     * parsed to process the rules in the file.
     *
     * @param theRule       the rule pattern text
     * @param theInclude    a comma-separated list of categories to include
     * @param theExclude    a comma-separated list of categories to exclude
     */
    private void addRule(final String theRule,
                         final String theInclude,
                         final String theExclude) {
        vRules.add(new Rule(theRule, theInclude, theExclude));
    }

    /**
     * Gets the text out of a DOM node. This is used in parsing the
     * rule set.
     *
     * @param theNode       the DOM node
     * @return              the text inside the node
     */
    private static String getText(final Node theNode) {
        NodeList nl = theNode.getChildNodes();
        Node ndCurrent;
        StringBuffer theResult = new StringBuffer();

        for (int i = 0; i < nl.getLength(); i++) {
            ndCurrent = nl.item(i);
            if (ndCurrent.getNodeType() == Node.TEXT_NODE) {
                theResult.append(ndCurrent.getNodeValue());
            } else if (ndCurrent.getNodeType() == Node.ELEMENT_NODE) {
                theResult.append(getText(ndCurrent));
            }
        }

        return theResult.toString();
    }

    /**
     * Sets the rule from a DOM element.
     * @param theElement    the element
     * @throws Exception    if there is a problem, such as parsing
     */
    private void setRuleFromElement(final Element theElement) throws Exception {
        String theID = theElement.getAttribute("id");
        if (theID == null) {
            throw new Exception("Invalid rule: no rule identifier set");
        }
        NodeList matches = theElement.getElementsByTagName("match");
        if (matches.getLength() != 1) {
            throw new Exception("Invalid rule: no pattern set");
        }
        String thePattern = getText(matches.item(0));
        matches = theElement.getElementsByTagName("categories");
        if (matches.getLength() != 1) {
            throw new Exception("Invalid rule: no categories set");
        }
        Element theCats = (Element) matches.item(0);
        String theInclude = theCats.getAttribute("include");
        String theExclude = theCats.getAttribute("exclude");

        addRule(thePattern, theInclude, theExclude);
    }

    /**
     * Sets the rule from the input in an InputStream.
     * @param theInput      the input stream
     * @throws Exception    if there is a problem, such as parsing
     */
    private void setRulesFromInput(final InputSource theInput)
                 throws Exception {
        Document document;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(theInput);

            NodeList all = document.getElementsByTagName("rule");
            int theNodes = all.getLength();
            for (int i = 0; i < theNodes; i++) {
                Element theElement = (Element) all.item(i);
                setRuleFromElement(theElement);
            }
        } catch (SAXParseException spe) {
            throw spe;
        }
    }

    /**
     * Class to implement a pattern rule. This is used within the
     * PatternClassifier; each rule consists of a regular expression
     * and a set of categories to include, and a second set to
     * exclude. The classifier runs through these in order, updating
     * current sets of categories.
     *
     * @author Stuart Watt
     */
    private class Rule {
        /** The matcher pattern for the rule. */
        private Matcher locPattern = null;

        /** A list of classes that are included. */
        private String[] locInclude = null;

        /** A list of classes that are excluded. */
        private String[] locExclude = null;

        /**
         * Constructs a rule, initialising the patterns accordingly.
         * @param pattern       the rule regular expression pattern
         * @param include       comma-separated categories to include
         * @param exclude       comma-separated categories to exclude
         */
        protected Rule (final String pattern,
                        final String include,
                        final String exclude) {
            Pattern thePattern = Pattern.compile(pattern,
                    Pattern.CASE_INSENSITIVE);
            locPattern = thePattern.matcher("");
            locInclude = include.split(",");
            locExclude = exclude.split(",");
        }

        /**
         * Checks to see if a rule can be used.
         * @param theComment        the comment to be used with the rule
         * @return                  true if the rule fires
         */
        protected boolean tryRule (final String theComment) {
            locPattern.reset(theComment);

            return locPattern.find();
        }

        /**
         * Applies a rule, updating the include and exclude lists.
         */
        protected void applyRule() {
            if (locInclude != null) {
                for (int i = 0; i < locInclude.length; i++) {
                    vInclude.add(locInclude[i]);
                }
            }
            if (locExclude != null) {
                for (int i = 0; i < locExclude.length; i++) {
                    vExclude.add(locExclude[i]);
                }
            }
        }
    }
}
