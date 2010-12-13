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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.openmentor.model.Assignment;
import uk.org.openmentor.model.Course;
import uk.org.openmentor.model.Person;
import uk.org.openmentor.model.Principal;
import uk.org.openmentor.model.Submission;
import uk.org.openmentor.service.AssignmentManager;
import uk.org.openmentor.service.CourseInfoManager;
import uk.org.openmentor.service.ModelManager;

/**
 * The <code>ModelManagerImpl</code> provides more complicated model
 * interactions which use both assignment and course information
 * databases.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class ModelManagerImpl implements ModelManager {

    /**
     * Our usual looger.
     */
    private static Log log = LogFactory.getLog(ModelManagerImpl.class);

    /**
     * The manager that does part of the work.
     */
    private CourseInfoManager cmgr;

    /**
     * The other manager does the rest of the work.
     */
    private AssignmentManager amgr;

    /**
     * Give Spring a way to set the <code>CourseInfoManager</code>.
     *
     * @param mgr the manager.
     */
    public final void setCourseInfoManager(final CourseInfoManager mgr) {
        this.cmgr = mgr;
    }

    /**
     * Give Spring a way to set the <code>AssignmentManager</code>.
     *
     * @param mgr the manager.
     */
    public final void setAssignmentManager(final AssignmentManager mgr) {
        this.amgr = mgr;
    }

    /**
     * An <strong>active</strong> tutor is one who is associated with
     * at least one submission for a given course.  Each submission
     * may have more than one tutor associated with it and of course
     * there can be many submissions.  This method returns the set of
     * such tutors as a list.
     *
     * @param course the given course;
     * @return the list of active tutors.
     */
    public final List getActiveTutors(final Course course) {
        return getActivePrincipals(course, Principal.tutors);
    }

    /**
     * An <strong>active</strong> student is one who is associated
     * with at least one submission for a given course.  Each
     * submission may have more than one student associated with it
     * and of course there can be many submissions.  This method
     * returns the set of such students as a list.
     *
     * @param course the given course;
     * @return the list of active students.
     */
    public final List getActiveStudents(final Course course) {
        return getActivePrincipals(course, Principal.students);
    }

    public final Set<Submission> getSubmissions(final String courseId) {
        return  getSubmissions(cmgr.getCourse(courseId));
    }

    /**
     * Describe <code>getSubmissions</code> method here.
     *
     * @param course a <code>Course</code> value
     * @return a <code>Set</code> value
     */
    public final Set<Submission> getSubmissions(final Course course) {
        Set<Submission> submissions = new HashSet<Submission>();
        // First collect the ids as a set
        List<Assignment> assignments = amgr.getAssignments(course.getId());
        if (log.isDebugEnabled()) {
            if (assignments != null) {
                log.debug("Collecting " + assignments.size()
                          + " assignments: ");
            } else {
                log.debug("Collecting assignments: got null.");
            }
        }
        for (Assignment assignment : assignments){
            submissions.addAll(assignment.getSubmissions());
        }
        return submissions;
    }

    /**
     * An <strong>active</strong> principal (at present a student or
     * tutor) is one who is associated with at least one submission for
     * the given course.  Each submission may have more than one
     * principal associated with it and of course there can be many
     * submissions.  This method returns the set of such principals as
     * a list.
     *
     * @param course the given course,
     * @param p whether we want students or tutors,
     * @return the active principals.
     */
    private List getActivePrincipals(final Course course,
                                     final Principal p) {
        Set<String> personIds = new HashSet<String>();
        Set<Submission> submissions = getSubmissions(course);
        for (Submission submission : submissions) {
            if (p == Principal.students) {
                personIds.addAll(submission.getStudents());
            } else if (p == Principal.tutors) {
                personIds.addAll(submission.getTutors());
            } else {
                log.warn("Logic error: unknown principal.");
            }
        }
        // and then generate the principal objects.
        List<Person> participants = new ArrayList<Person>();
        for (Iterator i = personIds.iterator(); i.hasNext();) {
            String personId = (String) i.next();
            if (p == Principal.students) {
                participants.add(cmgr.getStudent(course, personId));
            } else if (p == Principal.tutors) {
                participants.add(cmgr.getTutor(course, personId));
            }
        }
        if (log.isDebugEnabled()) {
            if (participants != null) {
                log.debug("Found " + participants.size()
                          + " active " + p);
            } else {
                log.debug("Found no active participants");
            }
        }
        return participants;
    }

    /**
     * Returns a list of all submissions for the gibven course which
     * are associated with the given principal
     *
     * @param course a <code>Course</code> value
     * @param p a <code>Principal</code> value
     * @return a <code>List</code> value
     */
    private Set<Submission> 
        getSubmissionsForPrincipal(final Course course, 
                                   final String principalId,
                                   final Principal p) {
        Set<Submission> wanted = new HashSet<Submission>();
        Set<Submission> submissions = this.getSubmissions(course);
        for (Submission submission : submissions) {
            if (p == Principal.students) {
                for (String studentId : submission.getStudents()) {
                    if (studentId.equals(principalId)) {
                        wanted.add(submission);
                    }
                }
            } else if (p == Principal.tutors) {
                for (String tutorId : submission.getTutors()) {
                    if (tutorId.equals(principalId)) {
                        wanted.add(submission);
                    }
                }
            } else {
                log.warn("Logic error: unknown principal.");
            }
        }
        return wanted;
    }

    public Set<Submission> getTutorsSubmissions(String courseId, 
                                                 String tutorId) {
        Course course = cmgr.getCourse(courseId);
        return  getSubmissionsForPrincipal(course, tutorId,
                                           Principal.tutors);
    }

    public Set<Submission> getStudentsSubmissions(String courseId, 
                                                 String studentId) {
        Course course = cmgr.getCourse(courseId);
        return  getSubmissionsForPrincipal(course, studentId,
                                           Principal.students);
    }
}
