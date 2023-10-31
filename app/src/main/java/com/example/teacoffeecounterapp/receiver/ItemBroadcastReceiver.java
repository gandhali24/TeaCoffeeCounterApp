package com.example.teacoffeecounterapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ItemBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startActivity(intent); // example, add necessary reaction
    }
}