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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.RequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import uk.org.openmentor.model.Assignment;
import uk.org.openmentor.service.AssignmentManager;

/**
 * So called primarily because this is the simplest example of a form
 * backing object, with just two fields.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class SimpleAssignFormController extends SimpleFormController {

    /**
     * Our usual logger.
     */
    private static Log log =
        LogFactory.getLog(SimpleAssignFormController.class);

    /**
     * The manager which mediates all the database access.  Currently
     * set by Spring.
     */
    private AssignmentManager mgr = null;

    /**
     * Standard mutator for use eg by Spring.
     *
     * @param assignmentManager an <code>AssignmentManager</code> value
     */
    public final void setAssignmentManager(final AssignmentManager
                                           assignmentManager) {
        this.mgr = assignmentManager;
    }

    /**
     * Get the <code>AssignmentManager</code> object.
     *
     * @return the <code>AssignmentManager</code>.
     */
    public final AssignmentManager getAssignmentManager() {
        return this.mgr;
    }

    /**
     * Does what is requested; either a delete or a save.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @param command an <code>Object</code> value
     * @param errors a <code>BindException</code> value
     * @return a <code>ModelAndView</code> value
     * @exception Exception if an error occurs
     */
    public final ModelAndView onSubmit(final HttpServletRequest request,
                                       final HttpServletResponse response,
                                       final Object command,
                                       final BindException errors)
        throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'onSubmit' method...");
        }

        Assignment ass = (Assignment) command;

        if (request.getParameter("delete") != null) {
            mgr.removeAssignment(Integer.toString(ass.getId()));
            request.getSession().setAttribute("message",
                    getMessageSourceAccessor().
                       getMessage("assignment.deleted",
                          new Object[] {ass.getAssignmentTitle()}));
            if (log.isDebugEnabled()) {
                log.debug("Deleting " + ass.getAssignmentTitle());
            }
        } else {
            mgr.saveAssignment(ass);
            request.getSession().setAttribute("message",
                    getMessageSourceAccessor().
                       getMessage("assignment.saved",
                          new Object[] {ass.getAssignmentTitle()}));
            if (log.isDebugEnabled()) {
                log.debug("Saving " + ass.getAssignmentTitle());
            }
        }
        return new ModelAndView(getSuccessView());
    }

    /**
     * Manages the creation or re-use of the underlying Assessment object.
     *
     * @param request where the need originates.
     * @return the Assessment as an <code>Object</code>;
     * @exception ServletException if an error occurs.
     */
    protected final Object formBackingObject(final HttpServletRequest request)
        throws ServletException {
        Assignment ass;

        String assId = RequestUtils.getStringParameter(request, "id", "0");

        if (!assId.equals("0")) {
            if (log.isDebugEnabled()) {
                log.debug("formBackingObject: Got assignment id: " + assId);
            }
            ass = mgr.getAssignment(assId);
        } else {
            ass = new Assignment();
            // Get and set the course id for the form backing object
            String courseId = (String)
                request.getSession().getAttribute("course");
            ass.setCourseId(courseId);
            if (log.isDebugEnabled()) {
                log.debug("formBackingObject: creating new assignment.");
            }
        }
        return ass;
    }
}
