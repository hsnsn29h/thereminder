package com.example.thereminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.thereminder.models.NotificationItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {
    boolean isMercySelected = false;
    boolean isrespectSelected = false;
    boolean isropSelected = false;
    boolean ishonestySelected = false;
    boolean istrustSelected = false;
    boolean isjusticeSelected = false;

    ArrayList<NotificationItem> notificationItemArrayList = new ArrayList<>();

    @Override
    public void onReceive(final Context arg0, Intent arg1) {
        //        Toast.makeText(arg0, "Hello reminder", Toast.LENGTH_SHORT).show();

        SharedPreferences prefs = arg0.getSharedPreferences("prefs", arg0.MODE_PRIVATE);
        isMercySelected = prefs.getBoolean("mercy", false);
        isrespectSelected = prefs.getBoolean("respect", false);
        isropSelected = prefs.getBoolean("rop", false);
        ishonestySelected = prefs.getBoolean("honesty", false);
        istrustSelected = prefs.getBoolean("trust", false);
        isjusticeSelected = prefs.getBoolean("justice", false);

        Query query = FirebaseDatabase.getInstance().getReference();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        NotificationItem notificationItem = dataSnapshot1.getValue(NotificationItem.class);
                        if (isMercySelected) {
                            if (notificationItem.id_notfication_value == 1) {
                                notificationItemArrayList.add(notificationItem);
                            }
                        } else if (isrespectSelected) {
                            if (notificationItem.id_notfication_value == 2) {
                                notificationItemArrayList.add(notificationItem);
                            }
                        } else if (isropSelected) {
                            if (notificationItem.id_notfication_value == 3) {
                                notificationItemArrayList.add(notificationItem);
                            }
                        } else if (ishonestySelected) {
                            if (notificationItem.id_notfication_value == 4) {
                                notificationItemArrayList.add(notificationItem);
                            }
                        } else if (istrustSelected) {
                            if (notificationItem.id_notfication_value == 5) {
                                notificationItemArrayList.add(notificationItem);
                            }
                        } else if (isjusticeSelected) {
                            if (notificationItem.id_notfication_value == 6) {
                                notificationItemArrayList.add(notificationItem);
                            }
                        }


                    }

                    if (notificationItemArrayList.size() > 0) {
                        Random randomGenerator = new Random();
                        int index = randomGenerator.nextInt(notificationItemArrayList.size());
                        NotificationItem item = notificationItemArrayList.get(index);
                        showNotification(arg0, item.title, item.message);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private NotificationChannel mChannel;
    Intent notificationIntent;
    NotificationManager notificationManager;
    Notification myNotification;
    private static final int MY_NOTIFICATION_ID = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showNotification(Context context, String title, String message) {


        notificationIntent = new Intent(context, NotificationDetailActivity.class);
        notificationIntent.putExtra("notification_title", title);
        notificationIntent.putExtra("notification_message", message);

        Notification notification = null;
        if (notificationIntent == null) {
            return;
        }
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            notification = new Notification();
            notification.icon = R.mipmap.ic_launcher;
            try {
                Method deprecatedMethod = notification.getClass().getMethod("setLatestEventInfo", Context.class, CharSequence.class, CharSequence.class, PendingIntent.class);
                deprecatedMethod.invoke(notification, this, "Title", null, intent);
            } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                Log.w("TAG", "Method not found", e);
            }
        } else {
            if (notificationManager == null) {
                notificationManager = (NotificationManager) context.getSystemService
                        (Context.NOTIFICATION_SERVICE);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                NotificationCompat.Builder builder;
                PendingIntent pendingIntent;
                int importance = NotificationManager.IMPORTANCE_HIGH;
                if (mChannel == null) {
                    mChannel = new NotificationChannel
                            ("0", title, importance);
                    mChannel.setDescription(message);
                    mChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(mChannel);
                }
                builder = new NotificationCompat.Builder(context, "0");
                pendingIntent = PendingIntent.getActivity(context, 1251, notificationIntent, PendingIntent.FLAG_ONE_SHOT);


                builder.setContentTitle(title)
                        .setSmallIcon(getNotificationIcon()) // required
                        .setContentText(message)  // required
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setBadgeIconType(getNotificationIcon())
                        .setContentIntent(pendingIntent)
                        .setSound(RingtoneManager.getDefaultUri
                                (RingtoneManager.TYPE_NOTIFICATION));


                Notification notification1 = builder.build();
                notificationManager.notify(new Random().nextInt(), notification1);


            } else {


                PendingIntent pendingIntent = PendingIntent.getActivity(
                        context,
                        0,
                        notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                myNotification = new Notification.Builder(context)
                        .setColor(Color.parseColor("#4F6FF8"))
                        .setContentTitle(title)
                        .setContentText(message)
                        .setTicker(title)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setVibrate(new long[]{0L})
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setSmallIcon(getNotificationIcon())
                        .build();
                myNotification.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;


                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(new Random().nextInt(), myNotification);

            }

        }
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }

}
