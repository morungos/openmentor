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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Arguably the DataBook interface is so general that there in no
 * point in having this implementation; although maybe we will want a
 * particularly stupid one sometime.
 *
 * The interface has been slightly tightened to match more the
 * original concept of the data book; i.e., something that is general
 * enough to be rendered as a chart or as a table, and usually by
 * category. Adding the category descriptions will also simplify a
 * lot of the later rendering.
 *
 * @author Hassan Sheikh
 */
public class DataBookImpl implements DataBook {

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(DataBookImpl.class);

    /** The list of points. */
    private List<String> points;
    
    /** The storage used for the data. */
    private Map<String, List> hmModelData = new HashMap<String, List>();
    
    /** The storage used for additional properties. */
    private Map<String, String> properties = new HashMap<String, String>();

    /**
     * Creates a new <code>DataBookImpl</code> instance.
     *
     */
    public DataBookImpl() {
    }

    /**
     * Sets the data points for the rendering; each data series
     * should have a value matching the corresponding data point. 
     *
     * @param value the data to be stored
     */
    public void setDataPoints(List<String> value) {
    	points = value;
        if (log.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("DataPoint list is: ");
            for (String s : value) {
                sb.append(s);
                sb.append(", ");
            }
            log.debug(sb.toString());
        }
    }
    
    /**
     * Returns the data points for the rendering; each data series
     * should have a value matching the corresponding data point. 
     *
     * @return		the List of data points
     */
    public List<String> getDataPoints() {
    	return points;
    }

    /**
     * Sets a property - this can be used to mark up the DataBook
     * with additional values for either rendering or display.
     * 
     * @param key		the property key
     * @param value		the property value
     */
    public void setProperty(String key, String value) {
    	properties.put(key, value);
    }

    /**
     * Gets a property - this can be used to mark up the DataBook
     * with additional values for either rendering or display.
     * 
     * @param key		the property key
     * @return   	    the property value
     */
    public String getProperty(String key) {
    	return (String) properties.get(key);
    }

    /**
     * Stores given data against a given key.
     *
     * @param key the key used for storage
     * @param value the data to be stored.
     */
    public void setDataSeries(String key, List value) {
        if (log.isDebugEnabled()) {
            log.debug("Storing against key " + key);
            StringBuilder sb = new StringBuilder();
            sb.append("DataSeries list is: ");
            for (Object o : value) {
                String tmp = o.toString();
                if (tmp.length() > 44) {
                    sb.append(tmp.substring(0,40));
                    sb.append("...");
                } else {
                    sb.append(tmp);
                }
                sb.append(", ");
            }
            log.debug(sb.toString());
        }
        hmModelData.put(key, value);
    } 

    /**
     * The <code>getDataSeries</code> returns data that was
     * previously stored against the given key.
     *
     * @param key the key to retrieve the data
     * @return the data as an <code>Object</code>.
     */
    public List<Object> getDataSeries(String key) {
        if (log.isDebugEnabled()) {
            log.debug("Retrieving against key " + key);
        }
        if (hmModelData.containsKey(key)) {
            return hmModelData.get(key);
        }
        return null;
    }
}
