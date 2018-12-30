package com.example.json_reading_example;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity {
    TextView data;
    Button Btngetdata;
    JSONArray user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Btngetdata = (Button) findViewById(R.id.getdata);
        Btngetdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new JSONParse().execute();
            }
        });

    }

    private class JSONParse extends AsyncTask<String, String, String> { // String 입력, String 반환, String 보여주기
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            data = (TextView) findViewById(R.id.data);
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                    StringBuilder builder = new StringBuilder();
                    try {
                        String line;
                        InputStream is = getResources().openRawResource(R.raw.names);
                        InputStreamReader ir = new InputStreamReader(is);
                        BufferedReader reader = new BufferedReader(ir);
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return builder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            pDialog.dismiss();
            StringBuffer sb = new StringBuffer();
            try {
                JSONArray jarray = new JSONArray(json);   // JSONArray 생성
                for(int i=0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                    String name = jObject.getString("name");
                    int age = jObject.getInt("age");
                    sb.append(
                            "이름 : " + name + " 나이 : " + age + "\n"
                    );
                }
                data.setText(sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

