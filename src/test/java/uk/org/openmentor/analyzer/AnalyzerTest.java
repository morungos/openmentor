package uk.org.openmentor.analyzer;

import java.util.HashSet;
import java.util.Set;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;

import org.hibernate.SessionFactory;
import org.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import uk.org.openmentor.analyzer.Analyzer;
import uk.org.openmentor.service.AssignmentManager;

import uk.org.openmentor.dao.BaseDAOTestCase;
import uk.org.openmentor.model.Assignment;
import uk.org.openmentor.model.Submission;

public class AnalyzerTest extends BaseDAOTestCase {
	protected ApplicationContext ctx = null;
	private Analyzer analyzer = null;
	private SessionFactory sf = null;
	private AssignmentManager mgr = null;
 
	private static Log log = LogFactory.getLog(AnalyzerTest.class);
	
	public AnalyzerTest() {
        String[] paths = {"/WEB-INF/applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
    }
	
	public void setUp() throws Exception {
		sf = (SessionFactory) ctx.getBean("sessionFactory");
		mgr = (AssignmentManager) ctx.getBean("assignmentManager");
	}
	
	protected void startTransaction() throws Exception{
		Session s = sf.openSession();
		TransactionSynchronizationManager.bindResource(sf, new SessionHolder(s));
	}
	
	protected void endTransaction() throws Exception{
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sf);
		Session s = holder.getSession();
		s.flush();
		TransactionSynchronizationManager.unbindResource(sf);
		SessionFactoryUtils.releaseSession(s, sf);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		sf = null;
		mgr = null;
	}
	
	public void testAnalyzer() throws Exception {
		startTransaction();
		Assignment ass = new Assignment();
		ass.setAssignmentTitle("New assignment");
		ass.setCourseId("CM2006");
		ass.setSubmissions(new HashSet<Submission>());
		
		File file = new File("src/test/resources/test1.doc");
		InputStream input = new FileInputStream(file);
		Set<String> students = new HashSet<String>();
		Set<String> tutors = new HashSet<String>();
		students.add("M0000001");
		tutors.add("00900001");
		analyzer = (Analyzer) ctx.getBean("analyzer");
		Submission sub = analyzer.addSubmission("1", students, 
                                                        tutors, "Good", input);

		ass.getSubmissions().add(sub);
		mgr.saveAssignment(ass);
		
		if (log.isDebugEnabled()) {
			log.debug("New submission added: "+sub.getId());
		}
		
		if (log.isDebugEnabled()) {
			log.debug("Attempting to delete submission: "+sub.getId());
		}

		// For some reason, this is not cascading
                String id = Integer.toString(ass.getId());
		ass = mgr.getAssignment(id);
		mgr.removeAssignment(id);
		endTransaction();
 	}
}
