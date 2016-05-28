package com.vito.messageintercept.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.vito.messageintercept.R;

/**
 * @Description: 设置拦截和发生信息
 * @Created by Zhangfeng on 2016/5/28.
 * @ModifiedBy: Clowire51
 * @ModifiedTime: 2016/5/28 11:13
 * @ModifiedNotes:
 * @Version
 */
public class SetActivity extends AppCompatActivity{

    public static final String ADDRESS = "address";
    public static final String EMAIL = "email";
    private EditText mAddress;
    private EditText mEmail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        bindViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (!mAddress.getText().toString().isEmpty()) {
                    intent.putExtra(ADDRESS, mAddress.getText().toString());
                }
                if (!mEmail.getText().toString().isEmpty()) {
                    intent.putExtra(EMAIL, mEmail.getText().toString());
                }
                if(mEmail.getText().toString().isEmpty()&&mAddress.getText().toString().isEmpty()){
                    Toast.makeText(SetActivity.this, "请输入号码", Toast.LENGTH_SHORT).show();
                }else {
                    setResult(0,intent);
                    finish();
                }
            }
        });
    }

    private void bindViews() {
        mAddress = (EditText) findViewById(R.id.address_et);
        mEmail = (EditText) findViewById(R.id.email_et);
    }
}
