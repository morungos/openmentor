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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import uk.org.openmentor.service.AssignmentManager;
import uk.org.openmentor.service.CourseInfoManager;

import uk.org.openmentor.model.Course;
import uk.org.openmentor.model.UploadData;
import uk.org.openmentor.businesslogic.DescriptorFactory;
import uk.org.openmentor.businesslogic.Grade;

public class AssignmentController implements Controller {
    private static Log log = LogFactory.getLog(AssignmentController.class);
    private AssignmentManager mgr = null;
    private CourseInfoManager cmgr = null;

    public final void setAssignmentManager(final AssignmentManager
                                           assignmentManager) {
        this.mgr = assignmentManager;
    }

    public final void setCourseInfoManager(final CourseInfoManager
                                           courseInfoManager) {
        this.cmgr = courseInfoManager;
    }

    public final ModelAndView handleRequest(final HttpServletRequest request,
                                            final HttpServletResponse response)
        throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'handleRequest' method...");
        }
        String courseId = (String) request.getSession().getAttribute("course");
        if (log.isDebugEnabled()) {
            log.debug("Selected course: " + courseId);
        }
        Course course = cmgr.getCourse(courseId);

        UploadData data = new UploadData();
        data.setAssignments(mgr.getAssignments(courseId));
        data.setStudents(course.getStudents());
        data.setTutors(course.getTutors());
        data.setGrades(DescriptorFactory.getGrades());

        if (log.isDebugEnabled()) {
            log.debug("Saving " + data.getStudents().size()
                      + " students, " + data.getGrades().size()
                      + " grades, " + data.getTutors().size()
                      + " tutors and " + data.getAssignments().size()
                      + " assignments.");
            log.debug("Transferring to view");
        }

        return new ModelAndView("selectAssignment", "uploadData", data);
    }
}
