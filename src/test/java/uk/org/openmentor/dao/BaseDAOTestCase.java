package uk.org.openmentor.dao;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Base class for DAO TestCases.
 * @author Matt Raible
 */
public class BaseDAOTestCase extends TestCase {
	protected final Log log = LogFactory.getLog(getClass());

	protected ApplicationContext ctx = null;

	private SessionFactory sessionFactory = null;

	private Session session = null;

	public BaseDAOTestCase() {
		String[] paths = { "/WEB-INF/applicationContext.xml" };
		ctx = new ClassPathXmlApplicationContext(paths);
	}

	protected void setSessionFactory(SessionFactory s) {
		sessionFactory = s;
	}

	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	protected void setHibernateTemplate(Object value) {
		HibernateDaoSupport hdao = (HibernateDaoSupport) value;
		hdao.setHibernateTemplate(new HibernateTemplate(getSessionFactory()));
		if (log.isDebugEnabled()) {
			log.debug("Set hibernate template: " + hdao.getHibernateTemplate());
		}
	}

	protected void startTestTransaction() {
		session = SessionFactoryUtils.getSession(sessionFactory, true);
		if (log.isDebugEnabled()) {
			log.debug("Starting test transaction: session: " + session);
		}
		TransactionSynchronizationManager.bindResource(sessionFactory,
				new SessionHolder(session));
	}

	protected void endTestTransaction() {
		if (log.isDebugEnabled()) {
			log.debug("Ending test transaction: session: " + session);
		}
		try {
			session.flush();
		} catch (Exception e) {

		}
		;
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		SessionFactoryUtils.releaseSession(session, sessionFactory);
		session = null;
	}
}
