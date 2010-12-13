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

import uk.org.openmentor.dao.CourseInfoDAO;
import uk.org.openmentor.model.Course;
import uk.org.openmentor.model.Student;
import uk.org.openmentor.model.Tutor;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * This class interacts with Spring and Hibernate to save and
 * retrieve Course and Assignment objects.
 *
 * @author Stuart Watt
 */
public class CourseInfoDAOHibernate extends HibernateDaoSupport implements
        CourseInfoDAO {

    public final List getCourses() {
        return getHibernateTemplate().find(
                "from Course as course order by course.id");
    }

    public final Course getCourse(final String id) {
        return (Course) getHibernateTemplate().get(Course.class, id);
    }

    public final List getStudents(final Course course) {
        return getHibernateTemplate().find(
                "from Student as std order by std.lastName, std.firstName");
    }

    public final List getTutors(final Course course) {
        return getHibernateTemplate().find(
                "from Tutor as tut order by tut.lastName, tut.firstName");
    }

    public final Student getStudent(final Course course, final String id) {
        return (Student) getHibernateTemplate().get(Student.class, id);
    }

    public final Tutor getTutor(final Course course, final String id) {
        return (Tutor) getHibernateTemplate().get(Tutor.class, id);
    }
}
