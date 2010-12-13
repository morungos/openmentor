package uk.org.openmentor.dao;

import uk.org.openmentor.model.Assignment;
import uk.org.openmentor.model.Submission;
import uk.org.openmentor.model.Comment;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.SessionFactory;

public class AssignmentDAOTest extends BaseDAOTestCase {
	private static Log log = LogFactory.getLog(AssignmentDAOTest.class);
	private SessionFactory sessionFactory = null;
	private AssignmentDAO dao = null;

	protected void setUp() throws Exception {
		super.setUp();
		sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
		setSessionFactory(sessionFactory);
		dao = (AssignmentDAO) ctx.getBean("assignmentDAO");
		setHibernateTemplate((Object) dao);
		startTestTransaction();
	}

	protected void tearDown() throws Exception {
		endTestTransaction();
		super.tearDown();
		dao = null;
	}

	public void testAssignments() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Adding new assignment");
		}
		Assignment ass = new Assignment();
		ass.setAssignmentTitle("New assignment");
		ass.setCourseId("CM2006");
		
		dao.saveAssignment(ass);
		if (log.isDebugEnabled()) {
			log.debug("Assignment id: "+ass.getId());
		}
		
		Submission sub = new Submission();
		sub.setGrade("3");
		
		Set<String> students = new HashSet<String>();
		students.add("M0000001");
		sub.setStudents(students);
		
		Set<String> tutors = new HashSet<String>();
		tutors.add("00900001");
		sub.setTutors(tutors);
		
		Set<Submission> submissions = new HashSet<Submission>();
		submissions.add(sub);
		
		Set<Comment> comments = new HashSet<Comment>();
		Comment comm = new Comment();
		
		Set<String> classes = new HashSet<String>();
		classes.add("B1");
		classes.add("B2");
		classes.add("A3");
		
		comm.setText("This is a mixed comment");
		comm.setClasses(classes);
		comments.add(comm);
		sub.setComments(comments);
		
		ass.setSubmissions(submissions);
		dao.saveAssignment(ass);
		
		List courses = dao.getAssignments("CM2006");
		if (log.isDebugEnabled()) {
			log.debug("Found "+courses.size()+" assignments");
		}
		assertTrue(courses.size() >= 1);
		
		Assignment rass = (Assignment) courses.get(0);
		assertTrue(rass.getCourseId().equals("CM2006"));
		
		if (log.isDebugEnabled()) {
			log.debug("Removing assignment");
		}
		dao.removeAssignment(ass.getId());
	}
}
