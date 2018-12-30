package com.example.addtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText editUserName;
    private TextView txtLogin;
    private boolean clickButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // binding the UI's controls defined in "main.xml" to Java code
        btnLogin = (Button) this.findViewById(R.id.btnLogin);
        txtLogin = (TextView) this.findViewById(R.id.txtLogin);
        editUserName = (EditText) this.findViewById(R.id.edtUserName);
        clickButton = false;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!clickButton) {
                    int myinput = Integer.parseInt(editUserName.getText().toString());
                    myinput++;
                    txtLogin.setText(String.valueOf(myinput));
                    clickButton = true;
                }
                else
                {
                    int myinput = Integer.parseInt(txtLogin.getText().toString());
                    myinput++;
                    txtLogin.setText(String.valueOf(myinput));
                }
            }
        });
    }
}
