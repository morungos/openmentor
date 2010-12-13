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

import uk.org.openmentor.service.CourseInfoManager;

/**
 * Describe class <code>CourseInfoController</code> here.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class CourseInfoController implements Controller {

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(CourseInfoController.class);

    /**
     * Describe variable <code>mgr</code> here.
     */
    private CourseInfoManager mgr = null;

    /**
     * Describe <code>setCourseInfoManager</code> method here.
     *
     * @param courseInfoMgr a <code>CourseInfoManager</code> value
     */
    public final void setCourseInfoManager(final CourseInfoManager
                                           courseInfoMgr) {
        this.mgr = courseInfoMgr;
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

        if (request.getMethod() == "POST") {
            if (log.isDebugEnabled()) {
                log.debug("POST submission");
            }
            String courseId = request.getParameter("course");
            if (log.isDebugEnabled()) {
                log.debug("Selected course: " + courseId);
            }
            request.getSession().setAttribute("course", courseId);
        }

        return new ModelAndView("selectCourse", "courses", mgr.getCourses());
    }
}
