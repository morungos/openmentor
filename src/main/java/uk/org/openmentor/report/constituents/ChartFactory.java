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

import uk.org.openmentor.report.properties.ReportBarChartProperties;
import uk.org.openmentor.report.exceptions.NoSuchChartLibException;
import uk.org.openmentor.report.constituents.ReportBarChart;
import uk.org.openmentor.report.constituents.ReportClusteredBarChart;
import uk.org.openmentor.report.properties.ReportClusteredBarChartProperties;

/**
 * Provides a uniform mechanism for creating chart regardless of the underlying
 * mechanism required by different charting.
 * 
 * @author OpenMentor team
 */

public class ChartFactory {
    public static final String[] REGISTERED_LIBS = { "jCharts" };

    public static final int JCHARTS = 0;

    private static final int NO_REGISTERED_LIB = -1;

    private int libIndex_;

    /**
     * Creates the factory instance by identifying the charting library that the
     * factory should use to instantiate charts.
     * 
     * @param library
     *            String with the charting library to use with the factory
     */

    public ChartFactory(String library) throws NoSuchChartLibException {
        if ((libIndex_ = checkLib(library)) == REGISTERED_LIBS.length) {
            libIndex_ = NO_REGISTERED_LIB;
            throw new NoSuchChartLibException("The specified chart library ('"
                    + library + "') isn't registered "
                    + "for use in OpenMentor");
        }
    }

    /**
     * Generic mechanism for creating bar charts based on a general set of
     * information from the parser.
     * 
     * @param bcp
     *            The general bar chart properties to use with the chart
     * @return IChart reference with the created chart
     */

    public ChartConstituent createBarChart(ReportBarChartProperties bcp) {
        ChartConstituent c = null;

        switch (libIndex_) {
        case NO_REGISTERED_LIB:
            System.err
                    .println("ERROR: createBarChart() with no registered lib");

        case JCHARTS:
            c = createJCBarChart(bcp);
            break;
        }

        return c;
    }

    /**
     * Generic mechanism for creating clustered bar charts based on a general
     * set of information from the parser.
     * 
     * @param bcp
     *            The clustered bar chart properties to use with the chart
     * @return IChart reference with the created chart
     */

    public ChartConstituent createClusteredBarChart(ReportClusteredBarChartProperties bcp) {
        ChartConstituent c = null;

        switch (libIndex_) {
        case NO_REGISTERED_LIB:
            System.err.println("ERROR: createClusteredBarChart() with no "
                    + "registered lib");

        case JCHARTS:
            c = createJCClusteredBarChart(bcp);
            break;
        }

        return c;
    }

    private ChartConstituent createJCBarChart(ReportBarChartProperties bcp) {
        return new ReportBarChart(bcp);
    }

    private ChartConstituent createJCClusteredBarChart(ReportClusteredBarChartProperties bcp) {

        try {
            return new ReportClusteredBarChart(bcp);
        } catch (Exception ex) {
            System.out.println("Error Chart Factory: " + ex.getMessage());
            return null;
        }
    }

    private int checkLib(String library) {
        int check = 0;

        while (check != REGISTERED_LIBS.length
                && !library.equals(REGISTERED_LIBS[check])) {
            ++check;
        }

        return check;
    }
}
