package com.adobe.phonegap.push;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

@SuppressLint("LongLogTag")
public class MessageReceivedHook {
    private static final String LOG_TAG = "PUSH_MessageReceivedHook";

    public static final String ActionName_OnMessageReceived = "com.adobe.phonegap.push.OnMessageReceived";

    public static final String INTENT_PROP_NOT_ID = "notId";

    private Context context;

    public MessageReceivedHook(Context appContext) {
        this.context = appContext;
    }

    public void dispose() {
        this.context = null;
    }

    public void fireEvent(Bundle extras, int notId) {
        this.sendBroadcast(notId, extras);
    }

    private void sendBroadcast(int notId, Bundle extras) {
        Log.d(LOG_TAG, "Creating OnMessageReceivedBroadcast");

        Intent i = new Intent(ActionName_OnMessageReceived);
        i.setPackage(this.context.getPackageName());    // Required so that we can register an implicit receiver

        i.putExtras(extras);
        i.putExtra(INTENT_PROP_NOT_ID, notId);  // Overwrite the notId property - extras Bundle has the un-parsed notId as String

        // For Android O+, due to background restrictions
        // This require the app to be whitelist for background activity (Ignore battery optimisation)
        this.context.sendBroadcast(i);
    }
}