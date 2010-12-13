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

/**
 * The model class used to represent assignments in Open Mentor.
 * This class can be persisted through whatever persistence
 * system you choose to use; it is the start of the object
 * system within the main Open Mentor data storage systems. Each
 * <code>Assignment</code> contains a set of {@link Submission}s,
 * which are where the grading information, and the associations
 * to students and tutors, begin to come in. 
 *
 * @author Stuart Watt
 */
public class Assignment implements Serializable {
    
    static final long serialVersionUID = 1754543682880894503L;

    /** The assignment identifier. */
    private int id;

    /** The course identifier. */
    private String courseId;

    /** The assignment title. */
    private String assignmentTitle;

    /** The set of submissions. */
    private Set<Submission> submissions;

    /**
     * Constructs a new {@link Assignment} for the given course identifier,
     * assignment title, and list of submissions.
     *
     * @param courseId          the course identifier
     * @param assignmentTitle   the assignment title
     * @param submissions       the set of submissions
     */
    public Assignment(final String courseId,
                      final String assignmentTitle,
                      final Set<Submission> submissions) {
        this.courseId = courseId;
        this.assignmentTitle = assignmentTitle;
        this.submissions = submissions;
    }

    /**
     * Constructs a new {@link Assignment}.
     */
    public Assignment() {
    }

    /**
     * Constructs a new {@link Assignment} for the given course identifier
     * and assignment title.
     *
     * @param courseId          the course identifier
     * @param assignmentTitle   the assignment title
     */
    public Assignment(final String assignmentTitle,
                      final Set<Submission> submissions) {
        this.assignmentTitle = assignmentTitle;
        this.submissions = submissions;
    }

    /**
     * Gets the assignment identifier.
     * @return                  the assignment identifier
     */
    public final int getId() {
        return this.id;
    }

    /**
     * Sets the assignment identifier.
     * @param newId             the new assignment identifier
     */
    public final void setId(final int newId) {
        this.id = newId;
    }

    /**
     * Gets the course code.
     * @return                  the course code
     */
    public final String getCourseId() {
        return this.courseId;
    }

    /**
     * Sets the course code.
     * @param newCourseId       the new course code
     */
    public final void setCourseId(final String newCourseId) {
        this.courseId = newCourseId;
    }

    /**
     * Gets the assignment title.
     * @return                  the assignment title
     */
    public final String getAssignmentTitle() {
        return this.assignmentTitle;
    }

    /**
     * Sets the assignment title.
     * @param newAssignmentTitle the new assignment title
     */
    public final void setAssignmentTitle(final String newAssignmentTitle) {
        this.assignmentTitle = newAssignmentTitle;
    }

    /**
     * Gets the Set of submissions.
     * @return                  the set of submissions
     */
    public final Set<Submission> getSubmissions() {
        return this.submissions;
    }

    /**
     * Sets the set of submissions
     * @param newSubmissions    the new set of submissions
     */
    public final void setSubmissions(final Set<Submission> newSubmissions) {
        this.submissions = newSubmissions;
    }
}
