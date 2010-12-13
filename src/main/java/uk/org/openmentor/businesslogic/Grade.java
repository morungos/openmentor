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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is really an RGU Grade; I think we don't want this as an
 * interface since it is built form configuartion data.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public final class Grade {

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(Grade.class);

    /**
     * The string by which this class is known in the database, such
     * as "0".
     */
    private String label;

    /**
     * The corresponding description, such as "Excellent".
     */
    private String descriptor;

    /**
     * I need a public constructor to ensure Grade is not null in
     * UploadData.  Or (better) I need to generate an empty grade
     * here, and deliver it to that class.
     */
    public Grade(){
        // Intentionally empty.
    }

    /**
     * Try to make sure these aren't created on demand; rather we
     * supply an exising singleton.  Note that the class is imutable.
     *
     * @param theLabel a <code>String</code> value
     * @param theDescriptor a <code>String</code> value
     */
    protected Grade(String theLabel, String theDescriptor) {
        this.label = theLabel;
        this.descriptor = theDescriptor;
        if (log.isDebugEnabled()) {
            log.debug("Created " + this.label
                      + " (" + this.descriptor + ")");
        }
    }

    /**
     * Get the Descriptor value.
     * @return the Descriptor value.
     */
    public String getDescriptor() {
        return descriptor;
    }

    /**
     * Get the Label value.
     * @return the Label value.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Just to ease the use for debugging.
     * @return a <code>String</code> value
     */
    public String toString() {
        return label + " (" + descriptor +")";
    }
}

