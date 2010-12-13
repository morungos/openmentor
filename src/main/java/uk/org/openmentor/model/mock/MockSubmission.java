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

package uk.org.openmentor.model.mock;

import java.util.HashSet;
import java.util.Set;

import uk.org.openmentor.model.Comment;
import uk.org.openmentor.model.Submission;


/**
 * I'm still learning to write mocks; so let me start with a very
 * naive one, which I nevertheless believe will be useful.  It simply
 * provides access to two properly structured comments.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class MockSubmission extends Submission{

    public static final Submission getSubmission1(Integer grade) {
        Set<Comment> comments = new HashSet<Comment>();
        for (int i = 0; i < 16; i++) {
            comments.add(MockComment.getComment(i));
        }
        Submission s = new Submission();
        s.setComments(comments);
        s.setGrade(grade.toString());
        return s;
    }

    public static final Submission getSubmission2(Integer grade) {
        Set<Comment> comments = new HashSet<Comment>();
        for (int i = 0; i < 11; i++) {
            comments.add(MockComment.getComment2(i));
        }
        Submission s = new Submission();
        s.setComments(comments);
        s.setGrade(grade.toString());
        return s;
    }

    public static final Submission getSubmission3(Integer grade) {
        Set<Comment> comments = new HashSet<Comment>();
        for (int i = 0; i < 20; i++) {
            comments.add(MockComment.getComment3(i));
        }
        Submission s = new Submission();
        s.setComments(comments);
        s.setGrade(grade.toString());
        return s;
    }
}
