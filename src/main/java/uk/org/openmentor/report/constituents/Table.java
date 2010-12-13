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

import uk.org.openmentor.report.constituents.ReportConstituent;

/**
 * Provides a general table class that holds all the data needed to construct a
 * table in a user view and presents an iterator based interface that allows
 * access to the relevant data
 * 
 * @author OpenMentor team
 */

public class Table implements ReportConstituent {
    private static final int FIRST_ROW = 0;

    private int[][] data_;

    private String[] colTitles_;

    private String[] rowTitles_;

    private String tableTitle_;

    int cols_;

    int rows_;

    /**
     * Create a table object from the supplied data and title parameters.
     * 
     * @param data
     *            The arrays of data to use to construct the table. Supplying a
     *            null data array will result in a NullPpointerException being
     *            generated
     * @param colTitles
     *            The array of column titles to use. If this is null or shorter
     *            than the number of columns, any empty cells are given an empty
     *            string.
     * @param rowTitles
     *            The array of row titles to use. If this is null or shorter
     *            than the number of rows, any empty cells are given an empty
     *            string
     * @param tableTitle
     *            The title to use for the table. May be null in which case an
     *            empty string is used as the title of the table
     */
    public Table(int[][] data, String[] colTitles, String[] rowTitles,
            String tableTitle) {
        data_ = data;
        rows_ = data_.length;
        cols_ = data[FIRST_ROW].length;
        setupTitles(colTitles, rowTitles, tableTitle);
    }

    /**
     * Gets the number of columns of data the table represents
     * 
     * @return the number of columns
     */

    public int numColumns() {
        return cols_;
    }

    /**
     * Gets the number of rows of data the table represents
     * 
     * @return the number of rows
     */

    public int numRows() {
        return rows_;
    }

    /**
     * Gets the column titles to use in the table
     * 
     * @return The column titles to use
     */

    public String[] getColumnTitles() {
        return colTitles_;
    }

    /**
     * Gets the row titles to use in the table
     * 
     * @return The row titles to use
     */

    public String[] getRowTitles() {
        return rowTitles_;
    }

    /**
     * Gets the title to use to describe the table
     * 
     * @return The table title
     */

    public String getTableTitle() {
        return tableTitle_;
    }

    /**
     * Gets the data item at a given row and column position. It is the callers
     * responsibility to check that the request is not out of bounds or an
     * ArrayOutOfBounds exception will be generated.
     * 
     * @param row
     *            The row the data item is on. The row index is NOT a zero-based
     *            index.
     * @param col
     *            The column the data item is in. The column index is NOT a
     *            zero- based index.
     * @return The requested data item
     */

    public int dataItemAt(int row, int col) {
        return data_[row - 1][col - 1];
    }

    private void setupTitles(String[] colTitles, String[] rowTitles,
            String tableTitle) {
        if (tableTitle == null) {
            tableTitle_ = "";
        } else {
            tableTitle_ = tableTitle;
        }

        if (colTitles == null || colTitles.length < cols_) {
            colTitles_ = new String[cols_];

            for (int i = 0; i != cols_; ++i) {
                if (colTitles != null && i < colTitles.length) {
                    colTitles_[i] = colTitles[i];
                } else {
                    colTitles_[i] = "";
                }
            }
        } else {
            colTitles_ = colTitles;
        }

        if (rowTitles == null || rowTitles.length < rows_) {
            rowTitles_ = new String[rows_];

            for (int i = 0; i != rows_; ++i) {
                if (rowTitles != null && i < rowTitles.length) {
                    rowTitles_[i] = rowTitles[i];
                } else {
                    rowTitles_[i] = "";
                }
            }
        } else {
            rowTitles_ = rowTitles;
        }
    }
}
