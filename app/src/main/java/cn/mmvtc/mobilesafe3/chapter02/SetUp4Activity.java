package cn.mmvtc.mobilesafe3.chapter02;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import cn.mmvtc.mobilesafe3.R;
import cn.mmvtc.mobilesafe3.chapter02.utils.BaseSetUpActivity;
import cn.mmvtc.mobilesafe3.chapter02.utils.LostFindActivity;

import static android.R.attr.id;

public class SetUp4Activity extends BaseSetUpActivity {
    private TextView mStatusTV;
    private ToggleButton mToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        initView();
    }

    private void initView() {
        //设置小圆点的颜色   view是没有setChecked这个值   需要找到所需要的控件强转
        ((RadioButton) findViewById(R.id.rb_four)).setChecked(true);
        mStatusTV = (TextView) findViewById(R.id.tv_setup4_status);
        mToggleButton = (ToggleButton) findViewById(R.id.togglebtn_securityfunction);
        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mStatusTV.setText("防盗保护已经开启");
                }else{
                    mStatusTV.setText("防盗保护没有开启");
                }
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("protecting",isChecked);
                editor.commit();
            }
        });
        boolean protecting=sp.getBoolean("protecting",true);
        if (protecting)
        {
            mStatusTV.setText("防盗保护已经开启");
            mToggleButton.setChecked(true);
        }else{
            mStatusTV.setText("防盗保护没有开启");
            mToggleButton.setChecked(false);
        }

    }

    @Override
    public void showNext() {
        //跳转到防盗保护页面
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isSetUp",true);
        editor.commit();

        startActivityAndFinishSelf(LostFindActivity.class);

    }

    @Override
    public void showPre() {
        startActivityAndFinishSelf(SetUp3Activity.class);
    }
}
