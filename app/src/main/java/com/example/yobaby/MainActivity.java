package com.example.yobaby;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    Button start, pause, reset;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;

    Handler handler = new Handler();

    int Seconds, Hours, Minutes, MilliSeconds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        start = (Button) findViewById(R.id.button);
        pause = (Button) findViewById(R.id.button2);
        reset = (Button) findViewById(R.id.button3);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = textView.getText().toString();

                final String data[] = str.split(":");

                int h = Integer.parseInt(data[0]);
                int m = Integer.parseInt(data[1]);
                int s = Integer.parseInt(data[2]);

                int multiply=0;

                if (h==0 && m==0 && s!=0){
                    multiply = s;
                }
                else if(h==0 && m!=0 && s!=0){
                    multiply = s*m;
                }
                else if(h!=0 && m!=0 && s!=0){
                    multiply = h*m*s;
                }

                StartTime = SystemClock.uptimeMillis() - (multiply*1000);
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L;
                StartTime = 0L;
                TimeBuff = 0L;
                UpdateTime = 0L;
                Seconds = 0;
                Hours = 0;
                Minutes = 0;
                MilliSeconds = 0;

                textView.setText("00:00:00:000");

            }
        });

    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Hours = Minutes / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);

            textView.setText("" + String.format("%02d", Hours) + ":"
                    + String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds)+ ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("previous",textView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String str1 = savedInstanceState.getString("previous");
        textView.setText(str1);
        String str = textView.getText().toString();

        final String data[] = str.split(":");

        int h = Integer.parseInt(data[0]);
        int m = Integer.parseInt(data[1]);
        int s = Integer.parseInt(data[2]);

        int multiply=0;

        if (h==0 && m==0 && s!=0){
            multiply = s;
        }
        else if(h==0 && m!=0 && s!=0){
            multiply = s*m;
        }
        else if(h!=0 && m!=0 && s!=0){
            multiply = h*m*s;
        }

        if(multiply!=0) {
            StartTime = SystemClock.uptimeMillis() - (multiply * 1000);
            handler.postDelayed(runnable, 0);
            reset.setEnabled(false);
        }
    }
}