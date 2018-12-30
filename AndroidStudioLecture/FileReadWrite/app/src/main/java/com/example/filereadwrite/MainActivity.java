package com.example.filereadwrite;

// 출처) http://blog.naver.com/javaking75/140179563086

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    static final String FILE_NAME = "exam.txt";
    EditText edit1, edit2;
    Button btnWrite, btnRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit1 = (EditText) findViewById(R.id.text1); // 쓰기용
        edit2 = (EditText) findViewById(R.id.text2); // 읽기용

        btnWrite = (Button) findViewById(R.id.btn1);
        btnRead = (Button) findViewById(R.id.btn2);

        btnWrite.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {

                    FileOutputStream fos = openFileOutput(FILE_NAME,
                            Context.MODE_PRIVATE);      // openFileOutput()을 호출하여 내부 디렉터리의 파일에 데이터를 쓰는 FileOutputStream을 가져온다.
                    String str = edit1.getText().toString();
                    fos.write(str.getBytes()); // String을 byte배열로 변환후 저장
                    fos.close();

                } catch (Exception e) {
                    Log.e("File", "에러=" + e);
                }

            }// onClick
        });
        btnRead.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {

                    FileInputStream fis = openFileInput(FILE_NAME);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    String str = new String(buffer);
                    edit2.setText(str);
                    fis.close();

                } catch (Exception e) {
                    Log.e("File", "에러=" + e);
                }

            }
        });

    }// onCreate

}