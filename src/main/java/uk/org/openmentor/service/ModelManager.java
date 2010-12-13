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
import java.util.Set;

import uk.org.openmentor.model.Course;
import uk.org.openmentor.model.Submission;

/**
 * This interface is a web-friendly interface specifying how we use
 * more complicated model constructs.  The AssignmentManger and
 * CourseInfoManger each look to a separate database.  The methods
 * here typically require access to both databases; this is done by
 * having access to each of these managers as instance variables, set
 * of course by Spring.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public interface ModelManager {

    /**
     * An <strong>active</strong> tutor is one who is associated with
     * a submission for a given course.  This returns the set of such
     * tutors.
     *
     * @param course the given course
     * @return the list of active tutors
     */
    List getActiveTutors(Course course);

    /**
     * An <strong>active</strong> student is one who is associated
     * with a submission for a given course.  This returns the set of
     * such students.
     *
     * @param course the given course
     * @return the list of active students
     */
    List getActiveStudents(Course course);

    Set<Submission> getSubmissions(Course course);

    Set<Submission> getSubmissions(String courseId);

    Set<Submission> getTutorsSubmissions(String courseId,
                                          String tutorId);

    Set<Submission> getStudentsSubmissions(String courseId,
                                            String studentId);

}
