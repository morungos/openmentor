package uk.org.openmentor.businesslogic;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import uk.org.openmentor.evaluator.EvaluationScheme;

import junit.framework.TestCase;

/**
 * Tests both the <code>DescriptorFactory</code> and the
 * <code>Grade</code> and <code>Category</code> classes it produces.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class DescriptorFactoryTest extends TestCase {

    protected ApplicationContext ctx = null;

    private EvaluationScheme scheme = null;

    private static Log log = LogFactory.getLog(DescriptorFactoryTest.class);

    protected void setUp() throws Exception {
        String[] paths = {"/WEB-INF/applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
        scheme = (EvaluationScheme) ctx.getBean("evaluationScheme");
    }

    protected void tearDown() throws Exception {
        scheme = null;
    }

    /**
     * Test we can retrieve all possible grades; they will have been
     * created during initialisation of the Descriptor Factory.
     */
    public void testGrades() {
        assertNotNull("Null evaluationScheme",scheme);
        List<String> grades = scheme.getBands();
        for (String gradeName : grades) {
            Grade g = DescriptorFactory.getGrade(gradeName);
            if (log.isDebugEnabled()) {
                log.debug("Found " + g);
            }
            assertNotNull(g.getDescriptor());
        }
    }

    /**
     * Tess we can retrieve all possible categories, including the
     * default one; they will have been created during initialisation
     * of the Descriptor Factory.
     */
    public void testCategories() {
        List<String> categories = scheme.getCategories();
        for (String categoryName : categories) {
            Category c = DescriptorFactory.getCategory(categoryName);
            if (log.isDebugEnabled()) {
                log.debug("Found " + c);
            }
            assertNotNull(c.getDescriptor());
        }
        String defaultCategoryName = scheme.getDefaultCategory();
        Category c = DescriptorFactory.getCategory(defaultCategoryName);
        if (log.isDebugEnabled()) {
            log.debug("Found " + c);
        }
        assertNotNull(c.getDescriptor());
    }
}
