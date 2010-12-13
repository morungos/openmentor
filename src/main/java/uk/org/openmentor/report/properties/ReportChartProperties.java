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

package uk.org.openmentor.report.properties;

/**
 * Base class to provide common chart properties in a charting library
 * independent way giving setters and getters for the title, width,
 * height of the associated chart, with sensible defaults.
 *
 * @author OpenMentor team
 */
public class ReportChartProperties {

    /**
     * The default title of the associated chart.
     */
    public static final String DEFAULT_CHART_TITLE  = "Default chart title";

    /**
     * The default width of the associated chart.
     */
    public static final int DEFAULT_CHART_WIDTH  = 100;

    /**
     * The default height of the associated chart.
     */
    public static final int DEFAULT_CHART_HEIGHT = 100;

    /**
     * The title to use in the associated chart.
     */
    private String chartTitle;

    /**
     * The width to use in the associated chart.
     */
    private int width;

    /**
     * The height to use in the associated chart.
     */
    private int height;

    /**
     * Construct a chart property object with the specified title, width and
     * height.
     *
     * @param newChartTitle the title to use for the associated chart
     * @param newWidth      the width to use when drawing the associated chart
     * @param newHeight     the height to use when drawing the associated chart
     */

    public ReportChartProperties(final String newChartTitle,
                             final int newWidth, final int newHeight) {
        this.chartTitle = newChartTitle;
        this.width = newWidth;
        this.height = newHeight;
    }

    /**
     * Constructs a chart property object with default title, width and
     * height.
     */

    public ReportChartProperties() {
        this(DEFAULT_CHART_TITLE, DEFAULT_CHART_WIDTH, DEFAULT_CHART_HEIGHT);
    }

    /**
     * Gets the title to apply to the chart the properties are for.
     *
     * @return String reference to the chart title
     */

    public final String getChartTitle() {
        return this.chartTitle;
    }

    /**
     * Gets the width in pixels to use when plotting the associated chart.
     *
     * @return the width to plot the chart with
     */

    public final int getWidth() {
        return this.width;
    }

    /**
     * Gets the height in pixels to use when plotting the associated chart.
     *
     * @return the height to plot the chart with
     */

    public final int getHeight() {
        return this.height;
    }

    /**
     * Sets the title to apply to associated chart.
     *
     * @param newChartTitle String with the chart title to use
     */

    public final void setChartTitle(final String newChartTitle) {
        this.chartTitle = newChartTitle;
    }

    /**
     * Sets the width in pixels to use when plotting the associated chart.
     *
     * @param newWidth The width to use to plot the chart with
     */

    public final void setWidth(final int newWidth) {
        this.width = newWidth;
    }

    /**
     * Sets the height in pixels to use when plotting the associated chart.
     *
     * @param newHeight The height to use to plot the chart with
     */

    public final void setHeight(final int newHeight) {
        this.height = newHeight;
    }
}
