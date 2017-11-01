package com.example.shbae.myprogress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    Handler handler = new Handler();

    CompletionThread completionThread;

    String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ProgressThread thread = new ProgressThread();
                        thread.start();
                    }
                }, 3000);
                //ProgressThread thread = new ProgressThread();
                //thread.start();
            }
        });

        completionThread = new CompletionThread();
        completionThread.start();
    }

    class ProgressThread extends Thread {
        int value = 0;

        public void run() {
            while(true) {
                if (value > 100)
                    break;

                value += 1;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(value);
                    }
                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            completionThread.completionHandler.post(new Runnable() {
                @Override
                public void run() {
                    msg = "OK";

                    Log.d("MainActivity", "메시지: " + msg);
                }
            });
        }
    }

    class CompletionThread extends Thread {
        public Handler completionHandler = new Handler();

        public void run() {
            Looper.prepare();
            Looper.loop();
        }
    }
}
