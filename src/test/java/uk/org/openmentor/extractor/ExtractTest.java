package uk.org.openmentor.extractor;

import java.util.Set;
import java.io.FileInputStream;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.org.openmentor.extractor.Extractor;

import junit.framework.TestCase;

public class ExtractTest extends TestCase {
    protected ApplicationContext ctx = null;
    private Extractor extractor = null;
    
    public ExtractTest() {
        String[] paths = {"/WEB-INF/applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
    }
    
    protected void setUp() throws Exception {
        extractor = (Extractor) ctx.getBean("extractor");
    }
    
    /**
     * Simple test to check that comments can be successfully
     * extracted from Word files.
     * 
     * @throws Exception
     */
    public void testExtraction() throws Exception {
        extractor.extract(new
                          FileInputStream("src/test/resources/test4.doc"));
        Set result = extractor.getAnnotations();
        assertTrue(result.contains("Balloon here!"));
        assertTrue(result.contains("Second balloon"));
        assertTrue(result.size() == 2);
    }
    
    public void testExtraction2() throws Exception {
        extractor.extract(new
                          FileInputStream("src/test/resources/test2.doc"));
        Set result = extractor.getAnnotations();
        int theSize = result.size();
        assertTrue(theSize > 0);
    }
    
    public void testExtraction3() throws Exception {
        extractor.extract(new
                          FileInputStream("src/test/resources/test3.doc"));
        Set result = extractor.getAnnotations();
        int theSize = result.size();
        assertTrue(theSize > 0);
    }
    
    public void testExtraction4() throws Exception {
        extractor.extract(new
                          FileInputStream("src/test/resources/test1.doc"));
        Set result = extractor.getAnnotations();
        int theSize = result.size();
        assertTrue(theSize > 0);
    }
    
    public void testExtraction5() throws Exception {
        extractor.extract(new 
                          FileInputStream("src/test/resources/test4.doc"));
        Set result = extractor.getAnnotations();
        assertTrue(result.contains("Balloon here!"));
        assertTrue(result.contains("Second balloon"));
        assertTrue(result.size() == 2);
    }
}
