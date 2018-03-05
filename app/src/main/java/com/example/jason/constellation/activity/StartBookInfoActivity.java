package com.example.jason.constellation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jason.constellation.R;
import com.example.jason.constellation.base.BaseActivity;
import com.example.jason.constellation.share.AndroidShare;
import com.example.jason.constellation.utils.ResourceUtil;

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

    private String[] name =
                    {"白羊座", "金牛座", "双子座",
                    "巨蟹座", "狮子座", "处女座",
                    "天秤座", "天蝎座", "射手座",
                    "魔蝎座", "水瓶座", "双鱼座"};

    private String[] teZheng =
            {ResourceUtil.getString(R.string.te_zheng_bai_yang), ResourceUtil.getString(R.string.te_zheng_jin_niu), ResourceUtil.getString(R.string.te_zheng_shuang_zhi),
                    ResourceUtil.getString(R.string.te_zheng_jv_xie), ResourceUtil.getString(R.string.te_zheng_shi_zhi), ResourceUtil.getString(R.string.te_zheng_chu_nv),
                    ResourceUtil.getString(R.string.te_zheng_tian_ping), ResourceUtil.getString(R.string.te_zheng_tian_xie), ResourceUtil.getString(R.string.te_zheng_she_shou),
                    ResourceUtil.getString(R.string.te_zheng_mo_xie), ResourceUtil.getString(R.string.te_zheng_shui_ping), ResourceUtil.getString(R.string.te_zheng_shuang_yu)};

    private String[] love =
            {ResourceUtil.getString(R.string.love_bai_yang), ResourceUtil.getString(R.string.love_jin_niu), ResourceUtil.getString(R.string.love_shuang_zhi),
                    ResourceUtil.getString(R.string.love_jv_xie), ResourceUtil.getString(R.string.love_shi_zhi), ResourceUtil.getString(R.string.love_chu_nv),
                    ResourceUtil.getString(R.string.te_zheng_tian_ping), ResourceUtil.getString(R.string.te_zheng_tian_xie), ResourceUtil.getString(R.string.te_zheng_she_shou),
                    ResourceUtil.getString(R.string.love_mo_xie), ResourceUtil.getString(R.string.love_shui_ping), ResourceUtil.getString(R.string.love_shuang_yu)};

    private String[] huDong =
            {ResourceUtil.getString(R.string.hu_dong_bai_yang), ResourceUtil.getString(R.string.hu_dong_jin_niu), ResourceUtil.getString(R.string.hu_dong_shuang_zhi),
                    ResourceUtil.getString(R.string.hu_dong_jv_xie), ResourceUtil.getString(R.string.hu_dong_shi_zhi), ResourceUtil.getString(R.string.hu_dong_chu_nv),
                    ResourceUtil.getString(R.string.hu_dong_tian_ping), ResourceUtil.getString(R.string.hu_dong_tian_xie), ResourceUtil.getString(R.string.hu_dong_she_shou),
                    ResourceUtil.getString(R.string.hu_dong_mo_xie), ResourceUtil.getString(R.string.hu_dong_shui_ping), ResourceUtil.getString(R.string.hu_dong_shuang_yu)};

    private String[] music =
            {ResourceUtil.getString(R.string.music_bai_yang), ResourceUtil.getString(R.string.music_jin_niu), ResourceUtil.getString(R.string.music_shuang_zhi),
                    ResourceUtil.getString(R.string.music_jv_xie), ResourceUtil.getString(R.string.music_shi_zhi), ResourceUtil.getString(R.string.music_chu_nv),
                    ResourceUtil.getString(R.string.music_tian_ping), ResourceUtil.getString(R.string.music_tian_xie), ResourceUtil.getString(R.string.music_she_shou),
                    ResourceUtil.getString(R.string.music_mo_xie), ResourceUtil.getString(R.string.music_shui_ping), ResourceUtil.getString(R.string.music_shuang_yu)};


    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_book_info);
        ButterKnife.bind(this);
        mPosition = getIntent().getIntExtra("IntentToStartBookInfoActivity", 0);
        tvTitle.setText(name[mPosition]);
        tvTeZheng.setText(teZheng[mPosition]);
        tvLove.setText(love[mPosition]);
        tvHuDong.setText(huDong[mPosition]);
        tvMusic.setText(music[mPosition]);
    }

    @OnClick(R.id.start_title_back_start_book_info_activity)
    public void onBackClicked(View view) {
        finish();
    }

    @OnClick(R.id.iv_share_start_book_info_activity)
    public void onShareClicked(View view) {
        share();
    }

    public void share() {
        AndroidShare as = new AndroidShare(this, "星座为：" + name[mPosition] + "  星座特征： " + teZheng[mPosition] + " 星座爱情：" + love[mPosition] + " 星座互动： " + huDong[mPosition] + " 星座音乐： " + music[mPosition], "");
        as.show();
    }
}
