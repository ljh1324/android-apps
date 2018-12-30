package com.example.noteexample3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class NotificationReceiverActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        String callerName = getIntent()
                .getStringExtra("callerIntent");
        Toast.makeText(this, "Called by: " + callerName, 1).show();
    }
}
