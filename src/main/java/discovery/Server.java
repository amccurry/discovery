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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;
import discovery.request.ColumnRequest;
import discovery.request.DataRequest;
import discovery.request.OrderRequest;
import discovery.request.SearchRequest;
import discovery.table.FakeInMemoryTable;

public class Server {
  private static final String PARAM_PREFIX = ":";
  private static final String TABLE_URL = "/table/";
  private static final String DATA_SOURCE_URL = "/data-source/";
  private static final String TABLE_FTL = "table.ftl";
  private static final String PROCESSING = "processing";
  private static final String SERVER_SIDE = "serverSide";
  private static final String AJAX = "ajax";
  private static final String COLUMN_NAMES = "columnNames";
  private static final String DATA_TABLE_SETUP = "dataTableSetup";
  private static final String COLUMN = "column";
  private static final String DIR = "dir";
  private static final String DATA = "data";
  private static final String NAME = "name";
  private static final String ORDERABLE = "orderable";
  private static final String SEARCHABLE = "searchable";
  private static final String SEARCH = "search";
  private static final String REGEX = "regex";
  private static final String VALUE = "value";
  private static final String COLUMNS = "columns";
  private static final String ORDER = "order";
  private static final String LENGTH = "length";
  private static final String START = "start";
  private static final String TABLE_ID = "tableid";
  private static final String DRAW = "draw";

  private final static Map<String, Table> tables = new ConcurrentHashMap<String, Table>();

  public static void main(String[] args) {

    register(new FakeInMemoryTable("12345", Arrays.asList("col0", "col1", "col2")));
    register(new FakeInMemoryTable("col", Arrays.asList("col0", "col1", "col2")));
    TableListTable indexTable = new TableListTable(tables);
    register(indexTable);

    staticFileLocation("/webapp");
    get(DATA_SOURCE_URL + PARAM_PREFIX + TABLE_ID, (req, res) -> {
      String tableId = req.params(TABLE_ID);
      Table table = tables.get(tableId);
      DataRequest dataRequest = createDataRequest(req.queryMap());
      return handleDraw(req, table.handleRequest(dataRequest)).toString();
    });
    get(TABLE_URL + PARAM_PREFIX + TABLE_ID, (req, res) -> {
      String tableId = req.params(TABLE_ID);
      Table table = tables.get(tableId);
      Map<String, Object> attributes = getAttributes(table);
      return new ModelAndView(attributes, TABLE_FTL);
    }, new FreeMarkerEngine());
    get("/", (req, res) -> {
      Map<String, Object> attributes = getAttributes(indexTable);
      return new ModelAndView(attributes, TABLE_FTL);
    }, new FreeMarkerEngine());
  }

  private static void register(Table table) {
    tables.put(table.getTableId(), table);
  }

  private static Map<String, Object> getAttributes(Table table) {
    String tableId = table.getTableId();
    List<String> columnNames = table.getColumnNames();
    Map<String, Object> attributes = new HashMap<>();
    JSONObject dataTableDesc = new JSONObject();
    dataTableDesc.put(PROCESSING, true);
    dataTableDesc.put(SERVER_SIDE, true);
    dataTableDesc.put(AJAX, DATA_SOURCE_URL + tableId);
    JSONArray cols = new JSONArray();
    for (String col : columnNames) {
      cols.put(getJSONObject(DATA, col));
    }
    dataTableDesc.put(COLUMNS, cols);
    attributes.put(DATA_TABLE_SETUP, dataTableDesc.toString(1));
    attributes.put(TABLE_ID, tableId);
    attributes.put(COLUMN_NAMES, columnNames);
    return attributes;
  }

  private static JSONObject getJSONObject(String name, String value) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(name, value);
    return jsonObject;
  }

  private static JSONObject handleDraw(Request req, JSONObject response) {
    String drawStr = req.queryParams(DRAW);
    if (drawStr != null) {
      response.put(DRAW, Integer.parseInt(drawStr));
    } else {
      response.put(DRAW, 1);
    }
    return response;
  }

  private static DataRequest createDataRequest(QueryParamsMap queryMap) {
    SearchRequest searchRequest = createSearchRequest(queryMap);
    List<ColumnRequest> columnRequests = createColumnRequestList(queryMap.get(COLUMNS));
    int length = queryMap.get(LENGTH).integerValue();
    long start = queryMap.get(START).longValue();
    List<OrderRequest> orderRequests = createOrderRequestList(queryMap.get(ORDER));
    return new DataRequest(length, start, columnRequests, orderRequests, searchRequest);
  }

  private static List<OrderRequest> createOrderRequestList(QueryParamsMap queryMap) {
    List<OrderRequest> orderRequestList = new ArrayList<OrderRequest>();
    int order = 0;
    while (true) {
      String orderIdStr = Integer.toString(order);
      QueryParamsMap orderMap = queryMap.get(orderIdStr);
      if (!orderMap.hasKeys()) {
        return orderRequestList;
      }
      orderRequestList.add(createOrderRequest(order, orderMap));
      order++;
    }
  }

  private static OrderRequest createOrderRequest(int orderIndex, QueryParamsMap queryMap) {
    Integer columnIndex = queryMap.get(COLUMN).integerValue();
    String dir = queryMap.get(DIR).value();
    return new OrderRequest(orderIndex, columnIndex, dir);
  }

  private static List<ColumnRequest> createColumnRequestList(QueryParamsMap queryMap) {
    List<ColumnRequest> columnRequestList = new ArrayList<ColumnRequest>();
    int col = 0;
    while (true) {
      String colIdStr = Integer.toString(col);
      QueryParamsMap columnMap = queryMap.get(colIdStr);
      if (!columnMap.hasKeys()) {
        return columnRequestList;
      }
      columnRequestList.add(createColumnRequest(col, columnMap));
      col++;
    }
  }

  private static ColumnRequest createColumnRequest(int columnIndex, QueryParamsMap queryMap) {
    String dataName = queryMap.get(DATA).value();
    String displayName = queryMap.get(NAME).value();
    Boolean orderable = queryMap.get(ORDERABLE).booleanValue();
    Boolean searchable = queryMap.get(SEARCHABLE).booleanValue();
    SearchRequest searchRequest = createSearchRequest(queryMap.get(SEARCH));
    return new ColumnRequest(columnIndex, dataName, displayName, orderable, searchable, searchRequest);
  }

  private static SearchRequest createSearchRequest(QueryParamsMap queryMap) {
    QueryParamsMap search = queryMap.get(SEARCH);
    Boolean regex = search.get(REGEX).booleanValue();
    String value = search.get(VALUE).value();
    return new SearchRequest(regex, value);
  }

}
