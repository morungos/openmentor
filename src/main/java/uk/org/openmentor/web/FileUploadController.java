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

package uk.org.openmentor.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

//import javax.sql.rowset.serial.SerialBlob;

import java.io.ByteArrayInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.springframework.validation.BindException;
import javax.servlet.ServletException;

import uk.org.openmentor.analyzer.Analyzer;
import uk.org.openmentor.model.Assignment;
import uk.org.openmentor.model.Course;
import uk.org.openmentor.model.Student;
import uk.org.openmentor.model.Submission;
import uk.org.openmentor.model.UploadData;
import uk.org.openmentor.model.Tutor;
import uk.org.openmentor.service.AssignmentManager;
import uk.org.openmentor.service.CourseInfoManager;
import uk.org.openmentor.businesslogic.DescriptorFactory;
import uk.org.openmentor.businesslogic.Grade;

/**
 * FileUploadController
 *
 * Important note - this cannot easily be unit tested, due to the lack of mocks
 * for file upload requests. There are solutions, but these depend on
 * non-standard parts of the Spring distribution, which therefore don't work
 * with Maven. The standard approach with this seems to be to test this aspect
 * of the system manually.
 *
 * @author Stuart Watt
 */

public class FileUploadController extends SimpleFormController {
    private static Log log = LogFactory.getLog(FileUploadController.class);

    /**
     * Describe variable <code>analyzer</code> here.
     *
     */
    private Analyzer analyzer = null;

    /**
     * Describe variable <code>mgr</code> here.
     *
     */
    private AssignmentManager mgr = null;

    /**
     * Describe variable <code>cmgr</code> here.
     *
     */
    private CourseInfoManager cmgr = null;

    /**
     * Being explicit about a no argument constructor, although
     * nothing unexpected happens.
     */
    public FileUploadController() {
        super();
    }

    /**
     * Setter for the analyser bean; called from the
     * dependency injection container.
     *
     * @param newAnalyzer the Anaylzer to set.
     */
    public final void setAnalyzer(final Analyzer newAnalyzer) {
        this.analyzer = newAnalyzer;
    }

    /**
     * Setter for the assignment manager bean; called from the
     * dependency injection container.
     *
     * @param assignmentMgr
     */
    public final void setAssignmentManager(final AssignmentManager
                                           assignmentMgr) {
        this.mgr = assignmentMgr;
    }

    /**
     * Setter for the course manager bean; called from the
     * dependency injection container.
     * 
     * @param courseMgr
     */
    public final void setCourseInfoManager(final CourseInfoManager courseMgr) {
        this.cmgr = courseMgr;
    }

    protected final void initBinder(final HttpServletRequest request,
                                    final ServletRequestDataBinder binder)
        throws ServletException {
        // to actually be able to convert Multipart instance to byte[]
        // we have to register a custom editor (in this case the
        // ByteArrayMultipartEditor
        binder.registerCustomEditor(byte[].class,
                                    new ByteArrayMultipartFileEditor());
        // now Spring knows how to handle multipart object and convert them
    }

    protected final ModelAndView onSubmit(final HttpServletRequest request,
                                          final HttpServletResponse response,
                                          final Object command,
                                          final BindException errors)
        throws Exception {

        MultipartHttpServletRequest mreq =
            (MultipartHttpServletRequest) request;
        StringBuffer files = new StringBuffer("");
        for (Iterator i = mreq.getFileNames(); i.hasNext();) {
            String theFile = (String) i.next();
            if (!files.equals("")) {
                files.append(", ");
            }
            files.append(theFile);
        }

        UploadData bean = (UploadData) command;
        byte[] file = bean.getFile();

        if (log.isDebugEnabled()) {
            log.debug("About to analyse submission. "
                      + "Found " + file.length + " bytes");
        }

        String courseId = (String) request.getSession().getAttribute("course");
        Course course = cmgr.getCourse(courseId);

        bean.setAssignments(mgr.getAssignments(courseId));
        bean.setStudents(course.getStudents());
        bean.setTutors(course.getTutors());
        bean.setGrades(DescriptorFactory.getGrades());
        if (log.isDebugEnabled()) {
            log.debug("Saving " + bean.getStudents().size()
                      + " students, " + bean.getTutors().size()
                      + " tutors and " + bean.getAssignments().size()
                      + " assignments.");
        }

        /* TODO: file upload is erratic for analysis - no idea why yet */

        // Mediated write through a file
        // File f = new File("/tmp/test.doc");
        // FileOutputStream out = new FileOutputStream(f);
        // ByteArrayOutputStream bytes = new ByteArrayOutputStream(file.length);
        // bytes.write(file, 0, file.length); bytes.writeTo(out); out.close();
        // FileInputStream input = new FileInputStream(f);

        // Direct write from memory
        ByteArrayInputStream input = new ByteArrayInputStream(file);

        if (analyzer == null) {
            throw new Exception("Failed to initialize the analyzer correctly");
        }
        if ((file == null) || (file.length == 0)) {
            errors.reject("file", "no file specified");
        }
        String assignmentId = request.getParameter("assignment.id");
        if ((assignmentId == null) || (assignmentId.equals(""))
            || (assignmentId.equals("0"))) {
            errors.reject("assignment", "invalid assignment");
        } else {
            Assignment ass = mgr.getAssignment(assignmentId);
            bean.setAssignment(ass);
            if (log.isDebugEnabled()) {
                log.debug("saving assignment " + ass.getAssignmentTitle());
            }
        }
        String gradeLabel = request.getParameter("grade.label");
        if ((gradeLabel == null) || (gradeLabel.equals(""))) {
            errors.reject("grade", "no grade specified");
        } else {
            Grade grade = DescriptorFactory.getGrade(gradeLabel);
            bean.setGrade(grade);
           if (log.isDebugEnabled()) {
                log.debug("saving grade " + grade);
            }
        }


        String studentOrgId = request.getParameter("student.orgId");
        if ((studentOrgId == null) || (studentOrgId.equals(""))
            || (studentOrgId.equals("0"))) {
            errors.reject("student.orgId", "no student specified");
        } else {
            // <spring:bind path=..."> is powerful, but to make the
            // page where this is first selected intelligible, I need
            // to know the name of the student.  So I need to fully
            // instantiate the student eg in the student.setOrgId
            // method, (or somewhere in UploadData) since that is what
            // Spring calls when student.orgId is set.  It seems
            // easier to do things by hand.
            Student student = cmgr.getStudent(course, studentOrgId);
            bean.setStudent(student);
            if (log.isDebugEnabled()) {
                log.debug("saving Student " + student.getFirstName()
                          + " " + student.getLastName()
                          + " (" + student.getOrgId() + ")");
            }
        }

        String tutorOrgId = request.getParameter("tutor.orgId");
        if ((tutorOrgId == null) || (tutorOrgId.equals(""))
            || (tutorOrgId.equals("0"))) {
            errors.reject("tutorOrgId", "no tutor specified");
        } else { // Don't know how to do this using spring:bind
            Tutor tutor = cmgr.getTutor(course, tutorOrgId);
            bean.setTutor(tutor);
            if (log.isDebugEnabled()) {
                log.debug("saving tutor " + tutor.getFirstName()
                          + " " + tutor.getLastName());
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("\nParameters: assignmentId = " + assignmentId
                      + "\nParameters: grade = " + gradeLabel
                      + "\nParameters: studentOrgId = "
                      + studentOrgId + "\nParameters: tutorOrgId = "
                      + tutorOrgId);
        }

        if (errors.hasErrors()) {
            if (log.isDebugEnabled()) {
                log.debug("Error return: " + errors);
            }
            return super.processFormSubmission(request, response,
                                               command, errors);
        }

        if (log.isDebugEnabled()) {
            log.debug("Found uploaded bytes: " + file.length);
        }

        Set<String> students = new HashSet<String>();
        students.add(studentOrgId);
        Set<String> tutors = new HashSet<String>();
        tutors.add(tutorOrgId);

        // OK, at this point, we need to be able to read and write to the
        // database. The returned object is also important as this is what we
        // need to render a simple analysis.

        Submission submission = analyzer.
            addSubmission(assignmentId, students, tutors, gradeLabel, input);
        submission.setFilename(files.toString());
        //        submission.setBody(new SerialBlob(file));

        // Add the new submission to the assignment
        Assignment ass = mgr.getAssignment(assignmentId);
        ass.getSubmissions().add(submission);
        mgr.saveAssignment(ass);

        bean.setSubmission(submission);

        if (log.isDebugEnabled()) {
            log.debug("Returning successView");
        }
        return new ModelAndView(getSuccessView(), "model", bean);
    }
}
