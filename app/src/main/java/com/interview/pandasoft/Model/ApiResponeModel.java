package com.interview.pandasoft.Model;

import java.util.List;

public class ApiResponeModel {
    int status,expires_in;
    String message,access_token;
    List<DataApiResponse> data;

    public ApiResponeModel(int status, int expires_in, String message, String access_token, List<DataApiResponse> data) {
        this.status = status;
        this.expires_in = expires_in;
        this.message = message;
        this.access_token = access_token;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public List<DataApiResponse> getData() {
        return data;
    }

    public void setData(List<DataApiResponse> data) {
        this.data = data;
    }
}
