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
/**
 * @author OpenMentor team
 */

package uk.org.openmentor.businesslogic;

/**
 * Provides a wrapper exception class so that exceptions thrown from
 * within the reporting code of OpenMentor are independent of
 * exceptions from third party libraries such as the charting library.
 */
public class BusinessLogicException extends Exception {

    /** serialVersionUID is there for serializability. */
    static final long serialVersionUID = 6311830798917714229L;

    /**
     * Constructs the exception class with a message.
     *
     * @param message the message to throw with the exception
     */
    public BusinessLogicException(final String message) {
        super(message);
    }
}
