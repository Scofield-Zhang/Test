package com.example.colorpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ColorPicker colorPicker = (ColorPicker) findViewById(R.id.color_picker);
        final ImageView iv = (ImageView) findViewById(R.id.iv);
        colorPicker.setOnColorDrawEndListener(new ColorPicker.OnColorDrawEndListener() {
            @Override
            public void onColorDrawableEnd(int color) {
                iv.setBackgroundColor(color);
            }
        });

    }
}
