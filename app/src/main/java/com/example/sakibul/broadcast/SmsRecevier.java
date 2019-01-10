package com.example.sakibul.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsRecevier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction()
                .equals("android.provider.Telephony.SMS_RECEIVED")) {
            // extract number and message from intent
            Bundle bundle = intent.getExtras();
            SmsMessage[] chunks = null;
            String number = "";
            String message = "";

            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                String smsMessageStr = "";
                chunks = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    chunks[i] = SmsMessage.createFromPdu((byte[]) pdus[i]); //, "3gpp"
                    number = chunks[i].getOriginatingAddress();
                    message += chunks[i].getMessageBody();
                    smsMessageStr += "SMS From: " + number + "\n";
                    smsMessageStr += message + "\n";
                }
                Toast.makeText(context,
                        "SMS Received: from: " + number + " text:" + message,
                        Toast.LENGTH_LONG).show();

                //send back
                //if(number.equals("01717415191")){
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(number, null, "I'll call you back",
                        null, null);
                //}
                //this will update the UI with message
//                MainActivity inst = MainActivity.instance();
//                inst.updateList(smsMessageStr);

            }
        }
    }
}
