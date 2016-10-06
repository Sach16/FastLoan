package com.whatsloan.dsa;

import android.support.multidex.MultiDexApplication;

import com.orm.SugarContext;

/**
 * Created by S.K. Pissay on 30/3/16.
 */
public class DSA extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
