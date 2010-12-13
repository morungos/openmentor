package uk.org.openmentor.web;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.springframework.mock.web.MockServletContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockHttpServletRequest;

import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;


public class AssignmentControllerTest extends TestCase {

    private XmlWebApplicationContext ctx;

    public void setUp() {
        String[] paths = {"/WEB-INF/applicationContext.xml",
                          "/WEB-INF/action-servlet.xml"};
        ctx = new XmlWebApplicationContext();
        ctx.setConfigLocations(paths);
        ctx.setServletContext(new MockServletContext(""));
        ctx.refresh();
    }

    /**
     * The <code>testGetAssignments</code> method tests that we select
     * the "model" model and that the new view is correct.  In order
     * to do this, we need to mock up a session containing an
     * appropriate course.
     *
     * @exception Exception if an error occurs
     */
    public void testGetAssignments() throws Exception {

        HttpSession session = new MockHttpSession();
        MockHttpServletRequest req = new MockHttpServletRequest();
        session.setAttribute("course","CM2006");
        req.setSession(session);
        AssignmentController c = (AssignmentController) 
                            ctx.getBean("selectAssignmentController");
        ModelAndView mav =
            c.handleRequest(req, (HttpServletResponse) null);
        //        UploadData data = mav.getModel();
        //        assertNotNull(data.getAssignments());
        assertEquals(mav.getViewName(), "selectAssignment");
    }
}
