package com.example.notificationexample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
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
        intent.putExtra("notificationID", NOTIFICATION_ID);         // 먼가 넘겨주고 싶은 정보
        // => PendingIntent 객체로 바꿔준다.
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);  // 유예하는 인텐트, 주인이 없는 인텐트 Notification에서 다른 액티비티를 부를 때 반드시 사용해야한다.
        // better way to do previous work

        Intent resultIntent = new Intent(this, NotificationReceiverActivity1.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK // 새로운 작업을 만들고
                | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Activity실행시 스택에 쌓는다. 하지만 이 플래그를 설정함으로써 스택을 비운고 인텐트를 실행한다.
        PendingIntent piResult = PendingIntent.getActivity(this,
                (int) Calendar.getInstance().getTimeInMillis(), resultIntent, 0);


        ///PendingIntent pIntent1 = makePendingIntent(
        ///        NotificationReceiverActivity1.class, "Action1");
        PendingIntent pIntent2 = makePendingIntent(
                NotificationReceiverActivity2.class, "Action2");
        PendingIntent pIntent3 = makePendingIntent(
                NotificationReceiverActivity3.class, "Action3");


// a bitmap to be added in the notification view
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(),
                android.R.drawable.ic_menu_compass); // 안드로이드에서 제공하는 이미지
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("TITLE goes here ...")
                .setContentText("Line of text goes here")
                .addAction(R.drawable.assist, "Action1", piResult)
                //.addAction(android.R.drawable.ic_menu_search, "Action2", pIntent2)
                //.addAction(android.R.drawable.ic_menu_share, "Action3", pIntent3)
                .setSmallIcon(android.R.drawable.star_big_on)
                .setLargeIcon(myBitmap)
                .setLights(0xffcc00, 1000, 500)         // LED가 해당색깔로 깜빡임
                .setVibrate(new long[] { 1000, 1000, 1000 })
                .setContentIntent(pIntent)      // 클릭을 했을 때 액티비티 호출
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));    // audio 파일 제공

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
