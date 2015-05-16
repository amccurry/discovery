package discovery;

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

import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Helloworld {

  public static void main(String[] args) {
    staticFileLocation("/webapp");
    get("/hello", (req, res) -> "Hello World!");
    get("/data-source", (req, res) -> {

      Map<String, String> params = req.params();
      String startPosition = req.params("start");
      System.out.println(startPosition);
      
      System.out.println(params);

      String drawStr = params.get("draw");

      JSONObject jsonObject = new JSONObject();
      if (drawStr != null) {
        jsonObject.put("draw", Integer.parseInt(drawStr));
      } else {
        jsonObject.put("draw", 1);
      }

      // recordsTotal
      // recordsFiltered
      // data
      // error
        jsonObject.put("recordsTotal", 1000000000000L);
        jsonObject.put("recordsFiltered", 1000000000000L);
        JSONArray data = new JSONArray();
        for (long l = 0; l < 10; l++) {
          JSONObject record = new JSONObject();
          record.put("DT_RowId", "row_" + l);
          record.put("DT_RowData", getKey(Long.toString(l)));
          record.put("col0", "val0");
          record.put("col1", "val1");
          data.put(record);
        }
        jsonObject.put("data", data);

        return jsonObject.toString(1);
      });
  }

  private static JSONObject getKey(String key) {
    JSONObject object = new JSONObject();
    object.put("k", key);
    return object;
  }

}
