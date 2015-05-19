/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package discovery;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import discovery.request.DataRequest;

public abstract class Table {

  private static final String DATA = "data";
  private static final String DT_ROW_ID = "DT_RowId";
  private static final String RECORDS_FILTERED = "recordsFiltered";
  private static final String RECORDS_TOTAL = "recordsTotal";

  public abstract long getRecordTotal();

  public abstract List<String> getColumnNames();

  public abstract DiscoveryResultSet executeRequest(DataRequest dataRequest);

  public abstract String getTableId();

  public JSONObject handleRequest(DataRequest dataRequest) throws JSONException, IOException {
    DiscoveryResultSet resultSet = executeRequest(dataRequest);
    JSONObject response = new JSONObject();
    response.put(RECORDS_TOTAL, getRecordTotal());
    response.put(RECORDS_FILTERED, resultSet.getRecordCount());
    JSONArray data = new JSONArray();
    while (resultSet.next()) {
      String rowId = resultSet.getRowId();
      List<String> columnNames = resultSet.getColumnNames();
      JSONObject record = new JSONObject();
      record.put(DT_ROW_ID, rowId);
      for (String columnName : columnNames) {
        record.put(columnName, resultSet.getColumnValue(columnName));
      }
      data.put(record);
    }
    response.put(DATA, data);
    return response;
  }

}
