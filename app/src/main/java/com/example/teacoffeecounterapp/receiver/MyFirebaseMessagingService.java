package com.example.teacoffeecounterapp.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.teacoffeecounterapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

public String CHANNEL_SIREN_ID="CHANNEL_SIREN_ID";
    public String CHANNEL_SIREN_NAME="CHANNEL_SIREN_NAME";
    public String CHANNEL_SIREN_DESCRIPTION="CHANNEL_SIREN_DESCRIPTION";

    public MyFirebaseMessagingService() {
        super();

    }






//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//
//        //Log.d(Application.APPTAG, "myFirebaseMessagingService - onMessageReceived - message: " + remoteMessage);
//
//        Intent dialogIntent = new Intent(this, NotificationActivity.class);
//        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        dialogIntent.putExtra("msg", remoteMessage);
//        startActivity(dialogIntent);
//
//    }
//@Override
//public void onMessageReceived(RemoteMessage remoteMessage) {
//
//    //Log.d(Application.APPTAG, "myFirebaseMessagingService - onMessageReceived - message: " + remoteMessage);
//    Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ getApplicationContext().getPackageName() + "/" + R.raw.chai_coffee_order_audio_loud);
//    NotificationManager mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//    NotificationChannel mChannel;
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        mChannel = new NotificationChannel(CHANNEL_SIREN_ID, CHANNEL_SIREN_NAME, NotificationManager.IMPORTANCE_HIGH);
//        mChannel.setLightColor(Color.GRAY);
//        mChannel.enableLights(true);
//        mChannel.setDescription(CHANNEL_SIREN_DESCRIPTION);
//        AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                .build();
//        mChannel.setSound(soundUri, audioAttributes);
//
//        if (mNotificationManager != null) {
//            mNotificationManager.createNotificationChannel( mChannel );
//        }
//    }
//
//    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_SIREN_ID)
//            .setSmallIcon(R.drawable.teacoffeelogo)
//            .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher))
//            .setTicker("TEA/COFFEE Order")
//            .setContentTitle("PLease Order")
//           // .setContentText(contentText)
//            .setAutoCancel(true)
//            .setLights(0xff0000ff, 300, 1000) // blue color
//            .setWhen(System.currentTimeMillis())
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//        mBuilder.setSound(soundUri);
//    }
//

//}





    public void createNotificationChannel() {
        NotificationManager notificationManager =getSystemService(NotificationManager.class); // If you are writting code in Activity

        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.chai_coffee_order_audio_loud); //Here is FILE_NAME is the name of file that you want to play
// Create the NotificationChannel, but only on API 26+ because
// the NotificationChannel class is new and not in the support library if
//        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
            CharSequence name = "mychannel";
            String description = "testing";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("cnid", name, importance);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.setDescription(description);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.enableLights(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.enableVibration(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.setSound(sound, audioAttributes);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }
        // }
    };




}