package br.com.felipe.demothreadasyn;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirThread(View v){
        Intent i = new Intent(getApplicationContext(), DemoThreadActivity.class);
        startActivity(i);
    }

    public void abrirAsync1(View v){
        Intent i = new Intent(getApplicationContext(), DownloadActivity.class);
        startActivity(i);
    }
    public void abrirAsync2(View v){
        Intent i = new Intent(getApplicationContext(), JsonActivity.class);
        startActivity(i);
    }
}
