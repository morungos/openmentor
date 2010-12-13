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
package uk.org.openmentor.service;

import java.util.List;

import uk.org.openmentor.model.Assignment;
import uk.org.openmentor.model.Submission;

/**
 * This interface is a web-friendly interface specifying how we use
 * Assignments.  the methods are similar to the corresponding ones in
 * the model package, but the corresponding parameters are strings.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public interface AssignmentManager {

    /**
     * Describe <code>getAssignments</code> method here.
     *
     * @param courseId a <code>String</code> value
     * @return a <code>List</code> value
     */
    List getAssignments(String courseId);

    /**
     * Describe <code>getAssignment</code> method here.
     *
     * @param assId a <code>String</code> value
     * @return an <code>Assignment</code> value
     */
    Assignment getAssignment(String assId);

    /**
     * Describe <code>saveAssignment</code> method here.
     *
     * @param ass an <code>Assignment</code> value
     */
    void saveAssignment(Assignment ass);

    /**
     * Describe <code>removeAssignment</code> method here.
     *
     * @param assId a <code>String</code> value
     */
    void removeAssignment(String assId);

    /**
     * Describe <code>getSubmission</code> method here.
     *
     * @param id a <code>String</code> value
     * @return a <code>Submission</code> value
     */
    Submission getSubmission(String id);
}
