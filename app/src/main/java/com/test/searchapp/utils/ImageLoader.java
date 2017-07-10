package com.test.searchapp.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.test.searchapp.R;

import java.io.IOException;
import java.io.InputStream;

public class ImageLoader extends AsyncTask<String, Void, Bitmap> {
    private ImageView loadImg;

    public ImageLoader(ImageView loadImg) {
        this.loadImg = loadImg;
        loadImg.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        loadImg.setImageBitmap(bitmap);
    }


}
