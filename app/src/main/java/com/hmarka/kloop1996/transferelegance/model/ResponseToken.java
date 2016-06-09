package com.hmarka.kloop1996.transferelegance.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kloop1996 on 09.06.2016.
 */
public class ResponseToken {

    @SerializedName("0")
    private ResponseTokenData responseTokenData;

    public ResponseTokenData getResponseTokenData() {
        return responseTokenData;
    }

    public void setResponseTokenData(ResponseTokenData responseTokenData) {
        this.responseTokenData = responseTokenData;
    }

    public String getToken(){
        return responseTokenData.getData().getToken();
    }
    public String getMessage(){
        return responseTokenData.getMessage();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponseToken that = (ResponseToken) o;

        return responseTokenData != null ? responseTokenData.equals(that.responseTokenData) : that.responseTokenData == null;

    }

    @Override
    public int hashCode() {
        return responseTokenData != null ? responseTokenData.hashCode() : 0;
    }



    private class ResponseTokenData{
        private Token data;
        private String message;

        public ResponseTokenData(Token data, String message) {
            this.data = data;
            this.message = message;
        }

        public Token getData() {
            return data;
        }

        public void setData(Token data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ResponseTokenData that = (ResponseTokenData) o;

            if (data != null ? !data.equals(that.data) : that.data != null) return false;
            return message != null ? message.equals(that.message) : that.message == null;

        }

        @Override
        public int hashCode() {
            int result = data != null ? data.hashCode() : 0;
            result = 31 * result + (message != null ? message.hashCode() : 0);
            return result;
        }
    }


}
