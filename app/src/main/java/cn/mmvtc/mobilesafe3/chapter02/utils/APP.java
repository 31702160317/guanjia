package cn.mmvtc.mobilesafe3.chapter02.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telecom.TelecomManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import static android.os.Build.VERSION_CODES.M;

/**
 * 检查sim卡是否发生变化
 */

public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        correctSIM();
    }


    public void correctSIM() {
        //检查sim卡是否发生变化
        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
        //获取防盗保护的状态
        boolean protecting = sp.getBoolean("protecting", true);
        if (protecting) {
            //得到绑定sim卡串号
            String bindSIM = sp.getString("sim", "");
            //得到手机现在的sim卡串号

            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            //

            String realsim=tm.getSimSerialNumber();
            if (bindSIM.equals(realsim)) {

                Log.i("","sim卡未发生改变");
            }else{
                Log.i("","sim卡发生改变");
                String safenumber=sp.getString("safephone","");
                if (!TextUtils.isEmpty(safenumber)){
                    SmsManager smsManager=SmsManager.getDefault();
                    smsManager.sendTextMessage(safenumber,null,"你的亲友手机已经被更换",null,null);

                }

            }
        }
    }
}
