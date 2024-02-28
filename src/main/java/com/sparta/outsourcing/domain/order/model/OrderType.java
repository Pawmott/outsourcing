package com.sparta.outsourcing.domain.order.model;

public enum OrderType {
  WAITING("Waiting"),
  DELIVERY("On delivery"),
  COMPLETED("Delivery Completed"),
  CANCEL("Cancel");

  private final String orderStatus;

  OrderType(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String getOrderStatus() {
    return orderStatus;
  }
}
