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

import java.util.List;
import java.util.ArrayList;

public class DataSheet {

    private String caption;

    private List<String> columns = new ArrayList<String>();
    private List<String> rows = new ArrayList<String>();
    private List data = new ArrayList();

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public void setColumnLabels(List<String> columns) {
        this.columns.clear();
        this.columns.addAll(columns);
    }

    public List getColumnLabels() {
        return columns;
    }

    public void setRowLabels(List<String> rows) {
        this.rows.clear();
        this.rows.addAll(rows);
    }

    public List getRowLabels() {
        return rows;
    }

    public List getData() {
        return data;
    }

    public void setValue(int column, int row, Object value) {
        while (row + 1 > data.size())
            data.add(new ArrayList());
        List rowBlock = (List) data.get(row);
        if (rowBlock == null) {
            data.set(row, rowBlock = new ArrayList());
        }
        while (column + 1 > rowBlock.size())
            rowBlock.add(null);
        rowBlock.set(column, value);
    }

    public Object getValue(int column, int row) {
        while (row + 1 > data.size())
            data.add(new ArrayList());
        ArrayList rowBlock = (ArrayList) data.get(row);
        if (rowBlock == null) {
            data.set(row, rowBlock = new ArrayList());
        }
        while (column + 1 > rowBlock.size())
            rowBlock.add(null);
        return rowBlock.get(column);
    }
}
