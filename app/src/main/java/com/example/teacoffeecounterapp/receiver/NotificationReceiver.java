package com.example.teacoffeecounterapp.receiver;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.legacy.content.WakefulBroadcastReceiver;

import com.example.teacoffeecounterapp.R;

public class NotificationReceiver  extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        playNotificationSound(context);

    }
    public void playNotificationSound(Context context) {
        try {

            //Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Uri defaultSoundUri = null;

                defaultSoundUri = Uri.parse("android.resource://" + "raw" + "/" + R.raw.chai_coffee_order_audio_loud);


            Ringtone r = RingtoneManager.getRingtone(context, defaultSoundUri);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
