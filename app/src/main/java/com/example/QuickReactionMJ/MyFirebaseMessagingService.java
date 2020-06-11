package com.example.QuickReactionMJ;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.net.URLDecoder;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        Log.d("FCM Log", "Refreshed token:" + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("온리시브 메시지 호출", "Cooooooooooooooooooooooool");
        Map<String, String> data = remoteMessage.getData();
        String title1 = data.get("title");
        //String message1 = data.get("message");
        String body1 = data.get("body1");
        String body2 = data.get("body2");
        String body3 = data.get("body3");
        String body4 = data.get("body4");


        if (remoteMessage.getNotification() != null) { // onMessageReceived메소드를 통해서 notification이 왔을경우
            Log.d("FCM Log", "알림 메시지:" + remoteMessage.getNotification().getBody()); // log찍고

            String messageBody = remoteMessage.getNotification().getBody();
            String messageTitle = remoteMessage.getNotification().getTitle();
            Intent intent = new Intent(this, MainActivity.class); // intent를
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // addFlag 메소드에 관해서는 부가적인 설명이 더필요
            // 일단은 intent를 통해 액티비티가 실행되는 순서를 조정이 가능한 메소드로 알고있자.
            // 자세한건 README에서
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            // Notification으로 작업을 하는 중이여서, PendingIntent를 사용해 intent를 사용할 수 있도록 한다.
            String channelId = "Channel ID";
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            // Notification에서 작업
            try {
                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(this, channelId)
                                .setSmallIcon(R.drawable.warning)
                                .setContentTitle(URLDecoder.decode(title1, "UTF-8"))
                                //.setContentText(URLDecoder.decode(message1, "UTF-8"))
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelName = "Channel Name";
                    NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(channel);
                }

                Log.i("온메세지리씨브 메시지 값 : ", body1);
                Log.i("온메세지리씨브 메시지 디코딩 값 : ", URLDecoder.decode(body1, "UTF-8"));
                Log.i("온메세지리씨브 메시지 값 : ", body2);
                Log.i("온메세지리씨브 메시지 디코딩 값 : ", URLDecoder.decode(body2, "UTF-8"));
                Log.i("온메세지리씨브 메시지 값 : ", body3);
                Log.i("온메세지리씨브 메시지 디코딩 값 : ", URLDecoder.decode(body3, "UTF-8"));
                Log.i("온메세지리씨브 메시지 값 : ", body4);
                Log.i("온메세지리씨브 메시지 디코딩 값 : ", URLDecoder.decode(body4, "UTF-8"));

                notificationManager.notify(0, notificationBuilder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i("온리시브 접근 x", "Cooooooooooooooooooooooool");
            try {
                Log.i("온메세지리씨브 타이틀 값 : ", title1);
                Log.i("온메세지리씨브 타이틀 디코딩 값 : ", URLDecoder.decode(title1, "UTF-8"));
                Log.i("온메세지리씨브 메시지 값 : ", body1);
                Log.i("온메세지리씨브 메시지 디코딩 값 : ", URLDecoder.decode(body1, "UTF-8"));
                Log.i("온메세지리씨브 메시지 값 : ", body2);
                Log.i("온메세지리씨브 메시지 디코딩 값 : ", URLDecoder.decode(body2, "UTF-8"));
                Log.i("온메세지리씨브 메시지 값 : ", body3);
                Log.i("온메세지리씨브 메시지 디코딩 값 : ", URLDecoder.decode(body3, "UTF-8"));
                Log.i("온메세지리씨브 메시지 값 : ", body4);
                Log.i("온메세지리씨브 메시지 디코딩 값 : ", URLDecoder.decode(body4, "UTF-8"));
            }
            catch (Exception e) {
                e.getMessage();
            }
        }
    }
}