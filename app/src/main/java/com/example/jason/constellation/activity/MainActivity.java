package com.example.jason.constellation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.jason.constellation.R;
import com.example.jason.constellation.adapter.MainGridViewAdapter;
import com.example.jason.constellation.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_gridView) GridView gridView;
    private MainGridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initGridView();
    }

    private void initGridView() {
        adapter = new MainGridViewAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, CheckStartActivity.class);
                        MainActivity.this.startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, StartActivity.class);
                        MainActivity.this.startActivity(intent1);
                        break;

                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, StartBookActivity.class);
                        MainActivity.this.startActivity(intent2);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
