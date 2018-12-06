package com.firebaseloginapp.AccountActivity;

/**
 * Created by odvil on 22/04/2018.
 */

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFCMClass extends FirebaseMessagingService {

    private final String TAG = "JSA-FCM";

    @Override
    public void onMessageSent(String msgId) {
        Log.e(TAG, "onMessageSent: " + msgId);
    }

    @Override
    public void onSendError(String msgId, Exception e) {
        Log.e(TAG, "onSendError: " + msgId);
        Log.e(TAG, "Exception: " + e);
    }
}