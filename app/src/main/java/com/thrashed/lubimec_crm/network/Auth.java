package com.thrashed.lubimec_crm.network;


import com.thrashed.lubimec_crm.models.UserModels;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Auth {




    @FormUrlEncoded
    @POST("login")
    Call<RegistrationResponse> registerUser(
    @Field ("username") String username,
    @Field("password") String password
    );

}


