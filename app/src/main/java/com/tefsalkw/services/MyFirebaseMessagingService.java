package com.tefsalkw.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.tefsalkw.R;
import com.tefsalkw.activity.AccessoryProductDetailsActivity;
import com.tefsalkw.activity.DaraAbayaActivity;
import com.tefsalkw.activity.DishDashaProductActivity;
import com.tefsalkw.activity.MailingSystemActivity;
import com.tefsalkw.activity.MainActivity;
import com.tefsalkw.activity.MyOrderActivity;
import com.tefsalkw.activity.OrderDetailsActivity;
import com.tefsalkw.activity.OtherStoresActivity;
import com.tefsalkw.activity.ZaaraDaraaActivity;
import com.tefsalkw.app.TefalApp;
import com.tefsalkw.utils.Config;
import com.tefsalkw.utils.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by jagbirsinghkang on 09/08/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    final static String KEY_GROUP = "Tefsal";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "TimeStamp: " + remoteMessage.getSentTime());
        Log.e(TAG, "NotificationPayload: " + new Gson().toJson(remoteMessage.getNotification()));
        Log.e(TAG, "DataPayload: " + new Gson().toJson(remoteMessage.getData()));

        handlePush(remoteMessage);
    }


    private void handlePush(RemoteMessage remoteMessage) {

        String title = "";
        String message = "";

        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            message = remoteMessage.getNotification().getBody();

        }


        String type = "general";

        String category = "";
        if (remoteMessage.getData() != null) {

            title = remoteMessage.getData().get("title");
            message = remoteMessage.getData().get("message");
            type = remoteMessage.getData().get("type");
            category = remoteMessage.getData().get("category");
        }

        Intent intent = null;

        try {

            if (type.equalsIgnoreCase("store")) {
                if (category.equalsIgnoreCase("DTA")) {

                    TefalApp.getInstance().setToolbar_title("DISHDASHA STORES");
                    TefalApp.getInstance().setMin_meters("3");
                    TefalApp.getInstance().setStyleName("TefsalDefault");

                    intent = new Intent(getApplicationContext(), DaraAbayaActivity.class);
                    intent.putExtra("sub_category", "6");
                    intent.putExtra("flag", "dish");

                }

                if (category.equalsIgnoreCase("DTE")) {

                    TefalApp.getInstance().setToolbar_title("DISHDASHA STORES");
                    TefalApp.getInstance().setMin_meters("3");
                    TefalApp.getInstance().setStyleName("TefsalDefault");


                    intent = new Intent(getApplicationContext(), DaraAbayaActivity.class);
                    intent.putExtra("sub_category", "5");
                    intent.putExtra("flag", "dish");
                }

                if (category.equalsIgnoreCase("DB")) {

                    intent = new Intent(getApplicationContext(), OtherStoresActivity.class);
                }


            }


            if (type.equalsIgnoreCase("product")) {

//                if (category.equalsIgnoreCase("DTA")) {
//                    intent = new Intent(getApplicationContext(), TextileDetailActivity.class);
//                    intent.putExtra("store_id", remoteMessage.getData().get("store_id"));
//                    intent.putExtra("product_id", remoteMessage.getData().get("product_id"));
//                }

                if (category.equalsIgnoreCase("DTE")) {

                    intent = new Intent(getApplicationContext(), DishDashaProductActivity.class);
                    intent.putExtra("store_id", remoteMessage.getData().get("store_id"));
                    intent.putExtra("product_id", remoteMessage.getData().get("product_id"));
                    intent.putExtra("fromWhere", "textile");
                    intent.putExtra("flag", "dish");

                    TefalApp.getInstance().setStoreId(remoteMessage.getData().get("store_id"));
                    TefalApp.getInstance().setProductId(remoteMessage.getData().get("product_id"));
                    TefalApp.getInstance().setWhereFrom("textile");
                    TefalApp.getInstance().setFlage("dish");
                    TefalApp.getInstance().setFromPush("yes");

                }

                if (category.equalsIgnoreCase("DB")) {
                    intent = new Intent(getApplicationContext(), ZaaraDaraaActivity.class);
                    intent.putExtra("store_id", remoteMessage.getData().get("store_id"));
                    intent.putExtra("product_id", remoteMessage.getData().get("product_id"));
                }


                if (category.equalsIgnoreCase("A")) {
                    intent = new Intent(getApplicationContext(), AccessoryProductDetailsActivity.class);
                    intent.putExtra("store_id", remoteMessage.getData().get("store_id"));
                    intent.putExtra("product_id", remoteMessage.getData().get("product_id"));

                }


            }


            if (type.equalsIgnoreCase("order")) {
                intent = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                intent.putExtra("order_id", remoteMessage.getData().get("order_id"));
                intent.putExtra(" order_item_id", remoteMessage.getData().get(" order_item_id"));
            }


            if (type.equalsIgnoreCase("mail")) {
                intent = new Intent(getApplicationContext(), MailingSystemActivity.class);
                intent.putExtra("mail_id", remoteMessage.getData().get("mail_id"));
            }


            if (intent == null) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
            }


        } catch (Exception exc) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
        }

        intent.setAction("FromPushNotification");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        final int nId = new Random().nextInt(61) + 20;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = getResources().getString(R.string.notification_channel_id);

            CharSequence name = id.toString();
            String description = "Tefsal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setDescription(description);

            notificationManager.createNotificationChannel(channel);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "id_product")
                    .setSmallIcon(R.drawable.logo_blue) //your app icon
                    .setBadgeIconType(R.drawable.logo_blue) //your app icon
                    .setChannelId(id)
                    .setContentTitle(title)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setColor(255)
                    .setContentText(message)
                    .setWhen(System.currentTimeMillis());


            NotificationManagerCompat notificationManager2 = NotificationManagerCompat.from(this);

            notificationManager2.notify(nId, notificationBuilder.build());


        } else {


            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.logo_blue) //your app icon
                    .setBadgeIconType(R.drawable.logo_blue) //your app icon
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_blue))
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setSound(defaultSoundUri)
                    .setGroup(KEY_GROUP)
                    .setContentIntent(pendingIntent);


            notificationManager.notify(nId, notificationBuilder.build());

        }
    }


    private void handleNotification(String message) {

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {


            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();


        } else {


            Intent intent = new Intent(getApplicationContext(), MyOrderActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String id = "tefsal";

                CharSequence name = id.toString();
                String description = "Tefsal";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(id, name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this

                notificationManager.createNotificationChannel(channel);

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "id_product")
                        .setSmallIcon(R.drawable.logo_blue) //your app icon
                        .setBadgeIconType(R.drawable.logo_blue) //your app icon
                        .setChannelId(id)
                        .setContentTitle("Tefsal")
                        .setAutoCancel(false).setContentIntent(pendingIntent)
                        .setNumber(1)
                        .setColor(255)
                        .setContentText(String.format("Tefsal", 1))
                        .setWhen(System.currentTimeMillis());


                NotificationManagerCompat notificationManager2 = NotificationManagerCompat.from(this);

                notificationManager2.notify(1, notificationBuilder.build());

            } else {


                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo_blue)
                        .setContentTitle("Tefsal")
                        .setContentText(message)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_blue))
                        .setAutoCancel(false)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setSound(defaultSoundUri)
                        .setGroup(KEY_GROUP)
                        .setContentIntent(pendingIntent);


                notificationManager.notify((int) (System.currentTimeMillis() / 1000)/* ID of notification */, notificationBuilder.build());

            }

        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}