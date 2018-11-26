package com.firebaseloginapp.AccountActivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebaseloginapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import javax.xml.transform.Result;

/**
 * Created by NgocTri on 8/9/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Bundle bundle= new Bundle();


                bundle.putString("desc",remoteMessage.getData().get("desc"));
                bundle.putString("fecha",remoteMessage.getData().get("fecha"));
                bundle.putString("hora",remoteMessage.getData().get("hora"));
                bundle.putString("lat",remoteMessage.getData().get("lat"));
                bundle.putString("lon",remoteMessage.getData().get("lon"));
                bundle.putString("tipo",remoteMessage.getData().get("tipo"));
                bundle.putString("id",remoteMessage.getData().get("id"));
                bundle.putString("imagen",remoteMessage.getData().get("imagen"));


        //Check if the message contains notification

        if(remoteMessage.getNotification() != null) {
            Log.d(TAG, "Mesage body:" + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle(),bundle);
        }
    }

    /**
     * Dispay the notification
     * @param body
     */
    private void sendNotification(String body,String title, Bundle bindel) {

        Intent intent = new Intent(this, DenunciasActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bindel);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0/*Request code*/, intent, PendingIntent.FLAG_ONE_SHOT);
        //Set sound of notification
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /*ID of notification*/, notifiBuilder.build());

    }
}
