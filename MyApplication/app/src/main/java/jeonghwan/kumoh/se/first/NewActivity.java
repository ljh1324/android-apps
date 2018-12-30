package jeonghwan.kumoh.se.first;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

// Toast. Toast라는 클래스는 잠깐 보였다 없어지는 메세지를 표시할 수 있도록 해 주는 것으로 makeText() 함수와 show() 함수를 이용해 메세지를 보여줄 수 있다

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);              // 화면에 무엇(어떤 Activity)을 보여줄지 결정하는 함수

        // 버튼 객체 참조
        Button backBtn = (Button)findViewById((R.id.backBtn));
        backBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Back Button Click!", Toast.LENGTH_LONG).show();
                finish();
            }
        });


    }
}
