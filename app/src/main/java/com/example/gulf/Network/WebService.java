package com.example.gulf.Network;

import com.example.gulf.DataModel.Cliente;
import com.example.gulf.DataModel.DataModel;
import com.example.gulf.DataModel.NewUserModel;
import com.example.gulf.DataModel.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebService {
    @GET("clientes/{user}")
    Call<Cliente> getCliente(@Path("user") String user);
}
