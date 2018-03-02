package com.example.jason.constellation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jason.constellation.R;
import com.example.jason.constellation.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckStartActivity extends BaseActivity {

    @BindView(R.id.check_title_back) ImageView checkTitleBack;
    @BindView(R.id.spinner_month_check_start_activity) Spinner mMonthSpinner;
    @BindView(R.id.spinner_day_check_start_activity) Spinner mDaySpinner;
    @BindView(R.id.btn_check_start_activity) Button mCheckButton;
    @BindView(R.id.tv_start_name_check_start_activity) TextView mStartNameTextView;

    ArrayAdapter<Integer> arrayAdapter1;
    ArrayAdapter<Integer> arrayAdapter2;

    private List<Integer> list1 = new ArrayList<Integer>();
    private List<Integer> list2 = new ArrayList<Integer>();//31天的
    private List<Integer> list3 = new ArrayList<Integer>();//30天的
    private List<Integer> list4 = new ArrayList<Integer>();//29天的
    private int month = 1;
    private int day = 1;

    private String[] name = {"白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔蝎座", "水瓶座", "双鱼座"};

    private String[] time = {"3月21日-4月19日", "4月20日-5月20日", "5月21日-6月21日", "6月22日-7月22日", "7月23日-8月22日", "8月23日-9月22日", "9月23日-10月23日", "10月24日-11月22日", "11月23日-12月21日", "12月22日-1月19日", "1月20日-2月18日", "2月19日-3月20日"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_start2);
        ButterKnife.bind(this);
        initData();
        arrayAdapter1 = new ArrayAdapter<Integer>(CheckStartActivity.this, R.layout.support_simple_spinner_dropdown_item, list1);
        arrayAdapter2 = new ArrayAdapter<Integer>(CheckStartActivity.this, R.layout.support_simple_spinner_dropdown_item, list2);

        mMonthSpinner.setAdapter(arrayAdapter1);
        mMonthSpinner.setSelection(0, true);
        mDaySpinner.setAdapter(arrayAdapter2);
        mDaySpinner.setSelection(0, true);

        mMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = position + 1;
                switch (position + 1) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        arrayAdapter2 = new ArrayAdapter<Integer>(CheckStartActivity.this, R.layout.support_simple_spinner_dropdown_item, list2);
                        mDaySpinner.setAdapter(arrayAdapter2);
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        arrayAdapter2 = new ArrayAdapter<Integer>(CheckStartActivity.this, R.layout.support_simple_spinner_dropdown_item, list3);
                        mDaySpinner.setAdapter(arrayAdapter2);
                        break;
                    case 2:
                        arrayAdapter2 = new ArrayAdapter<Integer>(CheckStartActivity.this, R.layout.support_simple_spinner_dropdown_item, list4);
                        mDaySpinner.setAdapter(arrayAdapter2);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                day = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick(R.id.check_title_back)
    public void onBackClicked(View view) {
        finish();
    }

    private String[] name2 = {"白羊座", "金牛座", "双子座",
            "巨蟹座", "狮子座", "处女座",
            "天秤座", "天蝎座", "射手座",
            "魔蝎座", "水瓶座", "双鱼座"};

    private String[] time2 = {"3月21日-4月19日", "4月20日-5月20日", "5月21日-6月21日",
            "6月22日-7月22日", "7月23日-8月22日", "8月23日-9月22日",
            "9月23日-10月23日", "10月24日-11月22日", "11月23日-12月21日",
            "12月22日-1月19日", "1月20日-2月18日", "2月19日-3月20日"};

    @OnClick(R.id.btn_check_start_activity)
    public void onCheckClicked(View view) {
        switch (month) {
            case 1:
                if (day > 19) {
                    mStartNameTextView.setText("水瓶座");
                } else {
                    mStartNameTextView.setText("魔蝎座");
                }
                break;
            case 2:
                if (day > 18) {
                    mStartNameTextView.setText("双鱼座");
                } else {
                    mStartNameTextView.setText("水瓶座");
                }
                break;
            case 3:
                if (day > 20) {
                    mStartNameTextView.setText("白羊座");
                } else {
                    mStartNameTextView.setText("双鱼座");
                }
                break;
            case 4:
                if (day > 20) {
                    mStartNameTextView.setText("金牛座");
                } else {
                    mStartNameTextView.setText("白羊座");
                }
                break;
            case 5:
                if (day > 20) {
                    mStartNameTextView.setText("双子座");
                } else {
                    mStartNameTextView.setText("金牛座");
                }
                break;
            case 6:
                if (day > 21) {
                    mStartNameTextView.setText("巨蟹座");
                } else {
                    mStartNameTextView.setText("双子座");
                }
                break;
            case 7:
                if (day > 22) {
                    mStartNameTextView.setText("狮子座");
                } else {
                    mStartNameTextView.setText("巨蟹座");
                }
                break;
            case 8:
                if (day > 22) {
                    mStartNameTextView.setText("处女座");
                } else {
                    mStartNameTextView.setText("狮子座");
                }
                break;
            case 9:
                if (day > 22) {
                    mStartNameTextView.setText("天秤座");
                } else {
                    mStartNameTextView.setText("处女座");
                }
                break;
            case 10:
                if (day > 23) {
                    mStartNameTextView.setText("天蝎座");
                } else {
                    mStartNameTextView.setText("天秤座");
                }
                break;
            case 11:
                if (day > 22) {
                    mStartNameTextView.setText("射手座");
                } else {
                    mStartNameTextView.setText("天蝎座");
                }
                break;
            case 12:
                if (day > 21) {
                    mStartNameTextView.setText("魔蝎座");
                } else {
                    mStartNameTextView.setText("射手座");
                }
                break;
            default:
                break;
        }
    }

    public void initData() {
        for (int i = 1; i < 13; i++) {
            list1.add(i);
        }

        for (int i = 1; i < 32; i++) {
            list2.add(i);
        }

        for (int i = 1; i < 31; i++) {
            list3.add(i);
        }
        for (int i = 1; i < 30; i++) {
            list4.add(i);
        }

    }

}
