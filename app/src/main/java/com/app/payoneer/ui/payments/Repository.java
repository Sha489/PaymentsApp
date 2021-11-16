package com.app.payoneer.ui.payments;

import com.app.payoneer.model.Payment;
import com.app.payoneer.network.ApiInterface;
import com.google.gson.JsonElement;

import io.reactivex.Observable;

public class Repository {

    private ApiInterface apiInterface;

    public Repository(ApiInterface apiCallInterface) {
        this.apiInterface = apiCallInterface;
    }

    /*
     * method to call login api
     * */
    public Observable<JsonElement> executeLogin() {
        return apiInterface.login();
    }

}
