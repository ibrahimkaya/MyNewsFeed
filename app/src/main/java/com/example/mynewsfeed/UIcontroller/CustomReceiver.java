package com.example.mynewsfeed.UIcontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo activeNetwork = null;
        String intentAction = intent.getAction();
        String  networkState = "" ;
        String  networkType = "";
        String toastMessage = "unknown intent action";

        //delay for connecting wifi
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm != null) {
             activeNetwork = cm.getActiveNetworkInfo();
        }
        if(activeNetwork != null){
            networkState = activeNetwork.getState().toString();
            networkType = activeNetwork.getTypeName();
        }

        if (intentAction != null) {
            if (intentAction.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                if (networkState.equals("CONNECTED") && networkType.equals("WIFI")) {
                    toastMessage = "wifi is online, right time to refresh news! ";
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}