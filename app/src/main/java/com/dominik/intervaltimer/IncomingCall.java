package com.dominik.intervaltimer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by dominik on 2016-09-26.
 */

public class IncomingCall extends BroadcastReceiver{

    BackgroundWork backgroundWork;

    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneStateListener PhoneListener = new MyPhoneStateListener();
        tm.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    public IncomingCall(){

    }

    public IncomingCall(BackgroundWork backgroundWork){
        this.backgroundWork = backgroundWork;
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        public void onCallStateChanged(int state, String incomingNumber) {

            backgroundWork.setRunningVarFalse();

        }
    }

}