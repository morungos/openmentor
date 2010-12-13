package uk.org.openmentor.web;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mock.web.MockServletContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReportControllerTest extends TestCase {

    private XmlWebApplicationContext ctx;

    private SessionFactory sf = null;

    private static Log log = LogFactory.getLog(ReportControllerTest.class);
    private ReportController c;
    
    public void setUp() {
        String[] paths = {"/WEB-INF/applicationContext.xml",
                          "/WEB-INF/action-servlet.xml"};
        ctx = new XmlWebApplicationContext();
        ctx.setConfigLocations(paths);
        ctx.setServletContext(new MockServletContext(""));
        ctx.refresh();
        sf = (SessionFactory) ctx.getBean("sessionFactory");
        c = (ReportController) ctx.getBean("reportController");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        sf = null;
        c = null;
    }

    protected void startTransaction() throws Exception{
        Session s = sf.openSession();
        TransactionSynchronizationManager.
            bindResource(sf, new SessionHolder(s));
    }
    
    protected void endTransaction() throws Exception{
        SessionHolder holder = (SessionHolder) 
            TransactionSynchronizationManager.getResource(sf);
        Session s = holder.getSession();
        s.flush();
        TransactionSynchronizationManager.unbindResource(sf);
        SessionFactoryUtils.releaseSession(s, sf);
    }
    

    public void testTeachersReport() throws Exception {
    	MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();
        ModelAndView mav = null;
        
        req.getSession().setAttribute("course", "CM2006");
        req.addParameter("RequestType", "ChartImage");
        req.addParameter("ReportFor", "tutor");
        req.addParameter("ChartType", "BarChart");
        req.addParameter("ID", "00900001");
        req.setMethod("GET");
        startTransaction();
        mav = c.handleRequest(req, resp);
        
        // Charts return null
        assertNull(mav);
        endTransaction();
        // OK, what happened??
        if (log.isDebugEnabled()) {
        	log.debug("Completed request, status: "+resp.getStatus());
        }
    }

    public void testStudentsReport() throws Exception {
    	MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();
        ModelAndView mav = null;

        req.getSession().setAttribute("course", "CM2006");
        req.addParameter("RequestType", "ChartImage");
        req.addParameter("ReportFor", "student");
        req.addParameter("ChartType", "BarChart");
        req.addParameter("ID", "M0000001");
        req.setMethod("GET");
        startTransaction();
        mav = c.handleRequest(req, resp);
        
        // Charts return null
        assertNull(mav);
        endTransaction();        
        // OK, what happened??
        if (log.isDebugEnabled()) {
        	log.debug("Completed request, status: "+resp.getStatus());
        }
    }
        
    public void testCoursesReport() throws Exception {
    	MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();
        ModelAndView mav = null;

        req.getSession().setAttribute("course", "CM2006");
        req.addParameter("RequestType", "ChartImage");
        req.addParameter("ReportFor", "course");
        req.addParameter("ChartType", "BarChart");
        req.addParameter("ID", "CM2006");
        req.setMethod("GET");
        startTransaction();
        mav = c.handleRequest(req, resp);
        endTransaction();
        // Charts return null
        assertNull(mav);
        
        // OK, what happened??
        if (log.isDebugEnabled()) {
        	log.debug("Completed request, status: "+resp.getStatus());
        }
    }

    public void testTeachersTableReport() throws Exception {
    	MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();
        ModelAndView mav = null;
        
        req.getSession().setAttribute("course", "CM2006");
        req.addParameter("RequestType", "Table");
        req.addParameter("ReportFor", "tutor");
        req.addParameter("ID", "00900001");
        req.setMethod("GET");
        startTransaction();
        mav = c.handleRequest(req, resp);
        endTransaction();
        // Tables don't return null
        assertNotNull(mav);
        assertNotNull(mav.getModel().get("categories"));
        assertNotNull(mav.getModel().get("actual"));
        assertNotNull(mav.getModel().get("ideal"));
        assertNotNull(mav.getModel().get("comments"));
        
        // OK, what happened??
        if (log.isDebugEnabled()) {
        	log.debug("Completed request, status: "+resp.getStatus());
        }
    }

}
