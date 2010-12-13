package uk.org.openmentor.evaluator;

import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import uk.org.openmentor.evaluator.EvaluationScheme;

import junit.framework.TestCase;

public class EvaluationSchemesTest extends TestCase {
    protected ApplicationContext ctx = null;
    private EvaluationScheme es = null;
    private static Log log = LogFactory.getLog(EvaluationSchemesTest.class);
    
    public EvaluationSchemesTest() {
        String[] paths = {"/WEB-INF/applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
    }
    
    public void testEvaluationScheme() {
        es = (EvaluationScheme) ctx.getBean("evaluationScheme");
        if (log.isDebugEnabled()) {
            log.debug("Found evaluator: "+es);
        }
        
        List bands = es.getBands();
        Iterator i = bands.iterator();
        while (i.hasNext()) {
            String band = (String) i.next();
            if (log.isDebugEnabled()) {
                log.debug("Band: "+band);
            }
            Map proportions = es.getBandProportions(band);
            Set keys = (Set) proportions.keySet();
            Iterator j = keys.iterator();
            while (j.hasNext()) {
                String prop = (String) j.next();
                if (log.isDebugEnabled()) {
                    log.debug("Proportion: "+prop+" is "+proportions.get(prop));
                }
            }
        }
    }
}
