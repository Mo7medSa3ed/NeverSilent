package com.Mo7mdSa3ed55.NeverSilent;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Reoncreate extends Service  {
database db =new database(this);
ArrayList<String> phones = new ArrayList<String>();
AudioManager audioManager;



    @Override
    public void onCreate() {
        super.onCreate();

    }

    public Reoncreate(){}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            restart2();
        }else {
            restart2();

        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("restartservice");
            broadcastIntent.setClass(this, Restarter.class);
            this.sendBroadcast(broadcastIntent);
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

    public void test(String number){
        phones = db.getAllDataNumber();
        for (int i = 0; i <phones.size(); i++) {
            if (number.equals(phones.get(i)) ) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        }
    }


    public void restart2(){
        audioManager = (AudioManager)getSystemService(this.AUDIO_SERVICE);
        TelephonyManager telephonyManager =(TelephonyManager)getSystemService(this.TELEPHONY_SERVICE);
        PhoneStateListener listener = new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
                String number = phoneNumber;
                if (state==TelephonyManager.CALL_STATE_RINGING) {
                    test(number);
                }
            }
        };
        telephonyManager.listen(listener,listener.LISTEN_CALL_STATE);

    }

}
