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
 * Provides a facility for setting general bar chart properties in a charting
 * library independent fashion.
 *
 * @author OpenMentor team
 */

public class ReportBarChartProperties extends ReportChartProperties {

    /**
     * The default title to use for the x-axis of the associated bar chart.
     */
    public static final String   DEFAULT_X_AXIS_TITLE = "Default x-axis title";

    /**
     * The default title to use for the y-axis of the associated bar chart.
     */
    public static final String   DEFAULT_Y_AXIS_TITLE = "Default y-axis title";

    /**
     * The default legend label to describe the data series of the
     * associated bar chart.
     */
    public static final String   DEFAULT_LEGEND_LABEL = "Default legend label";

    /**
     * The default array of data to plot on the associated bar chart.
     */
    public static final int[]    DEFAULT_DATA         = {1, 2, 3};

    /**
     * The default tick mark labels to use on the axis of the
     * associated bar chart.
     */
    public static final String[] DEFAULT_AXIS_LABELS  = {"A", "B", "C"};

    /**
     * The title to use for the x-axis of the associated bar chart.
     */
    private String   xAxisTitle;

    /**
     * The title to use for the y-axis of the associated bar chart.
     */
    private String   yAxisTitle;

    /**
     * The legend label to describe the data series of the associated
     * bar chart.
     */
    private String   legendLabel;

    /**
     * The array of data to plot on the associated bar chart.
     */
    private int[]    data;

    /**
     * The tick mark labels to use on the axis of the associated bar
     * chart.
     */
    private String[] axisLabels;

  /**
   * Creates an instance of the property class using user supplied values. Note
   * that values must also be supplied for the base OMChartProperties instance
   * as well.
   *
   * @param newXAxisTitle  the title to use for the x-axis
   * @param newYAxisTitle  the title to use for the y-axis
   * @param newLegendLabel the label to use to describe the data series
   * @param newData        the array of data to plot
   * @param newAxisLabels  the tick mark labels to use on the axis
   * @param chartTitle     the title to use for the associated chart
   * @param width          the width to use when plotting the chart
   * @param height         the height to use when plotting the chart
   */

    public ReportBarChartProperties(final String newXAxisTitle,
                                final String newYAxisTitle,
                                final String newLegendLabel,
                                final int[] newData,
                                final String[] newAxisLabels,
                                final String chartTitle,
                                final int width,
                                final int height) {
        super(chartTitle, width, height);
        this.xAxisTitle = newXAxisTitle;
        this.yAxisTitle = newYAxisTitle;
        this.legendLabel = newLegendLabel;
        this.data = newData;
        this.axisLabels = newAxisLabels;
    }

    /**
     * Creates an instance of the property class with dummy values. Caller must
     * set values for a sensible chart to be produced
     */

    public ReportBarChartProperties() {
        this(DEFAULT_X_AXIS_TITLE, DEFAULT_Y_AXIS_TITLE,
             DEFAULT_LEGEND_LABEL, DEFAULT_DATA,
             DEFAULT_AXIS_LABELS, ReportChartProperties.DEFAULT_CHART_TITLE,
             ReportChartProperties.DEFAULT_CHART_WIDTH,
             ReportChartProperties.DEFAULT_CHART_HEIGHT);
    }

    /**
     * Gets the title to use for the x-axis for the associated chart.
     *
     * @return String with the x-axis title to use
     */
    public final String getXAxisTitle() {
        return this.xAxisTitle;
    }

    /**
     * Gets the title to use for the y-axis for the associated chart.
     *
     * @return String with the y-axis title to use
     */
    public final String getYAxisTitle() {
        return this.yAxisTitle;
    }

    /**
     * Gets the legend label to use with the plotted data set in the associated
     * chart.
     *
     * @return String with the legend label to use
     */
    public final String getLegendLabel() {
        return this.legendLabel;
    }

    /**
     * Gets the axis labels used to indicate the relevance of each
     * bar. Note that there should be one label for each data point in
     * the data array
     *
     * @return Array of strings with the labels
     */
    public final String[] getAxisLabels() {
        return this.axisLabels;
    }

    /**
     * Gets the data to use to plot the associated bar chart.
     *
     * @return Array containing the data
     */
    public final int[] getData() {
        return this.data;
    }

    /**
     * Sets the title to use for the x-axis in the associated chart.
     *
     * @param newXAxisTitle String with the title to use for the x-axis
     */
    public final void setXAxisTitle(final String newXAxisTitle) {
        this.xAxisTitle = newXAxisTitle;
    }

    /**
     * Sets the title to use for the y-axis iin the associated chart.
     *
     * @param newYAxisTitle String with the title to use for the y-axis
     */
    public final void setYAxisTitle(final String newYAxisTitle) {
        this.yAxisTitle = newYAxisTitle;
    }

    /**
     * Sets the legend label to use for the plotted data set in the associated
     * chart.
     *
     * @param newLegendLabel String with the legend label to use for
     *                       the data set
     */
    public final void setLegendLabel(final String newLegendLabel) {
        this.legendLabel = newLegendLabel;
    }

    /**
     * Sets the labels to use to indicate the meaning of each bar in the
     * associated chart.
     *
     * @param newAxisLabels The labels to use with the plot
     */
    public final void setAxisLabels(final String[] newAxisLabels) {
        this.axisLabels = newAxisLabels;
    }

    /**
     * Sets the data values to use to plot each bar of the associated chart.
     *
     * @param newData The data values to use to plot each bar
     */
    public final void setData(final int[] newData) {
        this.data = newData;
    }
}

