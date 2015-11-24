package com.dream.one.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dream.one.R;
import com.github.clans.fab.FloatingActionButton;

public class BaseActivity extends Activity {

    Context context;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        context = getApplicationContext();
        initView();
    }

    protected void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Fab btn is Work", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
