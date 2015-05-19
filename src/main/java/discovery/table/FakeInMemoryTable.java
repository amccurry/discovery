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
package discovery.table;

import java.util.List;

import discovery.DiscoveryResultSet;
import discovery.Table;
import discovery.request.DataRequest;

public class FakeInMemoryTable extends Table {

  private String _id;
  private List<String> _cols;

  public FakeInMemoryTable(String id, List<String> columnNames) {
    _id = id;
    _cols = columnNames;
  }

  public long getRecordTotal() {
    return 1000000000000L;
  }

  public List<String> getColumnNames() {
    return _cols;
  }

  public String getTableId() {
    return _id;
  }

  @Override
  public DiscoveryResultSet executeRequest(DataRequest dataRequest) {
    System.out.println(dataRequest);
    long start = dataRequest.getStart();
    int length = dataRequest.getLength();
    return new BaseDiscoveryResultSet(dataRequest) {

      @Override
      public long getRecordCount() {
        return 100000l;
      }

      private int count = 0;

      @Override
      public boolean next() {
        if (count < length) {
          count++;
          return true;
        }
        return false;
      }

      @Override
      public String getRowId() {
        return Long.toString(start + count);
      }

      @Override
      public Object getColumnValue(String columnName) {
        return columnName + " " + getRowId();
      }

    };
  }
}
