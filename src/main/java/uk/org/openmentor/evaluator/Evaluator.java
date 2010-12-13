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

import uk.org.openmentor.model.Submission;

/**
 * @author Stuart Watt
 *
 * The Evaluator interface represents the key business logic for
 * the Open Mentor system - taking a section of the submission
 * data, and returning a model object that can be used to
 * display feedback on those submissions.
 */

public interface Evaluator {

    /**
     * Sets a key which can be used to influence the way data is
     * extracted or summarised
     *
     * @param key
     * @param value
     */
    //  public void setProperty(String key, String value);

    /**
     * Analysis and returns feedback on a subset of the submission
     * data.
     *
     * @param submission the input on which feedback is required;
     * @return an Object which can be modelled for viewing.
     */
    Object getFeedback(Submission submission);
}
