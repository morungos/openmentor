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

package uk.org.openmentor.dao;

import java.util.List;

import uk.org.openmentor.model.Assignment;
import uk.org.openmentor.model.Submission;

/**
 * Data access interface for the assignments data source. This
 * is the data source used primarily by Open Mentor to manage and
 * store its comments and classifications. These are mainly accessed
 * though the {@link Submission} class, which is managed by
 * Hibernate. Note that assignments and submissions, because they
 * do not make sense outside the context of Open Mentor (by and
 * large) use integer keys.
 *
 * @author Stuart Watt
 */
public interface AssignmentDAO extends DAO {
    /**
     * Returns a List of all assignments for a given course.
     * @param courseId        the course identifier
     * @return                a List of Assignment objects
     */
    List getAssignments(String courseId);

    /**
     * Returns a particular {@link Assignment}, identified by its
     * key.
     * @param id              the assignment identifiier
     * @return                the corresponding {@link Assignment}
     */
    Assignment getAssignment(int id);

    /**
     * Stores an assignment using some kind of a persistence
     * system.
     * @param ass             the {@link Assignment} to be stored
     */
    void saveAssignment(Assignment ass);

    /**
     * Deleted an assignment, identified by its key, from any
     * persistent storage.
     * @param assId           the assignment identifier
     */
    void removeAssignment(int assId);

    /**
     * Retrieves a particular {@link Submission} from the
     * persistence system.
     * @param id              the submission identifier
     * @return                the {@link Submission} object
     */
    Submission getSubmission(int id);
}
