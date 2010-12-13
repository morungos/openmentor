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

package uk.org.openmentor.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.org.openmentor.dao.AssignmentDAO;
import uk.org.openmentor.model.Assignment;
import uk.org.openmentor.model.Submission;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * This class interacts with Spring and Hibernate to save and
 * retrieve Course and Assignment objects.
 *
 * @author Stuart Watt
 */
public class AssignmentDAOHibernate extends HibernateDaoSupport
    implements AssignmentDAO {

    /**
     * Our usual log.
     */
    private static Log log = LogFactory.getLog(AssignmentDAOHibernate.class);

    /**
     * Return the known assignments for a given course.
     *
     * @param courseId the given course
     * @return a <code>List</code> of the assignments
     */
    public final List getAssignments(final String courseId) {
        return getHibernateTemplate().
            find("from Assignment where course_id = ? order by assignmentTitle",
                 courseId);
    }

    /**
     * Get the <code>Assignment</code> corresponding to a given Id.
     *
     * @param id the given id
     * @return the corresponding <code>Assignment</code>.
     */
    public final Assignment getAssignment(final int id) {
        return (Assignment) getHibernateTemplate().
            get(Assignment.class, new Integer(id));
    }

    /**
     * Save a given <code>Assignment</code>.
     *
     * @param ass the <code>Assignment</code> to save.
     */
    public final void saveAssignment(final Assignment ass) {
        getHibernateTemplate().saveOrUpdate(ass);
        if (log.isDebugEnabled()) {
            log.debug("Assignment id set to: " + ass.getId());
        }
    }

    /**
     * Delete a given <code>Assignment</code>.
     *
     * @param id the <code>Assignment</code> to delete.
     */
    public final void removeAssignment(final int id) {
        Assignment ass = (Assignment) getHibernateTemplate()
            .load(Assignment.class, new Integer(id));
        getHibernateTemplate().delete(ass);
        if (log.isDebugEnabled()) {
            log.debug("Deleted assignment" + ass.getAssignmentTitle());
        }
    }

    /**
     * Get the <code>Submission</code> corresponding to a given id.
     *
     * @param id the given id.
     * @return the corresponding <code>Submission</code>.
     */
    public final Submission getSubmission(final int id) {
        return (Submission) getHibernateTemplate().
            get(Submission.class, new Integer(id));
    }
}
