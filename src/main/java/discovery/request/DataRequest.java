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

import java.util.Collections;
import java.util.List;

public class DataRequest {

  private final int length;
  private final long start;
  private final List<ColumnRequest> columnRequests;
  private final List<OrderRequest> orderRequests;
  private final SearchRequest searchRequest;

  public DataRequest(int length, long start, List<ColumnRequest> columnRequests, List<OrderRequest> orderRequests,
      SearchRequest searchRequest) {
    this.length = length;
    this.start = start;
    this.columnRequests = Collections.unmodifiableList(columnRequests);
    this.orderRequests = Collections.unmodifiableList(orderRequests);
    this.searchRequest = searchRequest;
  }

  public int getLength() {
    return length;
  }

  public long getStart() {
    return start;
  }

  public List<ColumnRequest> getColumnRequests() {
    return columnRequests;
  }

  public List<OrderRequest> getOrderRequests() {
    return orderRequests;
  }

  public SearchRequest getSearchRequest() {
    return searchRequest;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((columnRequests == null) ? 0 : columnRequests.hashCode());
    result = prime * result + length;
    result = prime * result + ((orderRequests == null) ? 0 : orderRequests.hashCode());
    result = prime * result + ((searchRequest == null) ? 0 : searchRequest.hashCode());
    result = prime * result + (int) (start ^ (start >>> 32));
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
    DataRequest other = (DataRequest) obj;
    if (columnRequests == null) {
      if (other.columnRequests != null)
        return false;
    } else if (!columnRequests.equals(other.columnRequests))
      return false;
    if (length != other.length)
      return false;
    if (orderRequests == null) {
      if (other.orderRequests != null)
        return false;
    } else if (!orderRequests.equals(other.orderRequests))
      return false;
    if (searchRequest == null) {
      if (other.searchRequest != null)
        return false;
    } else if (!searchRequest.equals(other.searchRequest))
      return false;
    if (start != other.start)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "DataRequest [length=" + length + ", start=" + start + ", columnRequests=" + columnRequests
        + ", orderRequests=" + orderRequests + ", searchRequest=" + searchRequest + "]";
  }

}
