package cn.mmvtc.mobilesafe3.chapter02;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.mmvtc.mobilesafe3.R;
import cn.mmvtc.mobilesafe3.chapter02.utils.BaseSetUpActivity;

/**
 *向导2
 * 通过telephoneyManager获取SIM卡序列号
 * 判断是否绑定sim卡
 * 滑动屏幕
 * 按钮点击绑定sim卡
 *
 *
 * */
public class SetUp2Activity extends BaseSetUpActivity implements View.OnClickListener {
    private TelephonyManager mTelephonyManager;
    private Button mBindSIMBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        mTelephonyManager= (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        initView();
    }

    private void initView() {
        //设置小圆点的颜色   view是没有setChecked这个值   需要找到所需要的控件强转
        ((RadioButton) findViewById(R.id.rb_second)).setChecked(true);
        mBindSIMBtn= (Button) findViewById(R.id.btn_bind_sim);
        mBindSIMBtn.setOnClickListener(this);

        if (isBind()){
            //判断是否绑定过有的话不可操作
            mBindSIMBtn.setEnabled(false);
        }else{
            mBindSIMBtn.setEnabled(true);
        }
    }

    @Override
    public void showNext() {
        if(!isBind()){
            Toast.makeText(this, "你还没有绑定SIM卡", Toast.LENGTH_SHORT).show();
           // return;
        }


        //跳到向导3
        startActivityAndFinishSelf(SetUp3Activity.class);
    }

    @Override
    public void showPre() {
        startActivityAndFinishSelf(SetUp1Activity.class);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bind_sim:
                //绑定sim卡
                bindSIM();
                break;


        }
    }
    /**
     * 绑定sim卡
     * 改变按钮的状态，
     * */
    private void bindSIM() {
        if(!isBind()){
            String simSerialNumber=mTelephonyManager.getSimSerialNumber();
            SharedPreferences.Editor edit=sp.edit();
            edit.putString("sim",simSerialNumber);   //保存sim序列号到sim
            edit.commit();
            Toast.makeText(this, "SIM卡绑定成功", Toast.LENGTH_SHORT).show();
            mBindSIMBtn.setEnabled(false);

        }else{
            //已经绑定，提醒用户
            Toast.makeText(this, "SIM卡已经绑定", Toast.LENGTH_SHORT).show();
            mBindSIMBtn.setEnabled(false);
        }
    }
//判断sim序列号是否为空，在sp里面判断是否为空值
    public boolean isBind() {
        String simString=sp.getString("sim",null);
        if (TextUtils.isEmpty(simString)){
            return false;
        }
        return true;
    }
}
