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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import discovery.request.DataRequest;
import discovery.table.BaseDiscoveryResultSet;

public class TableListTable extends Table {

  private static final String RECORD_TOTAL = "Record Total";
  private static final String TABLE = "Table";
  private static final String INDEX = "_index";
  private final Map<String, Table> _tables;
  private final List<String> _columns;

  public TableListTable(Map<String, Table> tables) {
    _tables = tables;
    _columns = Arrays.asList(TABLE, RECORD_TOTAL);
  }

  @Override
  public long getRecordTotal() {
    return _tables.size();
  }

  @Override
  public List<String> getColumnNames() {
    return _columns;
  }

  @Override
  public DiscoveryResultSet executeRequest(DataRequest dataRequest) {
    long start = dataRequest.getStart();
    int length = dataRequest.getLength();
    List<Table> tables = new ArrayList<Table>(_tables.values());
    int max = (int) Math.min(length, tables.size() - start);
    return new BaseDiscoveryResultSet(dataRequest) {

      private int count = -1;

      @Override
      public boolean next() {
        count++;
        if (count < max) {
          return true;
        }
        return false;
      }

      @Override
      public String getRowId() {
        Table table = tables.get((int) getIndex(start, count));
        return table.getTableId();
      }

      @Override
      public long getRecordCount() {
        return tables.size();
      }

      @Override
      public Object getColumnValue(String columnName) throws IOException {
        Table table = tables.get((int) getIndex(start, count));
        if (columnName.equals(TABLE)) {
          return table.getTableId();
        } else if (columnName.equals(RECORD_TOTAL)) {
          return table.getRecordTotal();
        } else {
          throw new IOException("Column name [" + columnName + "] not found.");
        }
      }

      private long getIndex(long start, int count) {
        return count + start;
      }
    };
  }

  @Override
  public String getTableId() {
    return INDEX;
  }

}
