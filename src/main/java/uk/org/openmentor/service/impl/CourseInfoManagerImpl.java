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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.org.openmentor.dao.CourseInfoDAO;
import uk.org.openmentor.model.Course;
import uk.org.openmentor.model.Student;
import uk.org.openmentor.model.Tutor;
import uk.org.openmentor.service.CourseInfoManager;


public class CourseInfoManagerImpl implements CourseInfoManager {
    private static Log log = LogFactory.getLog(CourseInfoManagerImpl.class);
    private CourseInfoDAO dao;

    public void setCourseInfoDAO(CourseInfoDAO dao) {
        this.dao = dao;
    }

    public List getCourses() {
        return dao.getCourses();
    }

    public Course getCourse(String courseId) {
        Course course = dao.getCourse(courseId);

        if (course == null) {
            log.warn("CourseId '" + courseId + "' not found in database.");
        }

        return course;
    }

    public List getStudents(Course course) {
        return dao.getStudents(course);
    }

    public List getTutors(Course course) {
        return dao.getTutors(course);
    }

    public final Student getStudent(final Course course, final String id) {
        Student student = dao.getStudent(course, id);
        if (student == null) {
            log.warn("StudentId '" + id + "' not found in database.");
        }
        return student;
    }

    public final Tutor getTutor(final Course course, final String id) {
        Tutor tutor = dao.getTutor(course, id);
        if (tutor == null) {
            log.warn("TutorId '" + id + "' not found in database.");
        }
        return tutor;
    }
}
