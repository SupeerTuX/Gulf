package com.example.gulf.DataModel;

//Modelo de respuesta del servidor para el parseo de JSON
public class ResponseModel {
    private String error;
    private String msg;
    private String code;

    public ResponseModel() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
