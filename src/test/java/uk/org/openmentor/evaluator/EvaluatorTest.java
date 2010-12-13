package uk.org.openmentor.evaluator;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.org.openmentor.analyzer.Analyzer;
import uk.org.openmentor.evaluator.Evaluator;
import uk.org.openmentor.model.Submission;

import junit.framework.TestCase;

public class EvaluatorTest  extends TestCase {
	private Analyzer analyzer = null;
	private Evaluator evaluator = null;
	private ApplicationContext ctx;
	
    public void setUp() {
        String[] paths = {"/WEB-INF/applicationContext.xml",
                          "/WEB-INF/action-servlet.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
        analyzer = (Analyzer) ctx.getBean("analyzer");
        evaluator = (Evaluator) ctx.getBean("evaluator");
    }

    public void testEvaluator() throws Exception {
        
        FileInputStream input = new 
            FileInputStream(new File("src/test/resources/test1.doc"));
        
        Set<String> students = new HashSet<String>();
        students.add("M0000001");
        Set<String> tutors = new HashSet<String>();
        tutors.add("00963381");
        
        Submission submission = analyzer.addSubmission("1", students, 
                                                       tutors, "2", input);
        evaluator.getFeedback(submission);
    }
}
