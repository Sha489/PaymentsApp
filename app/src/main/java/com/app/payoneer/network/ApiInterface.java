package com.app.payoneer.network;

import com.app.payoneer.model.Payment;
import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET(Urls.PAYMENTS_URL)
    Observable<JsonElement> login();
}
