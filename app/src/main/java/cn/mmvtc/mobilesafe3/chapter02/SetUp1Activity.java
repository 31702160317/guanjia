package cn.mmvtc.mobilesafe3.chapter02;


import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.mmvtc.mobilesafe3.R;
import cn.mmvtc.mobilesafe3.chapter02.utils.BaseSetUpActivity;

public class SetUp1Activity extends BaseSetUpActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
        initView();
    }

    private void initView() {
    //设置第一个小圆点的颜色   view是没有setChecked这个值   需要找到所需要的控件强转
        ((RadioButton)findViewById(R.id.rb_first)).setChecked(true);
    }

    @Override
    public void showNext() {
    startActivityAndFinishSelf(SetUp2Activity.class);
    }

    @Override
    public void showPre() {
        Toast.makeText(this, "当前页面已经是第一个页面", Toast.LENGTH_SHORT).show();
    }
}
