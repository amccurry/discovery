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

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import discovery.request.DataRequest;

public abstract class Table {

  public abstract long getRecordTotal();

  public abstract List<String> getColumnNames();

  public JSONObject handleRequest(DataRequest dataRequest) {
    JSONObject response = new JSONObject();
    response.put("recordsTotal", getRecordTotal());
    response.put("recordsFiltered", 1000000000000L);
    JSONArray data = new JSONArray();
    for (long l = 0; l < 10; l++) {
      JSONObject record = new JSONObject();
      record.put("DT_RowId", "row_" + l);
      record.put("DT_RowData", getKey(Long.toString(l)));
      record.put("col0", "val");
      record.put("col1", "val");
      data.put(record);
    }
    response.put("data", data);
    return response;
  }

  private static JSONObject getKey(String key) {
    JSONObject object = new JSONObject();
    object.put("k", key);
    return object;
  }

  public abstract String getTableId();

}
