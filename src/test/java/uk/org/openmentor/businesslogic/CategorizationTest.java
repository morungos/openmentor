/* =======================================================================
 * Copyright 2004-2006 The OpenMentor Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================================
 */
package uk.org.openmentor.businesslogic;

import java.util.Set;
import java.util.HashSet;

import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.openmentor.businesslogic.DescriptorFactory;
import uk.org.openmentor.businesslogic.Grade;
import uk.org.openmentor.model.Submission;
import uk.org.openmentor.model.Comment;
import uk.org.openmentor.model.mock.MockSubmission;

/**
 * A fairly thorough test of the <code>Categorization</code> class; we
 * apply it to two mock submissions consecutively and check the counts
 * are correct.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class CategorizationTest extends TestCase {

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(CategorizationTest.class);

    /**
     * Where we get the <code>Categorization</code> instance from.
     */
    private ApplicationContext ctx;

    /**
     * This is the object we are testing..
     */
    private Categorization ctgz;
    
    public CategorizationTest() {
        String[] paths = {"/WEB-INF/applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
    }

    public void setUp() throws Exception {
        ctgz = (Categorization) ctx.getBean("categorization");
        ctgz.clear();
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
        ctgz = null;
    }

    public void testInitialisation() throws Exception {
        assertNotNull("Categorisation not loaded",ctgz);
    }

    /**
     * Create an object, initialised with a given submission; then
     * accumulate two more submissions there.
     *
     * @exception Exception if an error occurs
     */
    public void testCategorization() throws Exception {
        Integer grade = 6;
        Submission s = MockSubmission.getSubmission1(grade);
        if (log.isDebugEnabled()) {
            log.debug("Found submission with grade " + s.getGrade());
        }
        assertEquals("Incorrect grade", grade.toString(),s.getGrade());
        ctgz.addComments(s);
        if (log.isDebugEnabled()) {
            log.debug(ctgz);
        }

        Grade g = DescriptorFactory.getGrade(grade.toString());
        Category c = DescriptorFactory.getCategory("B");
        int n = ctgz.getCommentCount(c,g);
        assertEquals("Incorrect number of type B comments.",n,9);

        // Finally test we can accumulate data.
        Submission s2 = MockSubmission.getSubmission2(grade);
        ctgz.addComments(s2);
        if (log.isDebugEnabled()) {
            log.debug(ctgz);
        }

        c = DescriptorFactory.getCategory("E");
        n = ctgz.getCommentCount(c,g);
        assertEquals("Incorrect number of type E comments.",n,3);

        Submission s3 = MockSubmission.getSubmission3(grade);
        ctgz.addComments(s3);
        if (log.isDebugEnabled()) {
            log.debug(ctgz);
        }

        c = DescriptorFactory.getCategory("D");
        n = ctgz.getCommentCount(c,g);
        assertEquals("Incorrect number of type D comments.",n,3);
        /*
        // Now to build the course submission for CMM007
        Integer grade2 = new Integer(2);
        Integer grade3 = new Integer(3);
        Integer grade4 = new Integer(4);
        Set<Submission> submissions = new HashSet<Submission>();
        submissions.add(MockSubmission.getSubmission2(grade2));
        submissions.add(MockSubmission.getSubmission3(grade4));
        submissions.add(MockSubmission.getSubmission1(grade2));
        submissions.add(MockSubmission.getSubmission3(grade3));
        submissions.add(MockSubmission.getSubmission2(grade3));
        ctgz.addComments(submissions);
        if (log.isDebugEnabled()) {
            log.debug(ctgz);
        }
        */
    }
}
