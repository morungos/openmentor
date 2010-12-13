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

package uk.org.openmentor.analyzer.impl;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.openmentor.analyzer.Analyzer;
import uk.org.openmentor.classifier.Classifier;
import uk.org.openmentor.extractor.Extractor;
import uk.org.openmentor.model.Comment;
import uk.org.openmentor.model.Submission;

/**
 * Analyzer components are set up by Spring with a link to a classifier
 * and an extractor, and return a new Submission object completed with
 * the classified comments. This implements their standard
 * behaviour, and should be enough for most usage.
 *
 * @author Stuart Watt
 */

public class StandardAnalyzer implements Analyzer {

    /** Logger for log4j. */
    private static Log log = LogFactory.getLog(StandardAnalyzer.class);

    /** Reference to the Classifier component. */
    private Classifier classifier;

    /** Reference to the Extractor component. */
    private Extractor extractor;

    /**
     * Analyses a Word file, and returns the new submission identifier.
     *
     * @param assignmentId  IS CURRENTLY UNUSED
     * @param students      a Set of students
     * @param tutorIds        a Set of tutorIds
     * @param grade         the grade of assessment
     * @param theInput      an InputStream which will deliver the content
     *
     * @return the submission id for the newly created submission
     * @throws Exception if something goes wrong
     */
    public final Submission addSubmission(final String assignmentId,
                                          final Set<String> students,
                                          final Set<String> tutorIds,
                                          final String grade,
                                          final InputStream theInput)
            throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Analyzing input stream: " + theInput
                      + ", parameter grade: " + grade
                      + ", parameter assignmentId: " + assignmentId
                      + ", parameter students: " + students
                      + ", parameter tutorIds: " + tutorIds);
        }

        Submission sub = new Submission();
        sub.setStudents(students);
        sub.setTutorIds(tutorIds);
        sub.setGrade(grade);

        Set<Comment> commentSet = new HashSet<Comment>();

        Extractor ext = getExtractor();
        Classifier cls = getClassifier();

        ext.extract(theInput);
        Set comments = ext.getAnnotations();
        Iterator i = comments.iterator();
        while (i.hasNext()) {
            String comment = (String) i.next();
            if (log.isDebugEnabled()) {
                log.debug("Comment: " + comment);
            }
            Comment comm = new Comment();
            comm.setText(comment);
            comm.setClasses(cls.classifyString((String) comment));
            commentSet.add(comm);
        }

        sub.setComments(commentSet);

        if (log.isDebugEnabled()) {
            log.debug("Completed analysis of submission OK");
        }

        return sub;
    }

    /**
     * Sets the Classifier reference.
     * @param newClassifier    the new Classifier
     */
    public final void setClassifier(final Classifier newClassifier) {
        this.classifier = newClassifier;
    }

    /**
     * Returns the Classifier reference.
     * @return                  the Classifier
     */
    public final Classifier getClassifier() {
        return this.classifier;
    }

    /**
     * Sets the Extractor reference.
     * @param newExtractor     the new Extractor
     */
    public final void setExtractor(final Extractor newExtractor) {
        this.extractor = newExtractor;
    }

    /**
     * Returns the Extractor reference.
     * @return                  the Extractor
     */
    public final Extractor getExtractor() {
        return this.extractor;
    }
}
