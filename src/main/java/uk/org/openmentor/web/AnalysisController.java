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

import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import uk.org.openmentor.service.AssignmentManager;
import uk.org.openmentor.evaluator.Evaluator;
import uk.org.openmentor.model.Submission;

/**
 * Describe class <code>AnalysisController</code> here.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class AnalysisController implements Controller {

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(AnalysisController.class);

    /**
     * The <code>mgr</code> is normally set by Spring.
     */
    private AssignmentManager mgr = null;

    /**
     * The <code>evaluator</code> actually does the work, and is
     * normally set by Spring.
     *
     */
    private Evaluator evaluator = null;

    /**
     * Standard mutator eg for use by Spring.
     *
     * @param assignmentMgr the required manager.
     */
    public final void setAssignmentManager(final AssignmentManager
                                           assignmentMgr) {
        this.mgr = assignmentMgr;
    }

    /**
     * Standard mutator eg as used by Spring.
     *
     * @param newEvaluator an <code>Evaluator</code> value
     */
    public final void setEvaluator(final Evaluator newEvaluator) {
        this.evaluator = newEvaluator;
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
            log.debug("Entering 'handleRequest' method.");
        }

        String commentlimit = request.getParameter("show");
        if (commentlimit == null) {
            commentlimit = "3";
        }
        if (commentlimit.equals("all")) {
            commentlimit = "1000";
        }
        if (log.isDebugEnabled()) {
            log.debug("commentlimit is " + commentlimit);
        }
        String submissionId = request.getParameter("id");
        if (submissionId == null) { // real trouble if true
            submissionId = "0";     // only useful for testing
        }
        if (log.isDebugEnabled()) {
            log.debug("Parameter: submission = " + submissionId);
        }
        Submission submission = mgr.getSubmission(submissionId);
        // Can't do the next bit if we are testing
        if (log.isDebugEnabled() && !submissionId.equals("0")) {
            log.debug("\nSubmission parameter: id = " + submission.getId()
                      + "\nSubmission parameter: grade = "
                      + submission.getGrade()
                      + "\nSubmission parameter: students = "
                      + submission.getStudents()
                      + "\nSubmission parameter: tutors = "
                      + submission.getTutors()
                      + "\nSubmission parameter: type = "
                      + submission.getType());
        }
        // The order here is influenced by the need to test without
        // doing too much mocking; but maybe it is better to fail in
        // these null cases and show there is a problem?
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("submission", submission);
        model.put("commentlimit", commentlimit);
        if (submission != null) {
            Object result = evaluator.getFeedback(submission);
            model.put("analysis", result);
        }

        return new ModelAndView("analysis", "model", model);
    }
}
