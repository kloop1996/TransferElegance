package com.hmarka.kloop1996.transferelegance.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kloop1996 on 19.06.2016.
 */
public class ResponseStatusOrder {
    @SerializedName("0")
    private StatusOrder statusOrder;

    public String getStatus(){return statusOrder.getData().getStatus();}
    public String getMessage(){
        if (getStatusOrder().getData().getStatus()==null){
            return "Unsuccsess";
        }else
            return "Succsses";
        }

    public String getTime(){return getStatusOrder().getData().getTaxiArrivalTime();}
    public ResponseStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    class StatusOrder{
        private String message;
        private Order data;

        public StatusOrder(String message, Order data) {
            this.message = message;
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Order getData() {
            return data;
        }

        public void setData(Order data) {
            this.data = data;
        }

        class Order{
            private String status;
            @SerializedName("taxi_arrival_time")
            private String taxiArrivalTime;

            private String price;
//            "taxi_arrival_time": "1:35:00",
//                    "price": 25,


            public Order(String status, String taxiArrivalTime, String price) {
                this.status = status;
                this.taxiArrivalTime = taxiArrivalTime;
                this.price = price;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTaxiArrivalTime() {
                return taxiArrivalTime;
            }

            public void setTaxiArrivalTime(String taxiArrivalTime) {
                this.taxiArrivalTime = taxiArrivalTime;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
