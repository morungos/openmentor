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

import java.util.List;
import java.util.Set;
import java.util.Map;
import uk.org.openmentor.model.Submission;

/**
 * This interface defines the holder of the outcome of an application
 * of business logic, namely the result of categorising comments made
 * on (one of more) submissions.  Arguably the nub of this whole
 * application is to start with annotated text and produce the
 * comments or anotations partitioned into distinct categories,
 * characterised both by the way the comment has been classified, and
 * by the original grade assigned.  In this interface we model this as
 * a pair of tables, one of which provides a list of the comments in
 * that particular cell of the table; the other just provides the
 * associated counts.  Someting similar happens in the evaluation
 * subpackage, where a DataSheet is produced, but arguably that is
 * directed directly at graphing; heer we are more interested in
 * replaving the functionality of SQLBusinessLogic.java.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public interface Categorization {

    public int getCommentCount(Category c, Grade g);
    
    public List getComments(Category c, Grade g);

    public int getCommentCount(Category c);
    
    public Map<Grade,Integer> getSubmissionCounts();

    public List getComments(Category c);

    public Categorization addComments(Submission s);

    public Categorization addComments(Set<Submission> s);

    public void clear();

}
