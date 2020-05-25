package com.example.gulf.Network;


import com.example.gulf.DataModel.AddPoints;
import com.example.gulf.DataModel.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddPointsService {
        @POST("clientes/puntos")
        Call<ResponseModel> addPoints(@Body AddPoints data);
}
