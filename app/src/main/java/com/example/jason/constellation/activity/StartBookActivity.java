package com.example.jason.constellation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.jason.constellation.R;
import com.example.jason.constellation.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartBookActivity extends BaseActivity {

    @BindView(R.id.tv_bai_yang) TextView tvBaiYang;
    @BindView(R.id.tv_jin_niu) TextView tvJinNiu;
    @BindView(R.id.tv_shuang_zhi) TextView tvShuangZhi;
    @BindView(R.id.tv_jv_xie) TextView tvJvXie;
    @BindView(R.id.tv_shi_zhi) TextView tvShiZhi;
    @BindView(R.id.tv_chu_nv) TextView tvChuNv;
    @BindView(R.id.tv_tian_ping) TextView tvTianPing;
    @BindView(R.id.tv_tian_xie) TextView tvTianXie;
    @BindView(R.id.tv_she_shou) TextView tvSheShou;
    @BindView(R.id.tv_mo_xie) TextView tvMoXie;
    @BindView(R.id.tv_sui_ping) TextView tvSuiPing;
    @BindView(R.id.tv_shuang_yu) TextView tvShuangYu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_book);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_bai_yang, R.id.tv_jin_niu, R.id.tv_shuang_zhi, R.id.tv_jv_xie, R.id.tv_shi_zhi, R.id.tv_chu_nv
            , R.id.tv_tian_ping, R.id.tv_tian_xie, R.id.tv_she_shou, R.id.tv_mo_xie, R.id.tv_sui_ping, R.id.tv_shuang_yu})
    public void onClicked(View view) {
        Intent intent = new Intent(this, StartBookInfoActivity.class);
        switch (view.getId()) {
            //白羊座
            case R.id.tv_bai_yang:
                intent.putExtra("IntentToStartBookInfoActivity", 0);
                break;
            //金牛座
            case R.id.tv_jin_niu:
                intent.putExtra("IntentToStartBookInfoActivity", 1);
                break;

            //双子座
            case R.id.tv_shuang_zhi:
                intent.putExtra("IntentToStartBookInfoActivity", 2);
                break;
            //巨蟹座
            case R.id.tv_jv_xie:
                intent.putExtra("IntentToStartBookInfoActivity", 3);
                break;
            //狮子座
            case R.id.tv_shi_zhi:
                intent.putExtra("IntentToStartBookInfoActivity", 4);
                break;
            //处女座
            case R.id.tv_chu_nv:
                intent.putExtra("IntentToStartBookInfoActivity", 5);
                break;
            //天秤座
            case R.id.tv_tian_ping:
                intent.putExtra("IntentToStartBookInfoActivity", 6);
                break;
            //天蝎座
            case R.id.tv_tian_xie:
                intent.putExtra("IntentToStartBookInfoActivity", 7);
                break;
            //射手座
            case R.id.tv_she_shou:
                intent.putExtra("IntentToStartBookInfoActivity", 8);
                break;
            //魔蝎座
            case R.id.tv_mo_xie:
                intent.putExtra("IntentToStartBookInfoActivity", 9);
                break;
            //水瓶座
            case R.id.tv_sui_ping:
                intent.putExtra("IntentToStartBookInfoActivity", 10);
                break;
            //双鱼座
            case R.id.tv_shuang_yu:
                intent.putExtra("IntentToStartBookInfoActivity", 11);
                break;
            default:
                break;
        }
        startActivity(intent);
    }


    @OnClick(R.id.start_title_back_start_book_activity)
    public void onBackClicked(View view) {
        finish();
    }
}
