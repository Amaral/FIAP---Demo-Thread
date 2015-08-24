package br.com.felipe.demothreadasyn;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DownloadActivity extends ActionBarActivity {

    private ProgressDialog dialog;
    private ImageView ivImagem;
    private DownloadImageTask downloadImageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        ivImagem = (ImageView) findViewById(R.id.ivImagem);
    }

    @Override
    protected void onDestroy(){
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }

        if(downloadImageTask != null) {
            downloadImageTask.cancel(true);
        }

        super.onDestroy();
    }


    public void downloadImagem(View v) {
        dialog = ProgressDialog.show(DownloadActivity.this, "Download","Baixando a imagem");
        downloadImageTask = new DownloadImageTask();
        downloadImageTask.execute("http://img2.wikia.nocookie.net/__cb20121101053626/logopedia/images/5/5f/512px-Android_robot.png");
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                return downloadBitmap(params[0]);
            } catch (IOException io) {
                io.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap imagem) {
            super.onPostExecute(imagem);
            dialog.dismiss();

            if(imagem != null) {

                ivImagem.setImageBitmap(imagem);
            }
        }

        private Bitmap downloadBitmap(String url) throws IOException {
            URL imageURL = null;
            Bitmap bitmapImage = null;

            try {
                imageURL = new URL(url);
            } catch (MalformedURLException urle) {
                urle.printStackTrace();
                return null;
            }

            try {
                HttpURLConnection conn = (HttpURLConnection) imageURL.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmapImage = BitmapFactory.decodeStream(is);
            } catch (IOException ie) {
                ie.printStackTrace();
            }
            return bitmapImage;
        }
    }

}
