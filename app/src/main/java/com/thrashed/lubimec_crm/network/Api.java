package com.thrashed.lubimec_crm.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    public static <S> S create(Class<S> api) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://thrashedbrain.hldns.ru:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(api);
    }

    public class Call<T> {
    }
}
