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
package uk.org.openmentor.web;

/**
 * Describe class <code>FileUpload</code> here.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class FileUpload {

    /**
     * Describe variable <code>file</code> here.
     *
     */
    private byte[] file;

    /**
     * Get the File value.
     * @return the File value.
     */
    public final byte[] getFile() {
        return file;
    }

    /**
     * Set the File value.
     * @param newFile The new File value.
     */
    public final void setFile(final byte[] newFile) {
        this.file = newFile;
    }
}
