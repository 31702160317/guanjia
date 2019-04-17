package cn.mmvtc.mobilesafe3.chapter02.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.mmvtc.mobilesafe3.chapter02.utils.APP;

/**
 * 监听开机启动的广播接收者，用于检查sim卡是否更换，
 * 如果被更换则发信息给安全号码
 */

public class BootCompleteReciever extends BroadcastReceiver {
    private  static  final String TAG=BootCompleteReciever.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        ((APP)context.getApplicationContext()).correctSIM();

    }
}
