package jeonghwan.kumoh.se.myfirstapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button confirmBtn01 = (Button)findViewById(R.id.confirmBtn01);
        confirmBtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText wordText = (EditText)findViewById(R.id.wordEditText);
                EditText responseText = (EditText)findViewById(R.id.responseEditText);
                String word = wordText.getText().toString().split("\\s+")[0];       // 단어가 여러개일 경우를 대비해서 스페이스바 단위로 쪼갠것의 맨 앞 string
                String response = responseText.getText().toString();

                SharedPreferences setting = getSharedPreferences("setting", 0);     // setting.xml 파일에 저장, 0: read, write 가능
                SharedPreferences.Editor editor = setting.edit();

                editor.putString(word, response);           // key: word, value: response

                editor.commit();                            // 값의 변동을 저장한다. (or editor.apply())
            }
        });

        Button confirmBtn02 = (Button)findViewById(R.id.confirmBtn02);
        confirmBtn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText sendMsg = (EditText)findViewById(R.id.sendMsgEditText);
                String[] words = sendMsg.getText().toString().split("\\s+");
                String response = "";

                for (int i = 0; i < words.length; ++i)
                {
                    SharedPreferences setting = getSharedPreferences("setting", 0);
                    String text = setting.getString(words[i], "_DONT_EXIST__");
                    if (!text.equals("_DONT_EXIST__"))
                        response += " " + text;
                }

                TextView textView = (TextView)findViewById(R.id.responseTextView);
                textView.setText(response);
            }
        });
    }

}
