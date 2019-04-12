package com.dexertencreatives.stockfetcherapp.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by shola on 11/9/2018.
 */

public class NetworkSingleton {
    private static NetworkSingleton mInstance;
    private RequestQueue mRequestQueue;

    //Only use one request Queue
    private NetworkSingleton(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized NetworkSingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkSingleton(context);
        }

        return mInstance;
    }


    // Get Reference to Request Queue
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
