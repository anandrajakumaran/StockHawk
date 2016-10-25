package com.sam_chordas.android.stockhawk.service;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by raanand on 10/20/2016.
 */

public class StockController extends Application {

    private static final String TAG = StockController.class.getSimpleName();
    private RequestQueue requestQueue;
    private static StockController mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized StockController getInstance(){
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(this.requestQueue == null){
            this.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return this.requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (this.requestQueue != null) {
            this.requestQueue.cancelAll(tag);
        }
    }
}
