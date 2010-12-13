package uk.org.openmentor.businesslogic;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import junit.framework.TestCase;
import uk.org.openmentor.businesslogic.BusinessLogic;
import uk.org.openmentor.model.DataBook;

public class BusinessLogicTest extends TestCase {
	
    protected ApplicationContext ctx = null;
    
    private SessionFactory sf = null;

    private BusinessLogic logic = null;
    
    public BusinessLogicTest() {
    	String[] paths = {"/WEB-INF/applicationContext.xml"};
    	ctx = new ClassPathXmlApplicationContext(paths);
    }
    
    protected void setUp() throws Exception {
        super.setUp();
        sf = (SessionFactory) ctx.getBean("sessionFactory");
        logic = (BusinessLogic) ctx.getBean("businessLogic");
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
    
    protected void tearDown() throws Exception {
        super.tearDown();
        sf = null;
        logic = null;
    }
    
    public final void testTeachers() throws Exception {
        startTransaction();
        logic.setCourseId("CM2006");
        logic.setRequestType("tutor");
        logic.setId("00900001");
        
        // Do the work
        DataBook book = logic.buildDataBook();
        
        // Check we get something
        assertNotNull(book);
        assertTrue(book.getDataPoints().size() > 0);
        endTransaction();
    }
    
    public final void testStudents() throws Exception {
        startTransaction();
        logic.setCourseId("CM2006");
        logic.setRequestType("student");
        logic.setId("M0000002");
        
        // Do the work
        DataBook book = logic.buildDataBook();
        
        // Check we get something
        assertNotNull(book);
        assertTrue(book.getDataPoints().size() > 0);
        endTransaction();
    }
    
    public final void testCourses() throws Exception {
        startTransaction();
        logic.setCourseId("CM2006");
        logic.setRequestType("course");
        logic.setId("CM2006");
        
        // Do the work
        DataBook book = logic.buildDataBook();
        
        // Check we get something
        assertNotNull(book);
        assertTrue(book.getDataPoints().size() > 0);
        endTransaction();
    }
}
