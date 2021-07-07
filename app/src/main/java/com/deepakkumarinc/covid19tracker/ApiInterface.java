package com.deepakkumarinc.covid19tracker;

import com.deepakkumarinc.covid19tracker.Models.CoronaPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    public static final String BASEURL = "https://corona.lmao.ninja/v2/";

    @GET("countries")
    Call<List<CoronaPojo>> getCountryData();
}
