package com.example.loginscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText editUserName;
    private TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // binding the UI's controls defined in "main.xml" to Java code
        btnLogin = (Button) this.findViewById(R.id.btnLogin);
        txtLogin = (TextView) this.findViewById(R.id.txtLogin);
        editUserName = (EditText) this.findViewById(R.id.edtUserName);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String myinput = editUserName.getText().toString();
                txtLogin.setText(myinput);
                Log.e("%%%%%%%%%", myinput);
                Log.i("%%%%%%%%%", myinput);
                Log.v("%%%%%%%%%", myinput);
            }
        });
    }
}
