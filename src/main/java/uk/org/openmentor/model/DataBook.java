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

package uk.org.openmentor.model;

import java.util.List;

/**
 * This class abstracts the methods we need from the DataBookImpl.  In
 * particular it makes the point that there is no need to expose
 * directly the underlying HashMap at present.
 *
 * @author Ian Craw
 */
public interface DataBook {

	/**
     * Sets the data points for the rendering; each data series
     * should have a value matching the corresponding data point. 
     *
     * @param value the data to be stored
     */
	public void setDataPoints(List<String> value);

    /**
     * Returns the data points for the rendering; each data series
     * should have a value matching the corresponding data point. 
     *
     * @return		the List of data points
     */
	public List<String> getDataPoints();

    /**
     * Stores given data against a given key.
     *
     * @param key the key used for storage
     * @param value the data to be stored.
     */
	public void setDataSeries(String key, List value);

	/**
     * The <code>getDataSeries</code> returns data that was
     * previously stored against the given key.
     *
     * @param key the key to retrieve the data
     * @return the data as an <code>Object</code>.
     */
    public List<Object> getDataSeries(String key);

    /**
     * Sets a property - this can be used to mark up the DataBook
     * with additional values for either rendering or display.
     * 
     * @param key		the property key
     * @param value		the property value
     */
    public void setProperty(String key, String value);

    /**
     * Gets a property - this can be used to mark up the DataBook
     * with additional values for either rendering or display.
     * 
     * @param key		the property key
     * @return   	    the property value
     */
    public String getProperty(String key);
}
