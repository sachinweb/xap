/*
 * Copyright (c) 2008-2016, GigaSpaces Technologies, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 *
 */
package com.j_spaces.jdbc.query;

import java.sql.SQLException;

/**
 * @author anna
 * @since 7.1
 */
@com.gigaspaces.api.InternalApi
public class AsterixColumnData
        extends QueryColumnData {
    /**
     * @throws SQLException
     */
    public AsterixColumnData()
            throws SQLException {
        super(QueryColumnData.ASTERIX_COLUMN);

    }

    /**
     * @param tableData
     * @throws SQLException
     */
    public AsterixColumnData(QueryTableData tableData)
            throws SQLException {
        super(tableData, QueryColumnData.ASTERIX_COLUMN);

        if (tableData != null)
            tableData.setAsterixSelectColumns(true);
    }

    @Override
    public void setColumnTableData(QueryTableData columnTableData) {
        super.setColumnTableData(columnTableData);

        if (columnTableData != null)
            columnTableData.setAsterixSelectColumns(true);

    }

    @Override
    public boolean checkAndAssignTableData(QueryTableData tableData)
            throws SQLException {
        // this is just an indicator of all columns - so no table data needs to be set
        QueryTableData columnTableData = getColumnTableData();
        if (columnTableData != null)
            return false;

        if (tableData != null)
            tableData.setAsterixSelectColumns(true);
        return true;
    }

    @Override
    public boolean isAsterixColumn() {
        return true;
    }

}
