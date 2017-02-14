package com.example.oshao.uiactivity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.oshao.share.GlobalVariable;

/**
 * Created by oshao on 2/14/2017.
 */

public class CountService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startCountThread();
    }

    private void startCountThread() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (GlobalVariable.getCount() < 1000) {

                    GlobalVariable.setCount(GlobalVariable.getCount() + 1);

                    Intent intent = new Intent("test");
                    intent.putExtra("count", GlobalVariable.getCount());

                    sendBroadcast(intent);

                    Log.d("CountService", "Background count is " + GlobalVariable.getCount());

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        thread.start();
    }


}
