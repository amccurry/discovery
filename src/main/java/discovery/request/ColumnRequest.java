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
package discovery.request;

public class ColumnRequest {

  private final int columnIndex;
  private final String dataName;
  private final String displayName;
  private final Boolean orderable;
  private final Boolean searchable;
  private final SearchRequest searchRequest;

  public ColumnRequest(int columnIndex, String dataName, String displayName, Boolean orderable, Boolean searchable,
      SearchRequest searchRequest) {
    this.columnIndex = columnIndex;
    this.dataName = dataName;
    this.displayName = displayName;
    this.orderable = orderable;
    this.searchable = searchable;
    this.searchRequest = searchRequest;
  }

  public int getColumnIndex() {
    return columnIndex;
  }

  public String getDataName() {
    return dataName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Boolean getOrderable() {
    return orderable;
  }

  public Boolean getSearchable() {
    return searchable;
  }

  public SearchRequest getSearchRequest() {
    return searchRequest;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + columnIndex;
    result = prime * result + ((dataName == null) ? 0 : dataName.hashCode());
    result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
    result = prime * result + ((orderable == null) ? 0 : orderable.hashCode());
    result = prime * result + ((searchRequest == null) ? 0 : searchRequest.hashCode());
    result = prime * result + ((searchable == null) ? 0 : searchable.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ColumnRequest other = (ColumnRequest) obj;
    if (columnIndex != other.columnIndex)
      return false;
    if (dataName == null) {
      if (other.dataName != null)
        return false;
    } else if (!dataName.equals(other.dataName))
      return false;
    if (displayName == null) {
      if (other.displayName != null)
        return false;
    } else if (!displayName.equals(other.displayName))
      return false;
    if (orderable == null) {
      if (other.orderable != null)
        return false;
    } else if (!orderable.equals(other.orderable))
      return false;
    if (searchRequest == null) {
      if (other.searchRequest != null)
        return false;
    } else if (!searchRequest.equals(other.searchRequest))
      return false;
    if (searchable == null) {
      if (other.searchable != null)
        return false;
    } else if (!searchable.equals(other.searchable))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "ColumnRequest [columnIndex=" + columnIndex + ", dataName=" + dataName + ", displayName=" + displayName
        + ", orderable=" + orderable + ", searchable=" + searchable + ", searchRequest=" + searchRequest + "]";
  }

}
