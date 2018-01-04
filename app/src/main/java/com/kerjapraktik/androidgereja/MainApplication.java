package com.kerjapraktik.androidgereja;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//untuk volley request di main activity
public class MainApplication extends Application {
    private static MainApplication sInstance;
    private RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
        sInstance = this;
    }

    public synchronized static MainApplication getsInstance(){
        return sInstance;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }

    public void addToRequestQueue(Request request){
        requestQueue.add(request);
    }
}
