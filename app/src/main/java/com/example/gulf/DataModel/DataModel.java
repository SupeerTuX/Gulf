package com.example.gulf.DataModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
//Modelo de datos para el metodo get del ususario
public class DataModel {

    private boolean error;
    private List<Cliente> msg;
    private int code;

    public DataModel(boolean error, List<Cliente> msg, int code) {
        this.error = error;
        this.msg = msg;
        this.code = code;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Cliente> getMessage() {
        return msg;
    }

    public void setMessage(List<Cliente> msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static Cliente parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        Cliente msg = gson.fromJson(response, Cliente.class);
        return msg;
    }
}
