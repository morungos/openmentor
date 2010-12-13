package uk.org.openmentor.service;

import java.util.List;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AssignmentManagerTest extends TestCase {
    private ApplicationContext ctx;
    private AssignmentManager mgr;
    
    protected void setUp() throws Exception {
        String[] paths = {"/WEB-INF/applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
        mgr = (AssignmentManager) ctx.getBean("assignmentManager");
    }
    
    protected void tearDown() throws Exception {
        mgr = null;
    }
    
    public void testGetAssignments() throws Exception {
        List asses = mgr.getAssignments("CM2006");
        assertNotNull(asses);
    }
}
