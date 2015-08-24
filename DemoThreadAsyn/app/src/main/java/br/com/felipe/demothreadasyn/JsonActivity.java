package br.com.felipe.demothreadasyn;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class JsonActivity extends ActionBarActivity {

    private ProgressDialog dialog;
    private final String URL_SERVICE = "http://times-futebol-api.herokuapp.com/api/time";
    private ServiceTask serviceTask;
    private Button btTimes;
    private ListView lvLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        btTimes = (Button) findViewById(R.id.btTimes);
        lvLista = (ListView) findViewById(R.id.lvLista);

        btTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(JsonActivity.this, "JSON","Baixando o json");
                serviceTask = new ServiceTask();
                serviceTask.execute(URL_SERVICE);
            }
        });
    }

    private class ServiceTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return listaTimes(params[0]);
            } catch (IOException ie) {
                ie.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            dialog.dismiss();
            if(json != null) {
                try {
                    JSONArray array = new JSONArray(json);
                    String[] lista = new String[array.length()];

                    for (int i = 0; i < array.length(); i++) {
                        lista[i] = array.getJSONObject(i).getString("nome");
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_item, android.R.id.text1, lista);
                    lvLista.setAdapter(adapter);

                } catch (JSONException je) {
                    je.printStackTrace();
                }

            }
        }

        private String listaTimes(String url) throws IOException {
            String content = "";
            try {
                URL urlService = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) urlService.openConnection();
                conn.setDoInput(true);
                conn.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();

                String linha = null;
                while ((linha = reader.readLine()) != null) {
                    sb.append(linha + "\n");
                }
                content = sb.toString();
            } catch (MalformedURLException me) {
                me.printStackTrace();
                return null;
            } catch (IOException ie) {
                ie.printStackTrace();
            }

            return content;
        }
    }


}
