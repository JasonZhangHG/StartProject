package com.example.jason.constellation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.jason.constellation.R;
import com.example.jason.constellation.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckStartActivity extends BaseActivity {

    @BindView(R.id.check_title_back) ImageView checkTitleBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_start2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.check_title_back)
    public void onBackClicked(View view) {
        finish();
    }
}
