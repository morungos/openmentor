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

import java.awt.Paint;
import java.awt.Color;
import java.awt.Font;

import org.jCharts.axisChart.AxisChart;
import org.jCharts.chartData.AxisChartDataSet;
import org.jCharts.chartData.ChartDataException;
import org.jCharts.chartData.DataSeries;
import org.jCharts.chartData.interfaces.IAxisDataSeries;
import org.jCharts.types.ChartType;
import org.jCharts.properties.AxisProperties;
import org.jCharts.properties.BarChartProperties;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.properties.PropertyException;
import org.jCharts.properties.util.ChartFont;

import uk.org.openmentor.report.properties.ReportBarChartProperties;
import uk.org.openmentor.report.constituents.ReportChart;

/**
 * Provides a bar chart implementation that makes use of the jCharts library
 * 
 * @author OpenMentor team
 */

class ReportBarChart extends ReportChart {
    private static final boolean DIRECTION_VERTICAL = false;

    private IAxisDataSeries dataSeries_;

    private LegendProperties legendProperties_;

    private AxisProperties axisProperties_;

    private ChartProperties chartProperties_;

    /**
     * Constructs a bar chart object using the properties supplied as an
     * argument
     * 
     * @param bcp
     *            The bar chart properties that describe the details of the
     *            chart
     */

    public ReportBarChart(ReportBarChartProperties bcp) {
        dataSeries_ = null;
        legendProperties_ = null;
        axisProperties_ = null;
        chartProperties_ = null;

        try {
            createDataSeries(bcp);
            createChart(bcp);
        } catch (ChartDataException cde) {
            // Need some suitable code in here to make sure we handle exceptions
            // during chart creation gracefully. Ideally we need to create
            // something
            // as a placeholder for the chart that also contains error
            // information
            // so that we can continue to process and render the report request
        } catch (PropertyException pe) {
            // As above - or combine to one
        }
    }

    private void createDataSeries(ReportBarChartProperties bcp)
            throws ChartDataException {
        dataSeries_ = new DataSeries(bcp.getAxisLabels(), bcp.getXAxisTitle(),
                bcp.getYAxisTitle(), bcp.getChartTitle());
        dataSeries_.addIAxisPlotDataSet(new AxisChartDataSet(
                new double[][] { convertData(bcp.getData()) },
                new String[] { bcp.getLegendLabel() },
                new Paint[] { Color.BLACK }, ChartType.BAR,
                new BarChartProperties()));

    }

    private void createChart(ReportBarChartProperties bcp)
            throws ChartDataException, PropertyException {
        setupProperties();
        this.chart = new AxisChart(dataSeries_, chartProperties_,
                                   axisProperties_, legendProperties_,
                                   bcp.getWidth(), bcp.getHeight());
        this.chart.renderWithImageMap();
    }

    private void setupProperties() {
        chartProperties_ = new ChartProperties();
        legendProperties_ = new LegendProperties();
        axisProperties_ = new AxisProperties(DIRECTION_VERTICAL);

        ChartFont axisScaleFont = new ChartFont(new Font("Arial", Font.PLAIN,
                12), Color.black);
        axisProperties_.getXAxisProperties().setScaleChartFont(axisScaleFont);
        axisProperties_.getYAxisProperties().setScaleChartFont(axisScaleFont);

        ChartFont axisTitleFont = new ChartFont(
                new Font("Arial", Font.BOLD, 14), Color.black);
        axisProperties_.getXAxisProperties().setAxisTitleChartFont(
                axisTitleFont);
        axisProperties_.getYAxisProperties().setAxisTitleChartFont(
                axisTitleFont);

        ChartFont titleFont = new ChartFont(new Font("Arial", Font.BOLD, 14),
                Color.black);
        chartProperties_.setTitleFont(titleFont);

        legendProperties_.setFont(new Font("Arial", Font.PLAIN, 12));
    }
}
