package com.example.zhiqiang_li.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EmojiLayout test = (EmojiLayout) findViewById(R.id.test);
        assert test != null;
        test.showData();
    }
}
