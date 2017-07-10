package com.test.searchapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.test.searchapp.R;
import com.test.searchapp.utils.ImageLoader;

public class PhotoViewer extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_viewer);
        final ImageView photo = (ImageView) findViewById(R.id.bigPhoto);
        new ImageLoader(photo).execute(getIntent().getStringExtra("photo_url"));

        photo.animate()
                .setDuration(500)
                .scaleX(5f)
                .scaleY(5f)
                .start();

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo.animate()
                        .setDuration(100)
                        .scaleX(1f)
                        .scaleY(1f)
                        .start();
                finish();
            }
        });
    }
}
