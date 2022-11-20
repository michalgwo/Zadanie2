package com.example.zadanie2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        for (int i=0; i<=1;i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    for (int j=1;j<=20;j++) {
                        try {
                            int threadId = android.os.Process.myTid();
                            long sleepTime = new Random().nextInt(1000)+1;
                            Thread.sleep(sleepTime);
                            runOnUiThread(new Synchronizer(threadId, sleepTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    runOnUiThread(new Synchronizer("done"));
                }
            };

            thread.start();
        }
    }

    public class Synchronizer implements Runnable {
        int threadId;
        long sleepTime;
        String text;

        public Synchronizer(String text) {
            this.text = text;
        }

        public Synchronizer(int threadId, long sleepTime) {
            this.threadId = threadId;
            this.sleepTime = sleepTime;
        }

        public void run() {
            String currentString = textView.getText().toString();

            if (text != null && !text.equals("")) {
                textView.setText(currentString + text + "\n");
            } else {
                textView.setText(currentString + threadId + ":" + sleepTime + "\n");
            }
        }

    }
}