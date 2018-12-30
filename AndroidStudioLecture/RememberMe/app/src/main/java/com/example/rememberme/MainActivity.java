package com.example.rememberme;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static String FILE_NAME = "remember.txt";
    private EditText rememberEditText;
    private Button writeBtn, loadBtn, noteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();           // TitleBar 삭제.

        setContentView(R.layout.activity_main);

        initializeComponents();
    }

    private void initializeComponents() {
        rememberEditText = (EditText) findViewById(R.id.rememberEditText);
        writeBtn = (Button) findViewById(R.id.saveBtn);
        loadBtn = (Button) findViewById(R.id.loadBtn);

        writeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fos = openFileOutput(FILE_NAME,
                            Context.MODE_PRIVATE);      // openFileOutput()을 호출하여 내부 디렉터리의 파일에 데이터를 쓰는 FileOutputStream을 가져온다.
                    String str = rememberEditText.getText().toString();
                    fos.write(str.getBytes()); // String을 byte배열로 변환후 저장

                    fos.close();
                } catch (Exception e) {
                    Log.e("File", "Save Error=" + e);
                }

            }// onClick
        });

        loadBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fis = openFileInput(FILE_NAME);

                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    String str = new String(buffer);
                    rememberEditText.setText(str);

                    fis.close();
                } catch (Exception e) {
                    Log.e("File", "Load Error=" + e);
                }
            }
        });

        noteBtn = (Button) findViewById(R.id.noteBtn);
        noteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) MainActivity.this.getSystemService(MainActivity.this.NOTIFICATION_SERVICE);
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), MainActivity.class); //인텐트 생성.
                /*
                public Intent(Context packageContext, Class<?>cls)

                * packageContext :
                    문서에는 어렵게 설명되어있지만, 한마디로 말하면 호출하는 액티비티를 뜻합니다. 여기에서는 Activity1 이 되겠습니다. (Activity1.this)
                *cls :
                    호출할 클래스를 뜻합니다. 여기에서는 Activity2 액티비티를 호출해야하므로, Activity2의 클래스인 Activity2.class를 입력합니다.

                출처: http://androidhuman.com/112 [커니의 안드로이드 이야기]
                */
                Notification.Builder builder = new Notification.Builder(getApplicationContext());
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);//현재 액티비티를 최상으로 올리고, 최상의 액티비티를 제외한 모든 액티비티를 없앤다.

                PendingIntent pendingNotificationIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                //PendingIntent는 일회용 인텐트 같은 개념입니다.
                // FLAG_UPDATE_CURRENT - > 만일 이미 생성된 PendingIntent가 존재 한다면, 해당 Intent의 내용을 변경함.
                // FLAG_CANCEL_CURRENT - .이전에 생성한 PendingIntent를 취소하고 새롭게 하나 만든다.
                // FLAG_NO_CREATE -> 현재 생성된 PendingIntent를 반환합니다.
                // FLAG_ONE_SHOT - >이 플래그를 사용해 생성된 PendingIntent는 단 한번밖에 사용할 수 없습니다

                String note = rememberEditText.getText().toString();

                Calendar calendar = Calendar.getInstance();
                //알람시간 calendar에 set해주기

                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));

                builder.setTicker("RM!")                                                                    // 알림이 뜰때 잠깐 표시되는 텍스트
                        .setSmallIcon(R.drawable.notification)
                        .setWhen(calendar.getTimeInMillis())                                                 // 알림이 출력된 시간 설정
                        .setContentTitle("Remember Me!")                                                   // 상단바 알림 제목
                        .setContentText(note)                                                                // 상단바 알림 내용
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setContentIntent(pendingNotificationIntent)                                        // 실행할 작업이 담긴 PendingIntent
                        .setAutoCancel(true)                                                               // 터치하면 자동으로 지워지도록 설정
                        .setOngoing(true);                                                                 // 진행중 알람
                // 해당 부분은 API 4.1버전부터 작동합니다.
                // setSmallIcon - > 작은 아이콘 이미지
                // setTicker - > 알람이 출력될 때 상단에 나오는 문구.
                // setWhen -> 알림 출력 시간.
                // setContentTitle-> 알림 제목
                // setConentText->푸쉬내용

                notificationManager.notify(1, builder.build()); // Notification send

            }
        });

    } // initializeComponents

}
