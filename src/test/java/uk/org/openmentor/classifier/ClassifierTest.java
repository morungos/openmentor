package uk.org.openmentor.classifier;

import java.util.Set;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import uk.org.openmentor.classifier.Classifier;
import uk.org.openmentor.dao.hibernate.CourseInfoDAOHibernate;

import junit.framework.TestCase;

public class ClassifierTest extends TestCase {
    protected ApplicationContext ctx = null;
    private Classifier classifier = null;
    private static Log log = LogFactory.getLog(CourseInfoDAOHibernate.class);
    
    public ClassifierTest() {
        String[] paths = {"/WEB-INF/applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
    }
    
    private void classify(String theString) {
        Set result;
        if (log.isDebugEnabled()) {
            log.debug("Target: "+theString);
        }
        result = classifier.classifyString(theString);
        for (Iterator i = result.iterator(); i.hasNext(); ) {
            String value = i.next().toString();
            if (log.isDebugEnabled()) {
                log.debug("Classified: "+value);
            }
        }
    }
    
    public void testClassifier() {
        classifier = (Classifier) ctx.getBean("classifier");
        classify("Hello, there");
        classify("Well done");
        classify("Have you looked at coffee?");
        classify("You need to write better");
        classify("Not enough");
    }
}
