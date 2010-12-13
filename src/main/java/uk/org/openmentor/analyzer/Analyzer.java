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

package uk.org.openmentor.analyzer;

import java.util.Set;
import java.io.InputStream;

import uk.org.openmentor.extractor.Extractor;
import uk.org.openmentor.classifier.Classifier;
import uk.org.openmentor.model.Submission;

/**
 * Analyzer components are set up by Spring with a link to a classifier
 * and an extractor, and return a new Submission object completed with
 * the classified comments. This interface defines their standard
 * behaviour. The default analyzer should be enough for most usage.
 *
 * @author Stuart Watt
 */
public interface Analyzer {
    /**
     * Analyses a Word file, and returns the new submission identifier.
     *
     * @param assignmentId  the assignment identifier
     * @param students      a Set of students
     * @param tutors        a Set of tutors
     * @param grade         the grade
     * @param theInput      an InputStream containing the file submission
     * @return              the submission id for the newly created submission
     * @throws Exception    thrown when there are problems
     */
    Submission addSubmission(String assignmentId,
                             Set<String> students,
                             Set<String> tutors,
                             String grade,
                             InputStream theInput) throws Exception;

    /**
     * Setter for the comment extractor implementation.
     * @param extractor     the Extractor reference
     */
    void setExtractor(Extractor extractor);

    /**
     * Setter for the classifier implementation.
     * @param classifier    the Classifier reference
     */
    void setClassifier(Classifier classifier);
}
