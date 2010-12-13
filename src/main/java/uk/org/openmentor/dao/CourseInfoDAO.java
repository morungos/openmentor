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

import uk.org.openmentor.model.Course;
import uk.org.openmentor.model.Student;
import uk.org.openmentor.model.Tutor;

/**
 * Data access interface for the course information data source. This is
 * deliberately separate from the internal storage used by Open Mentor, and
 * will typically be connected to an external data store rather than an
 * internal one.
 * <p>
 * The main keys for the objects here, unusually for persistence systems, are
 * strings. This is because these will normally use existing institutional
 * identifiers, rather than newly constructed ones, and it is important that we
 * are able to work with and use these existing identifiers.
 *
 * @author Stuart Watt
 */
public interface CourseInfoDAO extends DAO {

    /**
     * Returns a List of {@link Course} objects, coresponding to all available
     * courses.
     *
     * @return a List if {@link Course} objects
     */
    List getCourses();

    /**
     * Returns a particular {@link Course} object, identified by its course
     * code.
     *
     * @param courseId
     *            the course code
     * @return the {@link Course} object
     */
    Course getCourse(String courseId);

    /**
     * Returns a List of all students assigned to a particular course.
     *
     * @param course
     *            the {@link Course} object
     * @return a List of {@link Student} objects
     */
    List getStudents(Course course);

    /**
     * Returns a List of all tutors assigned to a particular course.
     *
     * @param course
     *            the {@link Course} object
     * @return a List of {@link Tutor} objects
     */
    List getTutors(Course course);

    /**
     * Returns a particular student, given a course and student identifier.
     *
     * @param course
     *            the {@link Course} object
     * @param orgId
     *            the student identifier
     * @return a {@link Student} object
     */
    Student getStudent(Course course, String orgId);

    /**
     * Returns a particular tutor, given a course and tutor identifier.
     *
     * @param course
     *            the {@link Course} object
     * @param orgId
     *            the tutor identifier
     * @return a {@link Tutor} object
     */
    Tutor getTutor(Course course, String orgId);
}
