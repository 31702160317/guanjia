package cn.mmvtc.mobilesafe3;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import cn.mmvtc.mobilesafe3.chapter01.adapter.HomeAdapter;
import cn.mmvtc.mobilesafe3.chapter02.dialog.InterPasswordDialog;
import cn.mmvtc.mobilesafe3.chapter02.dialog.SetUpPasswordDialog;
import cn.mmvtc.mobilesafe3.chapter02.utils.MD5Utils;
import cn.mmvtc.mobilesafe3.chapter02.SetUp1Activity;

public class HomeActivity extends Activity {
    private GridView gv_home;
    private long mExitTime;
    //手机防盗密码的保存
    private SharedPreferences msharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_home);
        msharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        gv_home = (GridView) findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
        //每个更新条目的点击事件
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://手机防盗
                        if (isSetUpPassWord()) {
                            showInterPswdDialog();
                        } else {
                            showSetUpPswdDiglog();
                        }

                        break;
                    case 1://通讯卫士

                        break;
                    case 2://软件管家

                        break;
                    case 3://手机杀毒

                        break;
                    case 4://缓存清理

                        break;
                    case 5://进程管理

                        break;
                    case 6://流量管理

                        break;
                    case 7://高级工具

                        break;
                    case 8://设置中心

                        break;
                }
            }
        });

    }

    /**
     * 掏出设置对话框
     */
    //没设置过密码的对话框
    private void showSetUpPswdDiglog() {
        final SetUpPasswordDialog setUpPasswordDialog = new SetUpPasswordDialog(HomeActivity.this);
        setUpPasswordDialog.setCallBack(new SetUpPasswordDialog.MyCallBack() {
            @Override
            public void ok() {
                String firstPwsd = setUpPasswordDialog.mFirstPWDET.getText().toString().trim();
                String affirmPwsd = setUpPasswordDialog.mAffirmET.getText().toString().trim();
                if (!TextUtils.isEmpty(firstPwsd) && !TextUtils.isEmpty(affirmPwsd)) {
                    if (firstPwsd.equals(affirmPwsd)) {
                        //如果两次密码比较正确则存储密码
                        savePswd(affirmPwsd);
                        //确认后，对话框消失
                        setUpPasswordDialog.dismiss();
                        //显示输入密码对话框
                        showInterPswdDialog();
                    } else {
                        Toast.makeText(HomeActivity.this, "两次不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void cancle() {
                setUpPasswordDialog.dismiss();
            }
        });
        setUpPasswordDialog.setCancelable(true);
        setUpPasswordDialog.show();
    }


    /**
     * 设置过密码出现输入密码对话框
     */





    private void showInterPswdDialog() {
        final String password = getPassword();
        final InterPasswordDialog interPasswordDialog = new InterPasswordDialog(HomeActivity.this);
        interPasswordDialog.setCallBack(new InterPasswordDialog.MyCallBack() {
            @Override
            public void confirm() {
                if (TextUtils.isEmpty(interPasswordDialog.getPassword())) {
                    Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();

                } else if (password.equals(MD5Utils.encode(interPasswordDialog.getPassword()))) {
                    //进入防盗页面
                    interPasswordDialog.dismiss();

                    //199-208
                   startActivity(SetUp1Activity.class);

                } else {
                    //对话框消失，弹出吐司
                    interPasswordDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "密码有误，请重新输入！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void cancle() {
                interPasswordDialog.dismiss();
            }
        });
        interPasswordDialog.setCancelable(true);
        interPasswordDialog.show();
    }


    /**
     * 保存密码
     * affirmPwsd
     */
    private void savePswd(String affirmPwsd) {

        final SharedPreferences.Editor editor = msharedPreferences.edit();
        editor.putString("PhoneAntiTheftPWD", MD5Utils.encode(affirmPwsd));
        editor.commit();
    }

    /**
     * 读取密码
     */
    private String getPassword() {

        final String password = msharedPreferences.getString("PhoneAntiTheftPWD", null);
        if (TextUtils.isEmpty(password)) {
            return "";
        }

        return password;
    }

    /**
     * 判断用户是否设置过手机防盗密码
     */
    private boolean isSetUpPassWord() {
        String password = msharedPreferences.getString("PhoneAntiTheftPWD", null);
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }

    /**
     * 开启新的activity不关闭自己
     * cls新的activity的字节码
     */
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(HomeActivity.this, cls);
        startActivity(intent);


    }

    /**
     * 按两次返回键退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) < 2000) {
                System.exit(0);
            } else {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
