package com.vito.messageintercept.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.vito.messageintercept.BroadcastReceiver.MessageReceiver;
import com.vito.messageintercept.R;
import com.vito.messageintercept.Util.EmailUtil;

public class MainActivity extends AppCompatActivity {

    private TextView mAddress;
    private TextView mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        bindViews();
        setViews();

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SetActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    private void bindViews() {
        mAddress = (TextView) findViewById(R.id.address_tv);
        mEmail = (TextView) findViewById(R.id.email_tv);
    }

    private void setViews() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(MessageReceiver.NAME, Context.MODE_PRIVATE);
        mAddress.setText(sharedPreferences.getString(MessageReceiver.ADDRESS, ""));
        mEmail.setText(sharedPreferences.getString(MessageReceiver.EMAIL, ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0){
            if (data != null) {
                mAddress.setText(data.getExtras().getString(SetActivity.ADDRESS));
                mEmail.setText(data.getExtras().getString(SetActivity.EMAIL));
                //保存配置数据到系统偏好里
                saveConfig(data);
            }
        }
    }

    private void saveConfig(Intent data) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(MessageReceiver.NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MessageReceiver.ADDRESS,data.getExtras().getString(SetActivity.ADDRESS));
        editor.putString(MessageReceiver.EMAIL,data.getExtras().getString(SetActivity.EMAIL));
        editor.commit();
    }

}
