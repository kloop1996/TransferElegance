package com.hmarka.kloop1996.transferelegance.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kloop1996 on 15.06.2016.
 */
public class ResponseCreateOrder {

    @SerializedName("0")
    private Order order;

    public String getMessge(){return order.getMessage();}
    public int getOrderId(){return order.getData().getId();}

    public ResponseCreateOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    class Order{
        private String message;
        private OrderData data;


        public Order(String message, OrderData data) {
            this.message = message;
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public OrderData getData() {
            return data;
        }

        public void setData(OrderData data) {
            this.data = data;
        }

        class OrderData{
            @SerializedName("order_id")
            private int id;

            public OrderData(int id) {
                this.id = id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
