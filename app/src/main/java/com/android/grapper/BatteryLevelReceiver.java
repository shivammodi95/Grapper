package com.android.grapper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by saraf on 11/5/2017.
 */
public class BatteryLevelReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "BAttery's dying!!", Toast.LENGTH_LONG).show();
        Log.e("", "BATTERY LOW!!");
    }
}