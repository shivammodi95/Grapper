package com.android.grapper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by saraf on 11/20/2017.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
int x=0;
    @Override
    public void onReceive(final Context context, final Intent intent) {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
Log.d("abc","abc");

if(!isOnline(context))
{
    Toast.makeText(context, "Network Lost/Unavailable!", Toast.LENGTH_LONG).show();
            Log.d("Network Not Available ", "Flag No 1");
}
//        if (wifi.isAvailable() || mobile.isAvailable()) {
//            // Do something
//            x=1;
//
//
//        }
//        if(x==0)
//        {
//            Toast.makeText(context, "Network bro!", Toast.LENGTH_LONG).show();
//            Log.d("Network Not Available ", "Flag No 1");
//        }



    }
    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }


}
