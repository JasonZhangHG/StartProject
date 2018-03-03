package com.example.jason.constellation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jason.constellation.R;
import com.example.jason.constellation.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartBookInfoActivity extends BaseActivity {

    @BindView(R.id.tv_title_start_book_info_activity) TextView tvTitle;
    @BindView(R.id.iv_share_start_book_info_activity) ImageView ivShare;
    @BindView(R.id.tv_start_te_zheng) TextView tvTeZheng;
    @BindView(R.id.tv_start_love) TextView tvLove;
    @BindView(R.id.tv_start_hu_dong) TextView tvHuDong;
    @BindView(R.id.tv_start_music) TextView tvMusic;

    private String[] name = {"白羊座", "金牛座", "双子座", "巨蟹座",
            "狮子座", "处女座", "天秤座", "天蝎座",
            "射手座", "魔蝎座", "水瓶座", "双鱼座"};

    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_book_info);
        ButterKnife.bind(this);
        mPosition = getIntent().getIntExtra("IntentToStartBookInfoActivity", 0);
        tvTitle.setText(name[mPosition]);
    }

    @OnClick(R.id.start_title_back_start_book_info_activity)
    public void onBackClicked(View view) {
        finish();
    }

    @OnClick(R.id.iv_share_start_book_info_activity)
    public void onShareClicked(View view) {

    }
}
