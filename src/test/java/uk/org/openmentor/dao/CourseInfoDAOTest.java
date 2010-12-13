package uk.org.openmentor.dao;

import uk.org.openmentor.model.Course;
import uk.org.openmentor.model.Student;
import uk.org.openmentor.model.Tutor;
import uk.org.openmentor.service.CourseInfoManagerTest;

import java.util.List;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.SessionFactory;

public class CourseInfoDAOTest extends BaseDAOTestCase {
	private static Log log = LogFactory.getLog(CourseInfoManagerTest.class);
	private SessionFactory sessionFactory = null;
	private CourseInfoDAO dao = null;

	protected void setUp() throws Exception {
		super.setUp();
		sessionFactory = (SessionFactory) ctx.getBean("sessionFactoryForCourseInfo");
		setSessionFactory(sessionFactory);
		dao = (CourseInfoDAO) ctx.getBean("courseInfoDAO");
		setHibernateTemplate((Object) dao);
		startTestTransaction();
	}

	protected void tearDown() throws Exception {
		endTestTransaction();
		super.tearDown();
		dao = null;
	}
	
	public void testGetCourses() throws Exception {
		List courses = dao.getCourses();
		assertTrue(courses.size() > 0);
		for (Iterator i = courses.iterator(); i.hasNext(); ) {
			Course course = (Course) i.next();
            if (log.isDebugEnabled()) {
                log.debug("Found course: "+course.getId());
            }
		}
 	}
	
	public void testGetStudentsAndTutors() throws Exception {
		List courses = dao.getCourses();
		Course course = (Course) courses.get(0);
        if (log.isDebugEnabled()) {
            log.debug("Testing course: "+course.getId());
        }
		Iterator i;
		String studentId = null;
		String tutorId = null;
		
		List students = dao.getStudents(course);
		for (i = students.iterator(); i.hasNext(); ) {
			Student std = (Student) i.next();
			studentId = std.getOrgId();
            if (log.isDebugEnabled()) {
                log.debug("Found student: "+std.getOrgId()+", "+std.getFirstName()+" "+
                        std.getLastName());
            }
		}
		List tutors = dao.getTutors(course);
		for (i = tutors.iterator(); i.hasNext(); ) {
			Tutor tut = (Tutor) i.next();
			tutorId = tut.getOrgId();
            if (log.isDebugEnabled()) {
                log.debug("Found tutor: "+tut.getOrgId()+", "+tut.getFirstName()+" "+
                        tut.getLastName());
            }
		}
		
		assertFalse(dao.getStudent(course, studentId) == null);
		assertFalse(dao.getTutor(course, tutorId) == null);
	}
}
