package com.example.oshao.share;

import android.app.Application;
import android.content.Context;

/**
 * Created by oshao on 2/14/2017.
 */

public class GlobalVariable extends Application {

    private static Context context;
    private static int count;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        GlobalVariable.context = context;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        GlobalVariable.count = count;
    }

//    public static MapPoint getMapPoint(String id, String domain)
//    {
//        // return from Couchbase Lite database
//        // example
//        // Query = ?view.createQuery()
//        // return mp;
//    }
}
