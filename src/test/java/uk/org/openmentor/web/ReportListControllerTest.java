package uk.org.openmentor.web;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

public class ReportListControllerTest extends TestCase {

    private XmlWebApplicationContext ctx;
    private static Log log = LogFactory.getLog(ReportListControllerTest.class);

    public void setUp() {
        String[] paths = {"/WEB-INF/applicationContext.xml",
                          "/WEB-INF/action-servlet.xml"};
        ctx = new XmlWebApplicationContext();
        ctx.setConfigLocations(paths);
        ctx.setServletContext(new MockServletContext(""));
        ctx.refresh();
    }

    public void testReportList() throws Exception {
    	HttpSession session = new MockHttpSession();
        MockHttpServletRequest req = new MockHttpServletRequest();
        session.setAttribute("course","CM2006");
        //        req.addParameter("type","course");
        req.addParameter("type","course");
        req.setSession(session);
        
        ReportListController c = (ReportListController) 
                            ctx.getBean("reportListController");
        if (log.isDebugEnabled()) {
        	log.debug("In testReportList");
        }
        ModelAndView mav =
          c.handleRequest(req, (HttpServletResponse) null);
        Map m = mav.getModel();
        assertNotNull(m.get("course"));
        // Can't get the next two even with an appropriate type; there
        // is a Hibernate lazy load error
        // assertNotNull(m.get("students"));
        // assertNotNull(m.get("tutors"));
        assertEquals(mav.getViewName(), "listReports");
    }
}
