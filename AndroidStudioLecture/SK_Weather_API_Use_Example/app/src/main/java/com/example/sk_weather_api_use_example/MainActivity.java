package com.example.sk_weather_api_use_example;

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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {
    //URL to get JSON Array
    private static String url = "http://apis.skplanetx.com/weather/current/minutely?lon=&village=거의동&county=구미시&stnid=&lat=&city=경북&version=1&appKey=387056b8-ca8f-3ab0-9be2-a1cf447373ef";
    TextView currentT;
    TextView max;
    TextView min;
    TextView humidity;
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

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            currentT = (TextView) findViewById(R.id.current);
            max = (TextView) findViewById(R.id.max);
            min = (TextView) findViewById(R.id.min);
            humidity = (TextView) findViewById(R.id.humididy);

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            HttpURLConnection con = null;
            try {
                URL myurl = new URL(url);
                con = (HttpURLConnection) myurl.openConnection();
                int response = con.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    StringBuilder builder = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(                // Version Error
                            new InputStreamReader(con.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new JSONObject(builder.toString());
                } else {
                    Log.e("TAG-error", "Connection Error!");
                }

            } catch (Exception e) {

                e.printStackTrace();

            } finally {
                con.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();

            try {

                JSONObject c = json.getJSONObject("weather");
                user = c.getJSONArray("minutely");
                JSONObject j = user.getJSONObject(0);
                JSONObject xx = j.getJSONObject("temperature");
                String current = xx.getString("tc");
                String maxx = xx.getString("tmax");
                String minn = xx.getString("tmin");
                double humidity_double = j.getDouble("humidity");
                humidity.setText("현재 습도 " +  String.valueOf(humidity_double));
                currentT.setText("현재 기온 " + current+ " \u2103"); // 2109
                min.setText("최저 기온 " + minn+ " \u2103");
                max.setText("최고 기온 " + String.format("%.01f", Float.parseFloat(maxx))+ " \u2103");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
