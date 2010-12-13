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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides utility function for chart generation using the jCharts library
 * 
 * @author OpenMentor team
 */

class ReportChartUtils {
    private static Log log = LogFactory.getLog(ReportChartUtils.class);

    private static final int COLOUR_CHANNELS = 3;

    private static final int CHANNEL_SIZE = 255;

    private static final int RED = 0;

    private static final int BLUE = 1;

    private static final int GREEN = 2;

    static Paint[] generatePaints(int numPaints) {
        // This slightly ugly code is used to automatically generate colours for
        // charts with more than one data series. It simply works out how many
        // different colours there needs to be and what increment needs to be
        // applied sequentially to red, green and blue values to get as big
        // a constrast as possible between colours

        int cycles = numPaints / COLOUR_CHANNELS;
        int rem = numPaints % COLOUR_CHANNELS;
        int inc = 0;

        if (rem != 0) {
            inc = CHANNEL_SIZE / (cycles + 1);
        } else {
            inc = CHANNEL_SIZE / cycles;
        }

        int red = 0;
        int green = 0;
        int blue = 0;

        int paint_index = 0;
        Paint[] p = new Paint[numPaints];
        for (int i = 0; i != cycles; ++i) {
            for (int j = 0; j != COLOUR_CHANNELS; ++j) {
                p[paint_index] = new Color(red, green, blue);
                if (log.isDebugEnabled()) {
                    log.debug("Initialising p[" + paint_index + "] as "
                            + p[paint_index]);
                }

                ++paint_index;

                switch (j % COLOUR_CHANNELS) {
                case RED:
                    red += inc;
                    break;
                case BLUE:
                    blue += inc;
                    break;
                case GREEN:
                    green += inc;
                    break;
                }
            }
        }
        // I restored this next section, removed by Hassan at revision
        // 437; I guess it doesn't matter, but it does cause one of the
        // existing tests to fail, since fewer colous are created and we
        // get a "uniqueness failure when color null is the same as color
        // null.
        for (int i = 0; i != rem; ++i) {
            p[paint_index] = new Color(red, green, blue);
            ++paint_index;

            if (i == 0) {
                red += inc;
            } else {
                blue += inc;
            }
        }

        //this is only for two set of colors for clustered and pie charts

        //two colors
        p[0] = new Color(16, 78, 139);
        p[1] = new Color(139, 117, 0);

        return p;
    }
}
