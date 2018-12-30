package com.example.notificationexample;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

public class NotificationReceiverActivity1 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result1);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
// notification ID is 12345
        notificationManager.cancel(12345);      // status에 나오는 값을 없애버린다.

 }
}
