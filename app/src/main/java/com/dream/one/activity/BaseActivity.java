package com.dream.one.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.dream.one.R;
import com.github.clans.fab.FloatingActionButton;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    FloatingActionButton fab;

    public void initRootView() {
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRootView();
        context = getApplicationContext();
        initView();
    }

    protected void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == fab.getId()) {
            fabClick();
        }

    }

    public void fabClick() {
        Toast.makeText(context, "Fab btn is Work", Toast.LENGTH_SHORT).show();
    }
}
