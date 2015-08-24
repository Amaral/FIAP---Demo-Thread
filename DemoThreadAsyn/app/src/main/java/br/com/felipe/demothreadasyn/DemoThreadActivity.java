package br.com.felipe.demothreadasyn;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


public class DemoThreadActivity extends ActionBarActivity {

    private ProgressBar progressBar;
    private Button btIniciar;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_thread);

        handler = new Handler();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btIniciar = (Button) findViewById(R.id.btIniciar);

        btIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 1; i <= 10; i++) {
                            final int value = i;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(value);
                                }
                            });
                        }
                    }
                };

                new Thread(runnable).start();
            }
        });
    }
}
