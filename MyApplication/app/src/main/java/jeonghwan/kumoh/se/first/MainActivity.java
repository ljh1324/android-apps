package jeonghwan.kumoh.se.first;

import android.content.Intent;                  // Intent
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;                       // Menu
import android.view.View.OnClickListener;       // OnClickListener
import android.view.View;                       // View
// Toast. Toast라는 클래스는 잠깐 보였다 없어지는 메세지를 표시할 수 있도록 해 주는 것으로 makeText() 함수와 show() 함수를 이용해 메세지를 보여줄 수 있다
import android.widget.Toast;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버튼 객체 참조
        Button startBtn = (Button)findViewById((R.id.starBtn));
        startBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "StartBtn Click!", Toast.LENGTH_LONG).show();
            }
        });

        Button start02Btn = (Button)findViewById(R.id.start02Btn);
        start02Btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
                startActivity(myIntent);
            }
        });

        Button start03Btn = (Button)findViewById(R.id.start03Btn);
        start03Btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-2981-8539"));
                startActivity(myIntent);
            }
        });


        Button moveNewActivityBtn = (Button)findViewById(R.id.moveNewActivityBtn);
        moveNewActivityBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Move To NewActivity!", Toast.LENGTH_LONG).show();

                Intent myIntent = new Intent(getApplicationContext(), NewActivity.class);
                startActivity(myIntent);
            }
        });

        Button moveTextViewBtn = (Button)findViewById(R.id.moveTextViewBtn);
        moveTextViewBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Move To TextView!", Toast.LENGTH_LONG).show();

                Intent myIntent = new Intent(getApplicationContext(), TextViewActivity.class);
                startActivity(myIntent);
            }
        });

        Button moveButtonViewBtn = (Button)findViewById(R.id.moveButtonViewBtn);
        moveButtonViewBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Move To ButtonView!", Toast.LENGTH_LONG).show();

                Intent myIntent = new Intent(getApplicationContext(), ButtonActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
