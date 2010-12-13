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

/**
 * Describe class <code>SimpleAssignController</code> here.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class SimpleAssignController implements Controller {

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(SimpleAssignController.class);

    /**
     * The <code>mgr</code> is normally set by Spring.
     */
    private AssignmentManager mgr = null;

    /**
     * Stanadrd mutator eg for use by Spring.
     *
     * @param assignmentMgr the required manager.
     */
    public final void setAssignmentManager(final AssignmentManager
                                           assignmentMgr) {
        this.mgr = assignmentMgr;
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
            log.debug("Transferring to view");
        }
        String courseId = (String)
            request.getSession().getAttribute("course");
        if ((courseId == null) || (courseId.equals(""))) {
            log.warn("Can't list assignments without first"
                     + " specifying a course.");
            return new ModelAndView("redirect:selectCourse");
        }
        return new ModelAndView("listAssignments", "assignments",
                                mgr.getAssignments(courseId));
    }
}
