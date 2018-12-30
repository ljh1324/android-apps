package com.example.notificationexample2;

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
        notificationManager.cancel(12345);

 }
}
