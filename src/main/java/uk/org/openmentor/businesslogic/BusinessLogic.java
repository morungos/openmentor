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

import uk.org.openmentor.model.DataBook;

/**
 * This interface is used to structure the business logic for
 * OpenMentor.  The implementations of this will pass the data
 * structures needed to the charting component.
 *
 * @author Stuart Watt
 */

public interface BusinessLogic {

    /**
     * Describe <code>buildDataBook</code> method here.
     *
     * @return a <code>DataBook</code> value
     * @exception BusinessLogicException if an error occurs
     */
    DataBook buildDataBook() throws BusinessLogicException;

    /**
     * Describe <code>setRequestType</code> method here.
     *
     * @param requestType a <code>String</code> value
     */
    void setRequestType(String requestType);

    /**
     * Sets the current course, which is the scope for all
     * following calculations.
     * @param courseId       the new course identifier
     */

    void setCourseId(String courseId);

    /**
     * Describe <code>setId</code> method here.
     *
     * @param id a <code>String</code> value
     */
    void setId(String id);
}
