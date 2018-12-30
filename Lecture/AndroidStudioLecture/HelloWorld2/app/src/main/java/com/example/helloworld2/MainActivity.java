package com.example.helloworld2;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// AppCompatActivity: Activity를 상속받은 클래스
// AppCompatActivity -> Activity로 바꿔도 된다.
public class MainActivity extends AppCompatActivity {
    private Button btnOK, btnCancle;
    private TextView txtOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     // 화면을 채우는 메소드, 화면을 resource에서 관리하면 코드를 많이 안 고챠도 된다.

        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener( new ButtonActionHandler() );
        btnCancle = (Button) findViewById(R.id.btnCancle);
        btnCancle.setOnClickListener( new ButtonActionHandler() );
        txtOut = (TextView) findViewById(R.id.txtOut);
        txtOut.setTextSize(16.0f);

    }

    class ButtonActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == btnOK) {
                txtOut.setText("Clicked!");
                txtOut.setTextSize(32.0f);
            }
            else if (v == btnCancle) {
                txtOut.setText("Hello World!");
                txtOut.setTextSize(16.0f);
            }
        }
    }
}
