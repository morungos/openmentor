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
package uk.org.openmentor.service.impl;

import java.util.List;

import uk.org.openmentor.dao.AssignmentDAO;
import uk.org.openmentor.model.Assignment;
import uk.org.openmentor.model.Submission;
import uk.org.openmentor.service.AssignmentManager;

/**
 * Describe class <code>AssignmentManagerImpl</code> here.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class AssignmentManagerImpl implements AssignmentManager {

    /**
     * The Data Access Object that does the work.
     *
     */
    private AssignmentDAO dao;

    /**
     * Standard mutator, typically called by Spring as part of the
     * construction process.
     *
     * @param newDao an <code>AssignmentDAO</code> value
     */
    public final void setAssignmentDAO(final AssignmentDAO newDao) {
        this.dao = newDao;
    }

    /**
     * The <code>getAssignments</code> method looks up all known
     * assignments with the given course identifier.
     *
     * @param newCourseId the identifier
     * @return the corresponding <code>List</code> of assignments.
     */
    public final List getAssignments(final String newCourseId) {
        return dao.getAssignments(newCourseId);
    }

    /**
     * The <code>getAssignment</code> method instantiates (from the
     * database of necessary) an assigment knowing only its
     * identifier.
     *
     * @param assId the assignment identifier
     * @return the corresponding <code>Assignment</code> value.
     */
    public final Assignment getAssignment(final String assId) {
        return dao.getAssignment(Integer.parseInt(assId));
    }

    /**
     * Saves the given assigment to the database.
     *
     * @param ass the <code>Assignment</code> to be saved.
     */
    public final void saveAssignment(final Assignment ass) {
        dao.saveAssignment(ass);
    }

    /**
     * Removes the given assignment from the database.
     *
     * @param assId the assignment to be removed.
     */
    public final void removeAssignment(final String assId) {
        dao.removeAssignment(Integer.parseInt(assId));
    }

    /**
     * Instantiates a given submission from the database.
     *
     * @param id the id of the submission.
     * @return the corresponding <code>Submission</code> value.
     */
    public final Submission getSubmission(final String id) {
        return dao.getSubmission(Integer.parseInt(id));
    }
}
