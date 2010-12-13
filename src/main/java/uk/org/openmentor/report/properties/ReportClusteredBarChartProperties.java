/*
 ====================================================================
 Copyright 2005     Openability Consulting and The Robert Gordon University

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ====================================================================
 */

package uk.org.openmentor.report.properties;

/**
 * Provides a charting library indepdent way of specifying properties for
 * clustered bar charts.
 * 
 * @author OpenMentor team
 */

public class ReportClusteredBarChartProperties extends ReportChartProperties {
    public static final String DEFAULT_X_AXIS_TITLE = "Default x-axis title";

    public static final String DEFAULT_Y_AXIS_TITLE = "Default y-axis title";

    public static final String[] DEFAULT_LEGEND_LABELS = { "Default legend label" };

    public static final int[][] DEFAULT_DATA = { { 1, 2, 3 }, { 3, 2, 1 } };

    public static final String[] DEFAULT_AXIS_LABELS = { "Ideal", "Actual" };

    private String xAxisTitle_;

    private String yAxisTitle_;

    private String[] legendLabels_;

    private int[][] data_;

    private String[] axisLabels_;

    /**
     * Creates an instance of the property class using user supplied values.
     * Note that values must also be supplied for the base OMChartProperties
     * instance as well.
     * 
     * @param xAxisTitle    the title to use for the x-axis
     * @param yAxisTitle    the title to use for the y-axis
     * @param legendLabels  the labels to use to describe the data series
     * @param data          the arrays of data to plot
     * @param axisLabels    the tick mark labels to use on the axis
     * @param chartTitle    the title to use for the associated chart
     * @param width         the width to use when plotting the chart
     * @param height        the height to use when plotting the chart
     */

    public ReportClusteredBarChartProperties(String xAxisTitle, String yAxisTitle,
            String[] legendLabels, int[][] data, String[] axisLabels,
            String chartTitle, int width, int height) {
        super(chartTitle, width, height);
        xAxisTitle_ = xAxisTitle;
        yAxisTitle_ = yAxisTitle;
        legendLabels_ = legendLabels;
        data_ = data;
        axisLabels_ = axisLabels;
    }

    /**
     * Creates an instance of the property class with dummy values. Caller must
     * set values for a sensible chart to be produced.
     */

    public ReportClusteredBarChartProperties() {
        xAxisTitle_ = DEFAULT_X_AXIS_TITLE;
        yAxisTitle_ = DEFAULT_Y_AXIS_TITLE;
        legendLabels_ = DEFAULT_LEGEND_LABELS;
        data_ = DEFAULT_DATA;
        axisLabels_ = DEFAULT_AXIS_LABELS;
    }

    /**
     * Gets the title to use for the x-axis for the associated chart.
     * 
     * @return String with the x-axis title to use
     */

    public String getXAxisTitle() {
        return xAxisTitle_;
    }

    /**
     * Gets the title to use for the y-axis for the associated chart.
     * 
     * @return String with the y-axis title to use
     */

    public String getYAxisTitle() {
        return yAxisTitle_;
    }

    /**
     * Gets the legend label to use with the plotted data set in the associated
     * chart.
     * 
     * @return Array with the legend labels to use
     */

    public String[] getLegendLabels() {
        return legendLabels_;
    }

    /**
     * Gets the axis labels used to indicate the relevance of each bar. Note
     * that there should be one label for each data point in the data array.
     * 
     * @return Array of strings with the labels
     */

    public String[] getAxisLabels() {
        return axisLabels_;
    }

    /**
     * Gets the data to use to plot the associated bar chart.
     * 
     * @return The data arrays
     */

    public int[][] getData() {
        return data_;
    }

    /**
     * Sets the title to use for the x-axis in the associated chart.
     * 
     * @param xAxisTitle    String with the title to use for the x-axis
     */

    public void setXAxisTitle(String xAxisTitle) {
        xAxisTitle_ = xAxisTitle;
    }

    /**
     * Sets the title to use for the y-axis iin the associated chart.
     * 
     * @param yAxisTitle    String with the title to use for the y-axis
     */

    public void setYAxisTitle(String yAxisTitle) {
        yAxisTitle_ = yAxisTitle;
    }

    /**
     * Sets the legend label to use for the plotted data set in the associated
     * chart.
     * 
     * @param legendLabels  The legend labels to use for the data set
     */

    public void setLegendLabels(String[] legendLabels) {
        legendLabels_ = legendLabels;
    }

    /**
     * Sets the labels to use to indicate the meaning of each bar in the
     * associated chart.
     * 
     * @param axisLabels    The labels to use with the plot
     */

    public void setAxisLabels(String[] axisLabels) {
        axisLabels_ = axisLabels;
    }

    /**
     * Sets the data values to use to plot each bar of the associated chart.
     * 
     * @param data          The data values to use to plot each bar
     */

    public void setData(int[][] data) {
        data_ = data;
    }
}
