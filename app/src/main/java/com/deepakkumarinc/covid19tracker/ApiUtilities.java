package com.deepakkumarinc.covid19tracker;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilities {

    public  static Retrofit retrofit;
//    public static final String BASEURL = "https://corona.lmao.ninja/v2/";

    public static ApiInterface getAPIInterface(){

        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder().
                    baseUrl(ApiInterface.BASEURL).addConverterFactory(GsonConverterFactory.create()).
                    build();
        }
        return  retrofit.create(ApiInterface.class);
    }
}
