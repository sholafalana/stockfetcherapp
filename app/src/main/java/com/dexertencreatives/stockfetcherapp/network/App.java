package com.dexertencreatives.stockfetcherapp.network;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context mcontext;

    @Override
    public void onCreate() {
        super.onCreate();
        mcontext = this;
    }

    public static Context getContext() {
        return mcontext;
    }
}
