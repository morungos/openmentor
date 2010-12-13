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

package uk.org.openmentor.extractor;

import java.util.Set;
import java.io.InputStream;
import java.io.IOException;

/**
 * Extractor components are used to pull comments out of word processed
 * files. They consist of three methods, one of which processes a file
 * stream - no type hint is used, we are expected to use magic numbers
 * to determine the file type. This complete, other methods can be used
 * to return the text or a list of comments.
 *
 * @author Stuart Watt
 */
public interface Extractor {

    /**
     * Processes an input stream for comment and text extraction.
     *
     * @param stream        the input stream
     * @throws IOException  if something goes wrong
     */
    void extract(InputStream stream) throws IOException;

    /**
     * Returns the text of the word processed file.
     * @return              the word processed file contents
     */
    String getBody();

    /**
     * Returns any comments from the word processed file.
     * @return              the word processed file comments
     */
    Set getAnnotations();
}
