package com.example.user.realmwithretrofit;

import com.example.user.realmwithretrofit.Models.Examplerequest;
import com.example.user.realmwithretrofit.Models.ExamplerequestNew;
import com.example.user.realmwithretrofit.Models.Exampleresponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Gangadhar.G on 2/7/2018.
 */

public interface APIInterface {


    @POST("/api/DashBoard")
    Call<Exampleresponse> doGetListResources(@Body Examplerequest examplerequest);

    @GET("/users/ahmedrizwan")
    Call<ExamplerequestNew> doGetResponse();
}
