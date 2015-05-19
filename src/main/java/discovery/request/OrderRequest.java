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

public class OrderRequest {

  enum DIRECTION {
    ASC, DESC
  }

  private final int orderIndex;
  private final Integer columnIndex;
  private final DIRECTION direction;

  public OrderRequest(int orderIndex, Integer columnIndex, DIRECTION direction) {
    this.orderIndex = orderIndex;
    this.columnIndex = columnIndex;
    this.direction = direction;
  }

  public OrderRequest(int orderIndex, Integer columnIndex, String direction) {
    this(orderIndex, columnIndex, toDirection(direction));
  }

  private static DIRECTION toDirection(String direction) {
    if (direction == null) {
      return null;
    }
    return DIRECTION.valueOf(direction.toUpperCase());
  }

  public int getOrderIndex() {
    return orderIndex;
  }

  public Integer getColumnIndex() {
    return columnIndex;
  }

  public DIRECTION getDirection() {
    return direction;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((columnIndex == null) ? 0 : columnIndex.hashCode());
    result = prime * result + ((direction == null) ? 0 : direction.hashCode());
    result = prime * result + orderIndex;
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
    OrderRequest other = (OrderRequest) obj;
    if (columnIndex == null) {
      if (other.columnIndex != null)
        return false;
    } else if (!columnIndex.equals(other.columnIndex))
      return false;
    if (direction != other.direction)
      return false;
    if (orderIndex != other.orderIndex)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "OrderRequest [orderIndex=" + orderIndex + ", columnIndex=" + columnIndex + ", direction=" + direction + "]";
  }

}
