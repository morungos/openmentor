package uk.org.openmentor.service;

import java.util.List;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CourseInfoManagerTest extends TestCase {
    private ApplicationContext ctx;
    private CourseInfoManager mgr;

    protected void setUp() throws Exception {
        String[] paths = {"/WEB-INF/applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
        mgr = (CourseInfoManager) ctx.getBean("courseInfoManager");
    }

    protected void tearDown() throws Exception {
        mgr = null;
    }
    
    public void testGetCourses() throws Exception {
        List courses = mgr.getCourses();
        assertTrue(courses.size() > 0);
    }
}
