package com.example.noteexample3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends Activity implements View.OnClickListener {
    NotificationManager notificationManager;
    final int NOTIFICATION_ID = 12345;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnBig).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);
    }// onCreate
    @SuppressLint("NewApi")
    public void createBigNotification(View view) {
        Intent intent = new Intent(this, NotificationReceiverActivity.class);
        intent.putExtra("callerIntent", "main");
        intent.putExtra("notificationID", NOTIFICATION_ID);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // better way to do previous work

        Intent resultIntent = new Intent(this, NotificationReceiverActivity1.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent piResult = PendingIntent.getActivity(this,
                (int) Calendar.getInstance().getTimeInMillis(), resultIntent, 0);


        ///PendingIntent pIntent1 = makePendingIntent(
        ///        NotificationReceiverActivity1.class, "Action1");
        PendingIntent pIntent2 = makePendingIntent(
                NotificationReceiverActivity2.class, "Action2");
        PendingIntent pIntent3 = makePendingIntent(
                NotificationReceiverActivity3.class, "Action3");

        //Assign inbox style notification
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle("Inbox Notification");
        inboxStyle.addLine("Message 1.");
        inboxStyle.addLine("Message 2.");
        inboxStyle.addLine("Message 3.");
        inboxStyle.addLine("Message 4.");
        inboxStyle.addLine("Message 5.");       // Line추가
        inboxStyle.setSummaryText("+2 more");
// a bitmap to be added in the notification view
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(),
                android.R.drawable.ic_menu_compass);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("TITLE goes here ...")
                .setContentText("Second Line of text goes here")
                .addAction(R.drawable.assist, "Action1", piResult)
                //.addAction(android.R.drawable.ic_menu_search, "Action2", pIntent2)
                //.addAction(android.R.drawable.ic_menu_share, "Action3", pIntent3)
                .setSmallIcon(android.R.drawable.star_big_on)
                .setLargeIcon(myBitmap)
                .setLights(0xffcc00, 1000, 500)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setStyle(inboxStyle);

        notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
// notification ID is 12345
        notificationManager.notify(12345, mBuilder.build());
    }// createBigNotification
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBig:
                createBigNotification(v);
                break;
            case R.id.btnCancel :
                try {
                    if ( notificationManager != null){
                        notificationManager.cancel(NOTIFICATION_ID);
                    }
                } catch (Exception e) {
                    Log.e("<<MAIN>>", e.getMessage() );
                }
                break;
        }
    }// onClick
    public PendingIntent makePendingIntent(Class partnerClass, String callerName)
    {
        Intent intent = new Intent(this, partnerClass);
        intent.putExtra("callerIntent", callerName);
        intent.putExtra("notificationID", NOTIFICATION_ID);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        return pIntent;
    }
}
