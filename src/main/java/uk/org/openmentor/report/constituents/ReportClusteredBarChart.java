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

package uk.org.openmentor.report.constituents;

import java.awt.Color;
import java.awt.Font;

import org.jCharts.axisChart.AxisChart;
import org.jCharts.chartData.AxisChartDataSet;
import org.jCharts.chartData.ChartDataException;
import org.jCharts.chartData.DataSeries;
import org.jCharts.types.ChartType;
import org.jCharts.chartData.interfaces.IAxisDataSeries;
import org.jCharts.properties.AxisProperties;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.ClusteredBarChartProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.properties.PropertyException;
//import org.jCharts.chartData.interfaces.*;
import org.jCharts.properties.util.ChartFont;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.openmentor.report.properties.ReportClusteredBarChartProperties;

/**
 * Provides a clustered bar chart implementation using the jCharts
 * library.
 */
public class ReportClusteredBarChart extends ReportChart {

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(ReportClusteredBarChart.class);

    /**
     * Whether the chart is vertival.
     */
    private static final boolean DIRECTION_VERTICAL = false;

    /**
     * The chart data series.
     */
    private IAxisDataSeries dataSeries;

    /**
     * The chart legend properties.
     */
    private LegendProperties legendProperties;

    /**
     * The chart axis properties.
     */
    private AxisProperties axisProperties;

    /**
     * The other chart properties.
     */
    private ChartProperties chartProperties;

    /**
     * Constructs a clustered bar chart object using the properties
     * supplied.
     *
     * @param bcp The clustered bar chart properties that describe the
     *            details of the chart.
     */

    public ReportClusteredBarChart(final ReportClusteredBarChartProperties bcp) {
        this.dataSeries = null;
        this.legendProperties = null;
        this.axisProperties = null;
        this.chartProperties = null;

        try {
            createDataSeries(bcp);
            createChart(bcp);
        } catch (ChartDataException cde) {
            log.warn("CDE: " + cde.getMessage());
            // Need some suitable code in here to make sure we handle
            // exceptions during chart creation gracefully. Ideally we
            // need to create something as a placeholder for the chart
            // that also contains error information so that we can
            // continue to process and render the report request
        } catch (PropertyException pe) {
            log.warn("PE: " + pe.getMessage());
        }
    }

    /**
     * Describe <code>createDataSeries</code> method here.
     *
     * @param bcp an <code>OMClusteredBarChartProperties</code> value
     * @exception ChartDataException if an error occurs
     */
    private void createDataSeries(final ReportClusteredBarChartProperties bcp)
            throws ChartDataException {
        if (log.isDebugEnabled()) {
            log.debug("Creating dataSeries");
        }
        this.dataSeries = new DataSeries(bcp.getAxisLabels(),
                                         bcp.getXAxisTitle(),
                                         bcp.getYAxisTitle(),
                                         bcp.getChartTitle());
        this.dataSeries.addIAxisPlotDataSet
            (new AxisChartDataSet(convertData(bcp.getData()),
                                  bcp.getLegendLabels(),
                                  ReportChartUtils.generatePaints
                                  (bcp.getData().length),
                                  ChartType.BAR_CLUSTERED,
                                  new ClusteredBarChartProperties()));

    }

    /**
     * Describe <code>createChart</code> method here.
     *
     * @param bcp an <code>OMClusteredBarChartProperties</code> value
     * @exception ChartDataException if an error occurs
     * @exception PropertyException if an error occurs
     */
    private void createChart(final ReportClusteredBarChartProperties bcp)
            throws ChartDataException, PropertyException {
        setupProperties();
        this.chart = new AxisChart(this.dataSeries,
                                   this.chartProperties,
                                   this.axisProperties,
                                   this.legendProperties,
                                   bcp.getWidth(), bcp.getHeight());
        this.chart.renderWithImageMap();
    }

    private void setupProperties() {
        this.chartProperties = new ChartProperties();
        this.legendProperties = new LegendProperties();
        this.axisProperties = new AxisProperties(DIRECTION_VERTICAL);

        ChartFont axisScaleFont = new
            ChartFont(new Font("Arial", Font.PLAIN, 12), Color.black);
        this.axisProperties.getXAxisProperties().
            setScaleChartFont(axisScaleFont);
        this.axisProperties.getYAxisProperties().
            setScaleChartFont(axisScaleFont);

        ChartFont axisTitleFont = new
            ChartFont(new Font("Arial", Font.BOLD, 14), Color.black);
        this.axisProperties.getXAxisProperties().
            setAxisTitleChartFont(axisTitleFont);
        this.axisProperties.getYAxisProperties().
            setAxisTitleChartFont(axisTitleFont);

        ChartFont titleFont = new
            ChartFont(new Font("Arial", Font.BOLD, 14), Color.black);
        this.chartProperties.setTitleFont(titleFont);
        this.legendProperties.setFont(new Font("Arial", Font.PLAIN, 12));
    }
}
