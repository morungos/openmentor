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

package uk.org.openmentor.model;

import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Course implements Serializable {
    
    static final long serialVersionUID = 3990141369555115154L;

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String courseTitle;

    /** persistent field */
    private Set students;

    /** persistent field */
    private Set tutors;

    /** full constructor */
    public Course(String id, String courseTitle, Set students, Set tutors) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.students = students;
        this.tutors = tutors;
    }

    /** default constructor */
    public Course() {
    }

    /** minimal constructor */
    public Course(String id, Set students, Set tutors) {
        this.id = id;
        this.students = students;
        this.tutors = tutors;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseTitle() {
        return this.courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Set getStudents() {
        return this.students;
    }

    public void setStudents(Set students) {
        this.students = students;
    }

    public Set getTutors() {
        return this.tutors;
    }

    public void setTutors(Set tutors) {
        this.tutors = tutors;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
