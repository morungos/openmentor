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
 * This is a fine Category; I have to work out how to interface
 * this.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public final class Category {

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(Category.class);

    /**
     * The string by which this class is known in the database, such
     * as "A".
     */
    private String label;

    /**
     * The corresponding description, such as "Positive reactions".
     */
    private String descriptor;

    /**
     * Again a private constructor to avoid unnecessary creation.
     */
    Category() {
        // Intentionally empty.
    }

    /**
     * Try to make sure these aren't created on demand; rather we
     * supply an exising singleton.  Note that the class is imutable.
     *
     * @param theLabel a <code>String</code> value
     * @param theDescriptor a <code>String</code> value
     */
    protected Category(String theLabel, String theDescription) {
        this.label = theLabel;
        this.descriptor = theDescription;
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
     * Set the Descriptor value.
     * @param newDescriptor The new Descriptor value.
     */
    public void setDescriptor(String newDescriptor) {
        this.descriptor = newDescriptor;
    }


    /**
     * Get the Label value.
     * @return the Label value.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Just to ease the use.
     * @return a <code>String</code> value
     */
    public String toString() {
        return label;
    }
}
