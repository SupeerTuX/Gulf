package com.example.gulf.Network;

import com.example.gulf.DataModel.NewUserModel;
import com.example.gulf.DataModel.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NewUser {
    @POST("reporte")
    Call<ResponseModel> postNewUser(@Body NewUserModel newUser);
}
