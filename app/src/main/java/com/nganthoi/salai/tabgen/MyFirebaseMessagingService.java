/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nganthoi.salai.tabgen;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

//this service class receive notification from firebase cloud messaging
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    static final public String Chat_Data = "com.tabgen.Notification.Chat_Data";
    private LocalBroadcastManager broadcaster;
    public final static String CHANNEL_NAME = "com.nganthoi.salai.tabgen.MESSAGE";
    public final static String TEAM_NAME = "team_name";
     int notify_no=0;
    boolean isActivityFound;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        broadcaster = LocalBroadcastManager.getInstance(this);
        sendNotification(remoteMessage.getData().get("message"));
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */

    //
    private void sendNotification(String messageBody) {


        Log.e("message body", messageBody);

        if (!messageBody.equalsIgnoreCase(" ") || !messageBody.equalsIgnoreCase(null)) {
            try {
                JSONObject myJson = new JSONObject(messageBody);
                String notification_type = myJson.getString("notification_type");
                switch (notification_type) {

                    case "like":
                        String likername = myJson.getString("liker_name");
                        String ChannelName_like = myJson.getString("ChannelName");
                        String teamname_like = myJson.getString("TeamName");
                        Log.e("likername", likername);
                        Intent intent = new Intent();
                        intent.setAction(Chat_Data);
                        broadcaster.sendBroadcast(intent);

                        if (!ConversationActivity.active) {
                            notification(likername + " " + "likes a post", "Hcircle Notification", ChannelName_like, teamname_like);
                        }

                        break;

                    case "new_post":
                        String new_post = myJson.getString("message");
                        String sender_name = myJson.getString("sender_name");
                        String ChannelName = myJson.getString("ChannelName");
                        String teamname = myJson.getString("TeamName");
                        Log.e("new_post", new_post);
                        Intent intent_newpost = new Intent();
                        intent_newpost.setAction(Chat_Data);
                        broadcaster.sendBroadcast(intent_newpost);

                        Log.e("conversation",ConversationActivity.active+"");
                          if (!ConversationActivity.active) {
                            notification(new_post, sender_name, ChannelName, teamname);
                        }


                        break;

                    case "comment":
                        String comment = myJson.getString("comment");
                        String ChannelName_comment = myJson.getString("ChannelName");
                        String teamname_comment = myJson.getString("TeamName");
                        Log.e("comment", comment);
                        Intent intent_comment = new Intent();
                        intent_comment.setAction(Chat_Data);
                        broadcaster.sendBroadcast(intent_comment);

                        if (!ConversationActivity.active) {
                            notification(comment, "Hcircle Notification", ChannelName_comment, teamname_comment);
                        }

                        break;

                    default:
                        break;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

//notification function generate notification
    void notification(String mesasge, String contenttilttle, String channelname, String teamname) {

        Intent intent = new Intent(this, ConversationActivity.class);
        intent.putExtra(TEAM_NAME, teamname);
        intent.putExtra(CHANNEL_NAME, channelname);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notify_no /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (notify_no < 9) {
            notify_no = notify_no + 1;
        } else {
            notify_no = 0;
        }

        Long milis = System.currentTimeMillis();
        int id = milis.intValue();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_alarm_small)
                .setContentTitle(contenttilttle)
                .setContentText(mesasge)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationBuilder.build().flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id/* ID of notification */, notificationBuilder.build());
    }

}
