package com.thrashed.lubimec_crm.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Register {

    @FormUrlEncoded
    @POST("register")
    Call<RegistrationResponse> registrationUser(
            @Field("username") String username_reg,
            @Field("password") String password_reg,
            @Field("fio") String fio,
            @Field("role") String role
    );
}
