package cn.mmvtc.mobilesafe3.chapter02.dialog;


import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.mmvtc.mobilesafe3.R;



public class SetUpPasswordDialog extends Dialog implements View.OnClickListener {
    private TextView mTitleTV;     //标题
    public EditText mFirstPWDET;   //首次输入密码框
    public EditText mAffirmET;//确认密码框
    private MyCallBack myCallBack; //回调接口

    public SetUpPasswordDialog(Context context) {
        super(context, R.style.dialog_custom);//引用自定义对话框样式

    }

    public void setCallBack(MyCallBack myCallBack) {
    this.myCallBack=myCallBack;

    }
//设置对话框标题
    public void  setTitle(String title){
        if (!TextUtils.isEmpty(title)){
            mTitleTV.setText(title);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_password_dialog);
        initView();
    }
//初始化控件
    private void initView() {
        mTitleTV = (TextView) findViewById(R.id.tv_setuppws_title);
        mFirstPWDET = (EditText) findViewById(R.id.et_firstpwd);
        mAffirmET = (EditText) findViewById(R.id.et_affirmpwd);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancle).setOnClickListener(this);

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
            myCallBack.ok();
            break;
            case R.id.btn_cancle:
            myCallBack.cancle();
            break;
        }
    }

        //可以灵活点
    public interface MyCallBack {
        void ok();

        void cancle();
    }
}
