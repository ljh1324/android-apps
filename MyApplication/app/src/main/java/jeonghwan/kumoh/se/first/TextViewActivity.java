package jeonghwan.kumoh.se.first;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;



// Toast. Toast라는 클래스는 잠깐 보였다 없어지는 메세지를 표시할 수 있도록 해 주는 것으로 makeText() 함수와 show() 함수를 이용해 메세지를 보여줄 수 있다

public class TextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview_activity);              // 화면에 무엇(어떤 Activity)을 보여줄지 결정하는 함수


    }
}
