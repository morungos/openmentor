package uk.org.openmentor.web;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import uk.org.openmentor.model.Assignment;
import uk.org.openmentor.service.AssignmentManager;
import uk.org.openmentor.web.SimpleAssignFormController;

public class AssignmentFormControllerTest extends TestCase {
    private static Log log = LogFactory.getLog(AssignmentFormControllerTest.class);
    private XmlWebApplicationContext ctx;
    private SimpleAssignFormController c;
    private MockHttpServletRequest request;
    private ModelAndView mv;
    private Assignment ass;

    public void setUp() throws Exception {
        String[] paths =
            { "/WEB-INF/applicationContext.xml", "/WEB-INF/action-servlet.xml" };
        ctx = new XmlWebApplicationContext();
        ctx.setConfigLocations(paths);
        ctx.setServletContext(new MockServletContext(""));
        ctx.refresh();
        c = (SimpleAssignFormController) ctx.getBean("assignmentFormController");

        // add a test user to the database
        AssignmentManager mgr = (AssignmentManager) ctx.getBean("assignmentManager");
        ass = new Assignment();
        ass.setCourseId("CM2006");
        ass.setAssignmentTitle("Test assignment");
        mgr.saveAssignment(ass);
    }

    public void tearDown() {
        ctx = null;
        c = null;
        ass = null;
    }

    public void testEdit() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("testing edit...");
        }

        request = new MockHttpServletRequest("GET", "/editAssignment.html");
        request.addParameter("id", Integer.toString(ass.getId()));
        request.addParameter("title", "New test assignment");
        mv = c.handleRequest(request, new MockHttpServletResponse());
        assertEquals("assignmentForm", mv.getViewName());
    }

    public void testSave() throws Exception {
        request = new MockHttpServletRequest("POST", "/editAssignment.html");
        request.addParameter("id", Integer.toString(ass.getId()));
        request.addParameter("courseId", ass.getCourseId());
        request.addParameter("title", "Test assignment");
        mv = c.handleRequest(request, new MockHttpServletResponse());

        Errors errors =
            (Errors) mv.getModel().get(BindException.ERROR_KEY_PREFIX + "user");
        assertNull(errors);
        assertNotNull(request.getSession().getAttribute("message"));
    }

    public void testRemove() throws Exception {
        request = new MockHttpServletRequest("POST", "/editAssignment.html");
        request.addParameter("delete", "");
        request.addParameter("id", Integer.toString(ass.getId()));
        mv = c.handleRequest(request, new MockHttpServletResponse());
        assertNotNull(request.getSession().getAttribute("message"));
    }
}
