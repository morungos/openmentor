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
import java.sql.Blob;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class Submission implements Serializable {
    
    static final long serialVersionUID = 4755837863256857341L;

    /** Identifier field. */
    private int id;

    /** The file contents. */
    private Blob body;

    /** The file name. */
    private String filename;

    /** nullable persistent field */
    private String type;

    /** nullable persistent field */
    private String grade;

    /** persistent field */
    private Set<String> students;

    /** persistent field */
    private Set<String> tutors;

    /** persistent field */
    private Set<Comment> comments;

    /** full constructor */
    public Submission(final Blob body, final String filename,
                      final String type, final String grade,
                      final Set<String> students, 
                      final Set<String> tutors,
                      final Set<Comment> comments) {
        this.body = body;
        this.filename = filename;
        this.type = type;
        this.grade = grade;
        this.students = students;
        this.tutors = tutors;
        this.comments = comments;
    }

    /** default constructor */
    public Submission() {
    }

    /** minimal constructor */
    public Submission(final Set<String> students, 
                      final Set<String> tutors, 
                      final Set<Comment> comments) {
        this.students = students;
        this.tutors = tutors;
        this.comments = comments;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Blob getBody() {
        return this.body;
    }

    public void setBody(Blob body) {
        this.body = body;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Set<String> getStudents() {
        return this.students;
    }

    public void setStudents(Set<String> students) {
        this.students = students;
    }

    public Set<String> getTutors() {
        return this.tutors;
    }

    public void setTutors(Set<String> tutors) {
        this.tutors = tutors;
    }
        /** This and the next make the point that the field is
            arguably wrongly named. */
    public Set getTutorIds() {
        return this.tutors;
    }

    public void setTutorIds(Set<String> tutors) {
        this.tutors = tutors;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

}
