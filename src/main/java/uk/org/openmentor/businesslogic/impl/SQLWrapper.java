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
/*
 * Created on 13-Jan-2006
 *
 */
package uk.org.openmentor.businesslogic.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Much manipulated (and I hope not badly screwed up) by Ian Craw.
 * @author Hassan Sheikh - IET, The Open University
 */
public class SQLWrapper {

    /**
     * The database connection which, if not null, can be closed.
     */
    private Connection theConnection;

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(SQLWrapper.class);

    /**
     * Creates a new <code>SQLWrapper</code> instance, but privately!
     * So the effect is to stop the class being instantiated without
     * the full constructor.  See Bloch, page12.
     */
    private SQLWrapper() {
    }

    /**
     * Standard accessor.
     *
     * @return a <code>Connection</code> value
     */
    final Connection getConnection() {
        return theConnection;
    }

    /**
     * Our only useful constructor.  It does all the database
     * manipulation, which reflects how it is used in practice.
     *
     * @param driverClassName the JDBC class to use.
     * @param url the URL needed to access the database
     * @param username the name to use when accessing the database
     * @param password the password needed by the database.
     */
    public SQLWrapper(final String driverClassName, final String url,
                      final String username, final String password) {

        if (log.isDebugEnabled()) {
            log.debug("Setting jdbc connection parameters:"
                      + "driverClassName: " + driverClassName
                      + "; URL: " + url
                      + "; User: " + username
                      + "; Password: " + password);
        }
        try {
            Class.forName(driverClassName);
        } catch (Exception ex) {
            log.warn("Failed to find driver" + ex.getMessage());
        }
        try {
            theConnection = DriverManager.
                getConnection(url, username, password);

            // Allow long responses from GROUP_CONCAT
            String theSQL = "SET SESSION group_concat_max_len = 32000";
            PreparedStatement stmt = theConnection.prepareStatement(theSQL);
            stmt.executeQuery();

        } catch (Exception ex) {
            log.warn("Failed to connect to database " + ex.getMessage());
        }
    }

    /**
     * Release the resources and closeg the database connection.
     */
    public final void closeDB() {
        if (log.isDebugEnabled()) {
            log.debug("Closing database connection.");
        }
        try {
            theConnection.close();
        } catch (Exception ex) {
            log.warn("Unhandled exception " + ex.getMessage());
        }
    }

    /**
     * The <code>sqlTeacherCommentsCount</code> method seems to
     * prepare SQL to count the number of comments for a given tutor.
     *
     * @param courseId the ID of the course
     * @param teacherId a <code>String</code> value
     * @return a <code>String</code> value
     */
    final String sqlTeacherCommentsCount(final String courseId,
                                         final String teacherId) {
        StringBuilder b = new StringBuilder();
        b.append(" SELECT tbl_submission_tutors.tutor_id AS ObjectID,");
        b.append(" LEFT(tbl_comment_classes.category_name, 1) as CCode,");
        b.append(" tbl_submission.grade AS Grade,");
        b.append(" COUNT(DISTINCT tbl_comment.text) as CommentsCounter,");
        b.append(" GROUP_CONCAT(DISTINCT tbl_comment.text");
        b.append(" SEPARATOR '__SEP__') AS Comments");
        b.append(" FROM tbl_submission_tutors");
        b.append(" JOIN tbl_submission");
        b.append(" ON tbl_submission_tutors.submission_id = tbl_submission.id");
        b.append(" JOIN tbl_assignment ON");
        b.append(" tbl_assignment.id = tbl_submission.assignment_id");
        b.append(" JOIN tbl_comment");
        b.append(" ON tbl_submission.id = tbl_comment.submission_id");
        b.append(" JOIN tbl_comment_classes");
        b.append(" ON tbl_comment.id = tbl_comment_classes.comment_id");
        b.append(" WHERE tbl_assignment.course_id = '");
        b.append(courseId);
        b.append("'");
        b.append(" AND (tbl_submission_tutors.tutor_id in ('");
        b.append(teacherId);
        b.append("') )");
        b.append(" GROUP BY ObjectID, CCode, Grade");
        b.append(" ORDER BY ObjectID, Ccode, Grade");
        String theSQL = b.toString();
        if (log.isDebugEnabled()) {
            log.debug("Generating SQL: " + theSQL);
        }
        return theSQL;
    }

    /**
     * Describe <code>sqlStudentCommentsCount</code> method here.
     *
     * @param courseId  the course identifier
     * @param studentId a <code>String</code> value
     * @return a <code>String</code> value
     */
    final String sqlStudentCommentsCount(final String courseId,
                                         final String studentId) {
        StringBuilder b = new StringBuilder();
        b.append(" SELECT tbl_submission_students.student_id AS ObjectID,");
        b.append(" LEFT(tbl_comment_classes.category_name, 1) as CCode,");
        b.append(" tbl_submission.grade AS Grade,");
        b.append(" COUNT(DISTINCT tbl_comment.text) as CommentsCounter,");
        b.append(" GROUP_CONCAT(DISTINCT tbl_comment.text");
        b.append(" SEPARATOR '__SEP__') AS Comments");
        b.append(" FROM tbl_submission_students");
        b.append(" JOIN tbl_submission");
        b.append(" ON tbl_submission_students.submission_id");
        b.append(" = tbl_submission.id");
        b.append(" JOIN tbl_assignment ON");
        b.append(" tbl_assignment.id = tbl_submission.assignment_id");
        b.append(" JOIN tbl_comment");
        b.append(" ON tbl_submission.id = tbl_comment.submission_id");
        b.append(" JOIN tbl_comment_classes");
        b.append(" ON tbl_comment.id = tbl_comment_classes.comment_id");
        b.append(" WHERE tbl_assignment.course_id = '");
        b.append(courseId);
        b.append("'");
        b.append(" AND (tbl_submission_students.student_id in ('");
        b.append(studentId);
        b.append("' ) )");
        b.append(" GROUP BY ObjectID, CCode, Grade");
        b.append(" ORDER BY ObjectID, CCode, Grade");
        String theSQL = b.toString();
        if (log.isDebugEnabled()) {
            log.debug("Generating SQL: " + theSQL);
        }
        return theSQL;
    }

    /**
     * The <code>sqlCourseCommentsCount</code> method returns the sql
     * statement which will fetch the total number of comments for
     * each assignment for a specified course here.
     *
     * @param courseId the ID of the course
     * @return the SQL
     */
    final String sqlCourseCommentsCount(final String courseId) {
        StringBuilder b = new StringBuilder();
        b.append(" SELECT tbl_assignment.course_id AS ObjectID,");
        b.append(" LEFT(tbl_comment_classes.category_name, 1) as CCode,");
        b.append(" tbl_submission.grade AS Grade,");
        b.append(" COUNT(DISTINCT tbl_comment.text) as CommentsCounter,");
        b.append(" GROUP_CONCAT(DISTINCT tbl_comment.text");
        b.append(" SEPARATOR '__SEP__') AS Comments");
        b.append(" FROM tbl_submission JOIN tbl_assignment ON");
        b.append(" tbl_assignment.id = tbl_submission.assignment_id");
        b.append(" JOIN tbl_comment");
        b.append(" ON tbl_submission.id = tbl_comment.submission_id");
        b.append(" JOIN tbl_comment_classes");
        b.append(" ON tbl_comment.id = tbl_comment_classes.comment_id");
        b.append(" WHERE tbl_assignment.course_id = '");
        b.append(courseId);
        b.append("'");
        b.append(" GROUP BY ObjectID, CCode, Grade");
        b.append(" ORDER BY ObjectID, CCode, Grade");
        String theSQL = b.toString();
        if (log.isDebugEnabled()) {
            log.debug("Generating SQL: " + theSQL);
        }
        return theSQL;
    }

    /**
     * The <code>sqlAssignmentCommentsCount</code> method returns the
     * sql statement to fetch number of comments for each assignment
     * file for a specified assignment.
     *
     * @param assignmentID an <code>int</code> value
     * @return a <code>String</code> value
     */
    final String sqlAssignmentCommentsCount(final int assignmentID) {
        StringBuilder b = new StringBuilder();
        b.append(" SELECT tbl_assignment.id,");
        b.append(" Left(tbl_BalesCategory.BalesCategoryCode, 1) as CCode,");
        b.append(" Count(tbl_comment_classes.comment_id) as CommentsCounter");
        b.append(" FROM tbl_assignment");
        b.append(" Left Outer JOIN tbl_submission");
        b.append(" ON tbl_assignment.id = tbl_submission.assignment_id");
        b.append(" Left Outer JOIN tbl_submission_students");
        b.append(" ON tbl_submission.id");
        b.append(" = tbl_submission_students.submission_id");
        b.append(" Left Outer JOIN tbl_comment");
        b.append(" ON tbl_submission_students.submission_id");
        b.append(" = tbl_comment.submission_id");
        b.append(" INNER JOIN tbl_comment_classes");
        b.append(" ON tbl_comment.id = tbl_comment_classes.comment_id ");
        b.append(" Right Outer Join tbl_BalesCategory");
        b.append(" on tbl_comment_classes.category_name");
        b.append(" = tbl_BalesCategory.BalesCategoryCode");
        b.append(" WHERE     (tbl_assignment.id  in (");
        b.append(assignmentID);
        b.append(" ) )");
        b.append(" GROUP BY Left(tbl_BalesCategory.BalesCategoryCode, 1)");
        b.append(" ORDER BY Left(tbl_BalesCategory.BalesCategoryCode, 1)");
        String theSQL = b.toString();
        if (log.isDebugEnabled()) {
            log.debug("Generating SQL: " + theSQL);
        }
        return theSQL;
    }

    /**
     * Finds the list of categories to be used. This should really come from
     * the evaluator bean. Actually, this code really generates the SQL to
     * find the list of categories to be used, rather than actually doing it.
     *
     * @return the SQL statement.
     */
    final String sqlCommentsCategoriesList() {
        StringBuilder b = new StringBuilder();
        b.append(" Select DISTINCT Left(BalesCategoryCode, 1) As CCode ");
        b.append(" From tbl_BalesCategory ");
        b.append(" Order by Left(BalesCategoryCode, 1) ");
        String theSQL = b.toString();
        if (log.isDebugEnabled()) {
            log.debug("Generating SQL: " + theSQL);
        }
        return theSQL;
    }

    /**
     * The <code>sqlTeacherAssignmentList</code> method is used for
     * the first page of the report to show list of tutors and total
     * number of assignments they've marked. Feedback column will be
     * calculated using GetCommentCount method.
     *
     * @param selectedTeacherIDs a <code>String</code> value
     * @return the SQL statement.
     */
    final String sqlTeacherAssignmentList(final String selectedTeacherIDs) {
        StringBuilder b = new StringBuilder();
        b.append(" Select tbl_Tutor.ID,");
        b.append(" Concat(tbl_Tutor.first_name, ' ' , tbl_Tutor.last_name)");
        b.append(" as TeacherName,");
        b.append(" Count(Submission_ID) as TotalAssignments, tbl_Tutor.Org_ID");
        b.append(" from tbl_Tutor");
        b.append(" Inner Join tbl_submission_tutors");
        b.append(" on tbl_Tutor.ID = tbl_submission_tutors.tutor_id");
        if (!selectedTeacherIDs.equals("")) {
            b.append(" Where tbl_Tutor.ID in (");
            b.append(selectedTeacherIDs);
            b.append(")");
        }
        b.append(" Group by tbl_Tutor.ID");
        b.append(" Order by tbl_Tutor.ID");
        String theSQL = b.toString();
        if (log.isDebugEnabled()) {
            log.debug("Generating SQL: " + theSQL);
        }
        return theSQL;
    }

    /**
     * This function to be used for first page of report to show list
     * of assignment, course title and total number of student
     * submission for each assignment. Feedback column will be
     * calculated using GetCommentCount function
     *
     * @param selectedAssignmentIDs a <code>String</code> value
     * @return a <code>String</code> value
     */
    final String sqlAssignmentCourseList(final String selectedAssignmentIDs) {
        StringBuilder b = new StringBuilder();
        b.append("Select tbl_assignment.id,");
        b.append(" tbl_assignment.assignment_title, tbl_assignment.course_id,");
        b.append(" Course_Title, Count(tbl_submission.id) as TotalAssignments");
        b.append(" From tbl_submission");
        b.append(" Inner Join tbl_assignment");
        b.append(" on tbl_submission.assignment_id = tbl_assignment.id");
        b.append(" Inner Join tbl_Course");
        b.append(" on tbl_assignment.course_id = tbl_Course.ID");
        if (!selectedAssignmentIDs.equals("")) {
            b.append(" Where tblAssignment.AssignmentID in (");
            b.append(selectedAssignmentIDs);
            b.append(")");
        }
        b.append(" Group By tbl_assignment.id");
        String theSQL = b.toString();
        if (log.isDebugEnabled()) {
            log.debug("Generating SQL: " + theSQL);
        }
        return theSQL;
    }

    /**
     * Describe <code>sqlCourseAssignmentList</code> method here. This
     * function to be used for first page of report to show list of
     * courses, course title and total number of student submission
     * for each course. Feedback column will be calculated using
     * GetCommentCount function.
     *
     * @param selectedCourseIDs a <code>String</code> value
     * @return a <code>String</code> value
     */
    final String sqlCourseAssignmentList(final String selectedCourseIDs) {
        StringBuilder b = new StringBuilder();
        b.append(" Select tbl_assignment.course_id,");
        b.append(" Course_Title, Count(tbl_submission.id) as TotalAssignments");
        b.append(" From tbl_submission");
        b.append(" Inner Join tbl_assignment");
        b.append(" on tbl_submission.assignment_id = tbl_assignment.id");
        b.append(" Inner Join tbl_Course");
        b.append(" on tbl_assignment.course_id = tbl_Course.ID");
        if (!selectedCourseIDs.equals("")) {
            b.append(" Where tbl_Course.ID in (");
            b.append(selectedCourseIDs);
            b.append(")");
        }
        b.append(" Group By tbl_Course.ID");
        String theSQL = b.toString();
        if (log.isDebugEnabled()) {
            log.debug("Generating SQL: " + theSQL);
        }
        return theSQL;
    }

    /**
     * The <code>sqlStudentAssignmentList</code> method builds an SQL
     * query to be used for first page of report to show list of
     * tutors and total number of assignments they've marked. Feedback
     * column will be calculated using GetCommentCount functionhere.
     *
     * @param selectedStudentIDs a <code>String</code> value
     * @return a <code>String</code> value
     */
    final String sqlStudentAssignmentList(final String selectedStudentIDs) {
        StringBuilder b = new StringBuilder();
        b.append(" Select tbl_Student.ID,");
        b.append(" Concat(tbl_Student.first_name, ' ' ,tbl_Student.last_name)");
        b.append(" as StudentName,");
        b.append(" Count(tbl_submission_students.submission_id)");
        b.append(" as TotalAssignments, tbl_Student.Org_ID");
        b.append(" From tbl_student");
        b.append(" Inner Join tbl_submission_students");
        b.append(" on tbl_Student.ID = tbl_submission_students.student_id");
        if (!selectedStudentIDs.equals("")) {
            b.append(" Where tbl_student.ID in (");
            b.append(selectedStudentIDs);
            b.append(")");
        }
        b.append(" Group by tbl_student.ID");
        b.append(" Order by tbl_student.ID");
        String theSQL = b.toString();
        if (log.isDebugEnabled()) {
            log.debug("Generating SQL: " + theSQL);
        }
        return theSQL;
    }

    /**
     * The <code>sqlAvergageMarks</code> method this function returns
     * average marks SQL for the given function parameters.
     *
     * @param marksFor a <code>String</code> value
     * @param selectedID a <code>String</code> value
     * @return a <code>String</code> value
     */
    final String sqlAvergageMarks(final String marksFor,
                                  final String selectedID) {
        StringBuilder b = new StringBuilder();
        // all strings end the same way (after the if statement).
        if (marksFor.equals("tutor")) {
            b.append(" Select Round(Avg(tbl_submission_students.score_int),2)");
            b.append(" as AverageMarks");
            b.append(" From tbl_submission_tutors");
            b.append(" Inner join tbl_submission_students");
            b.append(" on tbl_submission_tutors.submission_id");
            b.append(" = tbl_submission_students.submission_id");
            b.append(" Where  tbl_submission_tutors.tutor_id in (");
        } else if (marksFor.equals("assignment")) {
            b.append(" Select Round(Avg(tbl_submission_students.Score_Int),2)");
            b.append(" as AverageMarks");
            b.append(" From tbl_submission_students");
            b.append(" Inner join tbl_submission on");
            b.append(" tbl_submission_students.submission_id");
            b.append(" = tbl_submission.id");
            b.append(" Where  tbl_submission.assignment_id in (");
        } else if (marksFor.equals("course")) {
            b.append(" Select Round(Avg(tbl_submission_students.Score_Int),2)");
            b.append(" as AverageMarks ");
            b.append(" From tbl_assignment");
            b.append(" Inner join tbl_submission on tbl_assignment.id");
            b.append(" = tbl_submission.assignment_id");
            b.append(" Inner join tbl_submission_students");
            b.append(" on tbl_submission.id");
            b.append(" = tbl_submission_students.submission_id");
            b.append(" Where  tbl_assignment.course_id in (");
        } else {        //otherwise for students
            b.append(" Select Round(Avg(Score_Int),2) as AverageMarks ");
            b.append(" From tbl_submission_students");
            b.append(" Where  Student_ID in (");
        }
        b.append(selectedID);
        b.append(")");
        String theSQL = b.toString();
        if (log.isDebugEnabled()) {
            log.debug("Generating SQL: " + theSQL);
        }
        return theSQL;
    }

    /**
     * The <code>sqlFullComments</code> method generates the SQL to
     * extract categories and corresponding list of comments.
     *
     * @param marksFor a <code>String</code> value
     * @param selectedID a <code>String</code> value
     * @return a <code>String</code> value
     */
    final String sqlFullComments(final String marksFor,
                                 final String selectedID) {
        StringBuilder b = new StringBuilder();
        // All queries begin and end in the same way.
        b.append(" Select left(category_name,1) as CategoryName,");
        b.append(" tbl_comment.text as CommentText");
        if (marksFor.equals("tutor")) {
            b.append(" From tbl_submission_tutors");
            b.append(" Inner join tbl_comment");
            b.append(" on tbl_submission_tutors.submission_id");
            b.append(" = tbl_comment.submission_id");
            b.append(" Inner join tbl_comment_classes");
            b.append(" on tbl_comment.id = tbl_comment_classes.comment_id");
            b.append(" Where tbl_submission_tutors.tutor_id in (");
        } else if (marksFor.equals("assignment")) {
            b.append(" From tbl_submission_students");
            b.append(" Inner join tbl_comment");
            b.append(" on tbl_submission_students.submission_id");
            b.append(" = tbl_comment.submission_id");
            b.append(" Inner join tbl_comment_classes");
            b.append(" on tbl_comment.id = tbl_comment_classes.comment_id");
            b.append(" Where  tbl_submission.assignment_id in (");
        } else if (marksFor.equals("course")) {
            b.append(" From tbl_assignment");
            b.append(" Inner join tbl_submission");
            b.append(" on tbl_assignment.id = tbl_submission.assignment_id");
            b.append(" Inner join tbl_comment");
            b.append(" on tbl_submission.id = tbl_comment.submission_id");
            b.append(" Inner join tbl_comment_classes");
            b.append(" on tbl_comment.id = tbl_comment_classes.comment_id");
            b.append(" Where  tbl_assignment.course_id in (");
        } else { //otherwise for students
            b.append(" From tbl_submission_students");
            b.append(" Inner join tbl_comment");
            b.append(" on tbl_submission_stu.submission_id");
            b.append(" = tbl_comment.submission_id");
            b.append(" Inner join tbl_comment_classes");
            b.append(" on tbl_comment.id = tbl_comment_classes.comment_id");
            b.append(" Where  Student_ID in (");
        }
        b.append(selectedID);
        b.append(")");
        b.append(" order by left(category_name,1)");
        String theSQL = b.toString();
        if (log.isDebugEnabled()) {
            log.debug("Generating SQL: " + theSQL);
        }
        return theSQL;
    }
}
