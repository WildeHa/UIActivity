package com.example.oshao.uiactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.oshao.share.GlobalVariable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final String ACTION_SLEEP_TIME_UIACTIVITY = "com.example.oshao.uiactivity.sleeptime";
    private final String KEY_SLEEP_TIME_UIACTIVITY = "com.example.oshao.uiactivity.sleeptime";

    private final String ACTION_READER_COUNT_CORESERVICE = "com.example.oshao.coreservice.actionreadercount";
    private final String KEY_READER_COUNT_CORESERVICE = "key_reader_count";

    private final String ACTION_LOOP_COUNT_CORESERVICE = "com.example.oshao.coreservice.actionloopcount";
    private final String KEY_LOOP_COUNT_CORESERVICE = "key_loop_count";

    //for test
    private final String TEST = "test";


    int progress = 10;

    int readerCount = 0;
    int loopCount = 0;

    private SeekBar seekBar;
    private TextView textViewInternal;
    private TextView textViewLooopCount;
    private TextView textViewServiceStatus;
    private TextView textViewReaderCount;
    private Button buttonGoConnection;
    private Button buttonClear;

    ArrayList<Integer> list = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textViewInternal = (TextView) findViewById(R.id.textView_internal);
        textViewLooopCount = (TextView) findViewById(R.id.textView_count);
        textViewServiceStatus = (TextView) findViewById(R.id.textView_service_status);
        textViewReaderCount = (TextView) findViewById(R.id.textView_reader_count);
        buttonGoConnection = (Button) findViewById(R.id.button_go_connection);
        buttonClear = (Button) findViewById(R.id.button_clear);

        textViewInternal.setText("Interval Modified : 0");
        textViewLooopCount.setText("Counting : 0");

//
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, CountService.class);
        startService(intent);


        ActivityReceiver activityReceiver = new ActivityReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_READER_COUNT_CORESERVICE);
        intentFilter.addAction(ACTION_LOOP_COUNT_CORESERVICE);

        //For test
        intentFilter.addAction(TEST);

        registerReceiver(activityReceiver, intentFilter);


        for (int j = 0; j < 1024 * 1024; j++) {

            list.add(j);

        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {


                int totalProgress = progress + i;

                Intent intent = new Intent(ACTION_SLEEP_TIME_UIACTIVITY);
                intent.putExtra(KEY_SLEEP_TIME_UIACTIVITY, totalProgress);
                intent.putExtra("mainactivitytime", System.currentTimeMillis());

                intent.putIntegerArrayListExtra("test", list);

                sendBroadcast(intent);

                Log.v("Timing", "Message: " + totalProgress + " Time: " + System.currentTimeMillis());


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    class ActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ACTION_READER_COUNT_CORESERVICE:
                    readerCount = intent.getIntExtra(KEY_READER_COUNT_CORESERVICE, 0);
                    textViewReaderCount.setText("Reader Counting : " + readerCount);
                    Log.v("MainActivity", "Reader Count is : " + readerCount);
                    break;
                case ACTION_LOOP_COUNT_CORESERVICE:
                    loopCount = intent.getIntExtra(KEY_LOOP_COUNT_CORESERVICE, 0);
                    textViewLooopCount.setText("Loop Counting :" + loopCount);
                    Log.v("MainActivity", "Loop Count is : " + loopCount);
                    break;

                //for test
                case TEST:
                    //int count = intent.getIntExtra("count", 0);
                    Log.d("MainActivity", "GlobalVariable.getCount() is " + GlobalVariable.getCount());
            }
        }
    }
}
