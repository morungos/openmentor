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

package uk.org.openmentor.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.org.openmentor.report.constituents.ChartFactory;
import uk.org.openmentor.report.exceptions.NoSuchChartLibException;
import uk.org.openmentor.report.exceptions.UnknownReportTypeException;
import uk.org.openmentor.report.properties.ReportClusteredBarChartProperties;
import uk.org.openmentor.report.constituents.ReportConstituent;
import uk.org.openmentor.report.constituents.Table;
import uk.org.openmentor.report.constituents.Text;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.openmentor.model.DataBook;

/**
 * Main rendering engine for reports.  Actually no renedering happens
 * here; this is in effect a filter that reformats data.
 *
 * @author Hassan Sheikh, significantly erwritten by Ian Craw in order
 *         to see what is going on.
 */
public class ReportRenderEngine {

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(ReportRenderEngine.class);

    /**
     * At the moment, there are only 4 categories i.e A, B, C and D.
     * Change this value accordingly if there are more categories.
     * But why can't we count them?
     */
    private static final int COMMENTS_CATEGORIES_COUNTER = 4;

    private String chartType;

    private DataBook dataBook;

    /**
     * The only useful constructor.
     *
     * @param newChartType the chart type to render.
     * @param newDataBook the data with which to render.
     * @exception NoSuchChartLibException if an error occurs.
     */
    public ReportRenderEngine(final String newChartType,
                              final DataBook newDataBook)
            throws NoSuchChartLibException {
        this.chartType = newChartType;
        this.dataBook = newDataBook;
    }

    /**
     * The heart of this (essentially static) class is relatively
     * simple; decide which type of chart is wanted and preparee the
     * data accordingly.
     *
     * @return the data in a suitable form.
     * @exception Exception if an error occurs
     */
    public List<ReportConstituent> renderRequest() throws Exception {
        List<ReportConstituent> chartData;
        if (this.chartType.equals("BarChart")) {
            // then creating the image
            chartData = generateClusteredChart();
        } else if (this.chartType.equals("SimpleTable")) {
            // then creating the image
            chartData = generateSimpleTable();
        } else {
            chartData = null;
        }
        return chartData;
    }

    /**
     * Modified considerably, now that we have added the concept
     * of data points to the DataBook. It returns an array as this
     * seems to be what we need to pass back to the charting 
     * side. 
     *
     * @return our preferred way of providing these data.
     */
    private String[] getCommentsCategoryArray() {
    	String[] values = new String[0];
        return (String[]) dataBook.getDataPoints().toArray(values);
    }

    /**
     * Again almost nothing happens; we simply unload and load data.
     * the complication comes because the array is bigger in one case
     * than the other, so that "this.chartType" is a hidden parameter
     * to this method.
     *
     * @return the required data.
     */
    private int[][] getCommentsPercentageArray() {
        int firstIndex = 1;
        if (this.chartType.equals("BarChart")) {
            firstIndex = 2;
        } else if (this.chartType.equals("SimpleTable")) {
            firstIndex = 3;
        } else {
            log.error("Unknown type.");
            return null;
        }
        int[][] commentsPercentageArray =
            new int[firstIndex][COMMENTS_CATEGORIES_COUNTER];
        int i = 0;
        ArrayList v;
        // Array to hold values of actual and ideal comments percentage
        // Put the actual data first, then ideal
        v = (ArrayList) this.dataBook.getDataSeries("ActualRange");
        for (Iterator e = v.iterator(); e.hasNext(); i++) {
            commentsPercentageArray[0][i] = ((Integer) e.next())
                    .intValue();
        }
        v = (ArrayList) this.dataBook.getDataSeries("IdealRange");
        i = 0;
        for (Iterator e = v.iterator(); e.hasNext(); i++) {
            commentsPercentageArray[1][i] = ((Integer) e.next())
                    .intValue();
        }
        if (this.chartType.equals("SimpleTable")) {
            for (i = 0; i < COMMENTS_CATEGORIES_COUNTER; i++) {
                //deviation of actual from ideal on third index which is 2
                commentsPercentageArray[2][i] =
                    commentsPercentageArray[0][i]
                    - commentsPercentageArray[1][i];
            }
        }
        return commentsPercentageArray;
    }

    /**
     * Here is how we build an OMClusteredBarChart.  Actually the
     * returned List consists of just one element (sigh!) in the
     * only case we use at present!
     *
     * @return a <code>List</code> value
     * @exception UnknownReportTypeException if an error occurs
     * @exception NoSuchChartLibException if an error occurs
     * @exception Exception if an error occurs
     */
    public List<ReportConstituent> generateClusteredChart()
        throws UnknownReportTypeException, NoSuchChartLibException,
               Exception {
        // The properties contain all that is needed to render the chart
        ReportClusteredBarChartProperties bcp = 
            new ReportClusteredBarChartProperties
            ("Categories", "Percent of Comments",
             new String[] {"Actual", "Ideal"},
             getCommentsPercentageArray(),
             getCommentsCategoryArray(),
             "Chart", 400, 300);
        // Now hand it off to jCharts.
        ChartFactory cf = new ChartFactory(
                ChartFactory.REGISTERED_LIBS[ChartFactory.JCHARTS]);
        List<ReportConstituent> constituents = 
        	new ArrayList<ReportConstituent>();
        constituents.add(cf.createClusteredBarChart(bcp));
        return constituents;
    }

    /**
     * And here is how we build a SimpleTable.
     *
     * @return a <code>List</code> value
     * @exception UnknownReportTypeException if an error occurs
     * @exception NoSuchChartLibException if an error occurs
     */
    public List<ReportConstituent> generateSimpleTable() 
    		throws UnknownReportTypeException, NoSuchChartLibException {
        List<ReportConstituent> constituents = new ArrayList<ReportConstituent>();
        constituents.add(new Text("Some text before the Table"));
        constituents.add
            (new Table(getCommentsPercentageArray(),
                       getCommentsCategoryArray(),
                       new String[] {"% of actual comments",
                                     "% of ideal comments",
                                     "Deviation of actual from ideal %"},
                       new String("All Data")));
        return constituents;
    }
}
