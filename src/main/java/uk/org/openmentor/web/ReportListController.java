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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import uk.org.openmentor.model.Course;
import uk.org.openmentor.service.CourseInfoManager;
import uk.org.openmentor.service.ModelManager;

/**
 * Describe class <code>ReportListController</code> here.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class ReportListController implements Controller {

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(ReportListController.class);

    /**
     * The <code>CourseInfoManager</code> bean is set by Spring.
     */
    private CourseInfoManager mgr = null;

    /**
     * The <code>ModelManager</code> bean is set by Spring.
     */
    private ModelManager mmgr = null;

    /**
     * Usual <code>CourseInfoManager</code> mutator.
     *
     * @param courseInfoManager the <code>CourseInfoManager</code> value.
     */
    public final void setCourseInfoManager(final CourseInfoManager
                                           courseInfoManager) {
        this.mgr = courseInfoManager;
    }

    /**
     * Usual <code>ModelManager</code> mutator.
     *
     * @param newModelManager the <code>ModelManager</code> value.
     */
    public final void setModelManager(final ModelManager
                                      newModelManager) {
        this.mmgr = newModelManager;
    }

    /**
     * Describe <code>handleRequest</code> method here.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return a <code>ModelAndView</code> value
     * @exception Exception if an error occurs
     */
    public final ModelAndView handleRequest(final HttpServletRequest request,
                                      final HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'handleRequest' method...");
        }

        String courseId =
            (String) request.getSession().getAttribute("course");
        if (courseId == null) {
            throw new Exception("Can't get session variable");
        }

        Map<String, Object> values = new HashMap<String, Object>();
        Course currentCourse = mgr.getCourse(courseId);
        String type = request.getParameter("type");
        if (log.isDebugEnabled()) {
            log.debug("Reporting for course: " + courseId
                      + ", type " + type);
        }
        values.put("type", type);
        values.put("course", currentCourse);
        // Only return those tutors or students (principals) we can
        // report on; and only if necessary.
        if (type.equals("student")) {
            values.put("students", mmgr.getActiveStudents(currentCourse));
        } else if (type.equals("tutor")) {
            values.put("tutors", mmgr.getActiveTutors(currentCourse));
        }
        return new ModelAndView("listReports", values);
    }
}
