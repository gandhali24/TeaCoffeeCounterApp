package com.example.teacoffeecounterapp.receiver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Config;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.teacoffeecounterapp.MainActivity;
import com.example.teacoffeecounterapp.R;
import com.google.android.datatransport.Priority;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.NotificationParams;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.File;
import java.net.URI;
import java.util.Map;

public class FirebaseMessageReceiver extends FirebaseMessagingService {
    PendingIntent pendingIntent;

    //    @Override
//    public void onMessageReceived(@NonNull RemoteMessage message) {
//        super.onMessageReceived(message);
//
//        System.out.println("From: "+message.getFrom());
//        if(message.getNotification()!=null)
//        {
//            System.out.println("Message Notification Body: "+message.getNotification().getBody());
//
//        }
//
//        sendNotification(message.getFrom(),message.getNotification().getBody());
//        sendNotification(message.getNotification().getBody());
//
//    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {

        Map<String, String> data = message.getData();

        //you can get your text message here.
        String text= data.get("sound");

        String title = message.getNotification().getTitle();
        String body = message.getNotification().getBody();
        String notsount=message.getNotification().getSound();
        final String channelId = "cnid";
       // Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.chai_coffee_tone);
      //Uri defaultSoundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + getPackageName() + "/" + R.raw.chai_coffee_tone);


        //  Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.chai_coffee_order_audio_loud);

        //Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.sample); //Here is FILE_NAME is the name of file that you want to play


        Uri defaultSoundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + File.pathSeparator + File.separator
                + File.separator + getApplicationContext().getPackageName() + File.separator + R.raw.chai_coffee_order_audio_loud);

        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.chai_coffee_order_audio_loud); //Here is FILE_NAME is the name of file that you want to play


        Uri notification1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);


//
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), defaultSoundUri);
        r.play();
    //  Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager .TYPE_ALARM);
//        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), defaultSoundUri);
//        r.play();

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build();
       // StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());

        Intent intent = new Intent(this, MainActivity.class);
     //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
         pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_MUTABLE);



        NotificationChannel channel = new NotificationChannel(channelId,
                "Heads Up Notification",
                NotificationManager.IMPORTANCE_HIGH);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
  channel.setSound(defaultSoundUri,audioAttributes);

        NotificationCompat.Builder notification
                = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(title)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                .setContentText(body)
                .setSmallIcon(R.drawable.teacoffeelogo)
                //.setPriority(NotificationCompat.PRIORITY_MAX)
              .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setChannelId(channelId)
               // .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                .setVibrate(new long[] { 1000, 1000, 1000,
                        1000, 1000 })
                .setSound(defaultSoundUri)
                //.setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                //.setVibrate(new long[] {400,0,400})
              //  .setPriority((int) NotificationPriority.MAX)
     // .setSound(Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.chai_coffee_tone)

              .setContentIntent(pendingIntent);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);

            return;
        }
        NotificationManagerCompat.from(this).notify(1, notification.build());

//    NotificationManager notificationManager =
//            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//}

//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//       // PendingIntent pendingIntent=PendingIntent.getActivity(this, 0 /* Request code */,intent,PendingIntent.FLAG_ONE_SHOT);
//        PendingIntent pintent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
//        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 0, pintent);
//        alarm.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pintent);

        super.onMessageReceived(message);

}



    private  void sendNotification(String from,String body)
    {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FirebaseMessageReceiver.this.getApplicationContext(), from + " ->"+ body,Toast.LENGTH_SHORT).show();
            }
        });
    }
    private  void  sendNotification(String messageBody)

    {
        Intent intent=new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      //  PendingIntent pendingIntent=PendingIntent.getActivity(this, 0 /* Request code */,intent,PendingIntent.FLAG_ONE_SHOT);
     //   PendingIntent pendingIntent=PendingIntent.getActivity(this, 0 /* Request code */,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        }else {
            pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        }



//        val updatedPendingIntent = PendingIntent.getActivity(
//                applicationContext,
//                NOTIFICATION_REQUEST_CODE,
//                updatedIntent,
//                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT // setting the mutability flag
        //)
        String channelId="My Channel ID";
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder
                =new NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground
                )
                .setContentTitle("My New Notification")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //since android oreo notification channel is needed
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel(channelId,
                    "Channel Human Readble Title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);

        }
        notificationManager.notify(0 /* Id of notification*/,notificationBuilder.build());
    }
}
//public class FirebaseMessageReceiver  extends FirebaseMessagingService {
//
//    // Override onNewToken to get new token
//    @Override
//    public void onNewToken(@NonNull String token)
//    {
//        Log.d("TAG", "Refreshed token: " + token);
//    }
//    // Override onMessageReceived() method to extract the
//    // title and
//    // body from the message passed in FCM
//    @Override
//    public void
//    onMessageReceived(RemoteMessage remoteMessage)
//    {
//        // First case when notifications are received via
//        // data event
//        // Here, 'title' and 'message' are the assumed names
//        // of JSON
//        // attributes. Since here we do not have any data
//        // payload, This section is commented out. It is
//        // here only for reference purposes.
//        /*if(remoteMessage.getData().size()>0){
//            showNotification(remoteMessage.getData().get("title"),
//                          remoteMessage.getData().get("message"));
//        }*/
//
//        // Second case when notification payload is
//        // received.
//        if (remoteMessage.getNotification() != null) {
//            // Since the notification is received directly
//            // from FCM, the title and the body can be
//            // fetched directly as below.
//            showNotification(
//                    remoteMessage.getNotification().getTitle(),
//                    remoteMessage.getNotification().getBody());
//        }
//    }
//
//    // Method to get the custom Design for the display of
//    // notification.
//    private RemoteViews getCustomDesign(String title,
//                                        String message)
//    {
//        RemoteViews remoteViews = new RemoteViews(
//                getApplicationContext().getPackageName(),
//                R.layout.layout_notification);
//        remoteViews.setTextViewText(R.id.tvNotificationTitle , title);
//        remoteViews.setTextViewText(R.id.tvNotificationMessage, message);
////        remoteViews.setImageViewResource(R.id.icon,
////                R.drawable.gfg);
//        return remoteViews;
//    }
//
//    // Method to display the notifications
//    public void showNotification(String title,
//                                 String message)
//    {
//        // Pass the intent to switch to the MainActivity
//        Intent intent
//                = new Intent(this, MainActivity.class);
//        // Assign channel ID
//        String channel_id = "notification_channel";
//        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
//        // the activities present in the activity stack,
//        // on the top of the Activity that is to be launched
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        // Pass the intent to PendingIntent to start the
//        // next Activity
//        PendingIntent pendingIntent
//                = PendingIntent.getActivity(
//                this, 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        // Create a Builder object using NotificationCompat
//        // class. This will allow control over all the flags
//        NotificationCompat.Builder builder
//                = new NotificationCompat
//                .Builder(getApplicationContext(),
//                channel_id)
//                .setSmallIcon(R.drawable.ic_black_coffee)
//                .setAutoCancel(true)
//                .setVibrate(new long[] { 1000, 1000, 1000,
//                        1000, 1000 })
//                .setOnlyAlertOnce(true)
//                .setContentIntent(pendingIntent);
//
//        // A customized design for the notification can be
//        // set only for Android versions 4.1 and above. Thus
//        // condition for the same is checked here.
//        if (Build.VERSION.SDK_INT
//                >= Build.VERSION_CODES.JELLY_BEAN) {
//            builder = builder.setContent(
//                    getCustomDesign(title, message));
//        } // If Android Version is lower than Jelly Beans,
//        // customized layout cannot be used and thus the
//        // layout is set as follows
//        else {
//            builder = builder.setContentTitle(title)
//                    .setContentText(message)
//                    .setSmallIcon(R.drawable.ic_black_coffee);
//        }
//        // Create an object of NotificationManager class to
//        // notify the
//        // user of events that happen in the background.
//        NotificationManager notificationManager
//                = (NotificationManager)getSystemService(
//                Context.NOTIFICATION_SERVICE);
//        // Check if the Android Version is greater than Oreo
//        if (Build.VERSION.SDK_INT
//                >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel
//                    = new NotificationChannel(
//                    channel_id, "web_app",
//                    NotificationManager.IMPORTANCE_HIGH);
//            notificationManager.createNotificationChannel(
//                    notificationChannel);
//        }
//
//        notificationManager.notify(0, builder.build());
//    }
//}
//
