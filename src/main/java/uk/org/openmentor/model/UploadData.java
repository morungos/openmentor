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

import java.util.Set;
import java.util.List;
import uk.org.openmentor.businesslogic.Grade;

/**
 * This class is designed solely to be a formBackingObject for the
 * selectAssignmentForm managed by the FileUploadController.  It is
 * similar to a submission, but it is designed for selection; so it
 * contains both a list of possible assignments and the particular
 * assignment selected for upload etc.  Any such object only makes
 * sense once a particular course has been chosen.  There is no reason
 * to have a fileName field; as far as I can see there is no way to
 * use one on the corresponding form.  Any such field has to be
 * reselected each time, since the file selection control on the HTML
 * form seems not to allow a default value.
 *
 * @author <a href="mailto:Ian,Craw@openability.co.uk>Ian Craw</a>
 *
 */
public class UploadData {

    /**
     * <code>assignments</code> consists of all known assignments for
     * the course.
     *
     */
    private List assignments;

    /**
     * <code>students</code> consists of all known students on
     * the course.
     *
     */
    private Set students;

    /**
     * <code>tutors</code> consists of all known tutors on
     * the course.
     *
     */
    private Set tutors;

    /**
     * A list of all possible Grades.
     */
    private List grades;

    /**
     * The particular <code>assignment</code> for which the
     * submission was prepared.
     *
     */
    private Assignment assignment = new Assignment();

    /**
     * The <code>grade</code> assigned to this submission.
     *
     */
    private Grade grade = new Grade();

    /**
     * The particular <code>student</code> who prepared this
     * submission.
     *
     */
    private Student student = new Student();

    /**
     * The particular <code>tutor</code> who marked this
     * submission.
     *
     */
    private Tutor tutor = new Tutor();

    /**
     * The contents (as bytes) of the submission.
     *
     */
    private byte[] file;

    /**
     * The associated <code>submission</code> that results form
     * providing these data..
     *
     */
    private Submission submission;

    /**
     * Get the Assignments value.
     * @return the Assignments value.
     */
    public final List getAssignments() {
        return assignments;
    }

    /**
     * Set the Assignments value.
     * @param newAssignments The new Assignments value.
     */
    public final void setAssignments(final List newAssignments) {
        this.assignments = newAssignments;
    }

    /**
     * Get the Students value.
     * @return the Students value.
     */
    public final Set getStudents() {
        return students;
    }

    /**
     * Set the Students value.
     * @param newStudents The new Students value.
     */
    public final void setStudents(final Set newStudents) {
        this.students = newStudents;
    }

    /**
     * Get the Tutors value.
     * @return the Tutors value.
     */
    public final Set getTutors() {
        return tutors;
    }

    /**
     * Set the Tutors value.
     * @param newTutors The new Tutors value.
     */
    public final void setTutors(final Set newTutors) {
        this.tutors = newTutors;
    }

    /**
     * Set the Grades value.
     * @param newGradess The new Grades value.
     */
    public final void setGrades(final List newGrades) {
        this.grades = newGrades;
    }

    /**
     * Get the Grades value.
     * @return  The list of possible Grades.
     */
    public final List getGrades() {
        return grades;
    }

    /**
     * Get the Assignment value.
     * @return the Assignment value.
     */
    public final Assignment getAssignment() {
        return assignment;
    }

    /**
     * Set the Assignment value.
     * @param newAssignment The new Assignment value.
     */
    public final void setAssignment(final Assignment newAssignment) {
        this.assignment = newAssignment;
    }

    /**
     * Get the Grade value.
     * @return the Grade value.
     */
    public final Grade getGrade() {
        return grade;
    }

    /**
     * Set the Grade value.
     * @param newGrade The new Grade value.
     */
    public final void setGrade(final Grade newGrade) {
        this.grade = newGrade;
    }

    /**
     * Get the Student value.
     * @return the Student value.
     */
    public final Student getStudent() {
        return student;
    }

    /**
     * Set the Student value.
     * @param newStudent The new Student value.
     */
    public final void setStudent(final Student newStudent) {
        this.student = newStudent;
    }

    /**
     * Get the Tutor value.
     * @return the Tutor value.
     */
    public final Tutor getTutor() {
        return tutor;
    }

    /**
     * Set the Tutor value.
     * @param newTutor The new Tutor value.
     */
    public final void setTutor(final Tutor newTutor) {
        this.tutor = newTutor;
    }

    /**
     * Get the File value.
     * @return the File value.
     */
    public final byte[] getFile() {
        return file;
    }

    /**
     * Set the File value.
     * @param newFile The new File value.
     */
    public final void setFile(final byte[] newFile) {
        this.file = newFile;
    }

    /**
     * Get the Submission value.
     * @return the Submission value.
     */
    public final Submission getSubmission() {
        return submission;
    }

    /**
     * Set the Submission value.
     * @param newSubmission The new Submission value.
     */
    public final void setSubmission(final Submission newSubmission) {
        this.submission = newSubmission;
    }

}
