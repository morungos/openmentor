package uk.org.openmentor.web;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

public class CourseInfoControllerTest extends TestCase {

    private XmlWebApplicationContext ctx;

    public void setUp() {
        String[] paths = {"/WEB-INF/applicationContext.xml",
                          "/WEB-INF/action-servlet.xml"};
        ctx = new XmlWebApplicationContext();
        ctx.setConfigLocations(paths);
        ctx.setServletContext(new MockServletContext(""));
        ctx.refresh();
    }

    public void testGetCourses() throws Exception {
    	HttpSession session = new MockHttpSession();
        MockHttpServletRequest req = new MockHttpServletRequest();
        session.setAttribute("course","CM2006");
        req.setSession(session);
        
        CourseInfoController c = (CourseInfoController) 
                            ctx.getBean("courseInfoController");
        ModelAndView mav =
            c.handleRequest(req, (HttpServletResponse) null);
        Map m = mav.getModel();
        assertNotNull(m.get("courses"));
        assertEquals(mav.getViewName(), "selectCourse");
    }
}
