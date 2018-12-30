package com.sample.camera;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);       // 자바 스크립트를 허용하기 위해
        webView.loadUrl("http://192.168.35.162:8080");          // 처음 이동할 URL 설정
        webView.setWebViewClient(new WebViewClient());          // 웹뷰 클라이언트 설정 만약 이 부분이 생략되면, 안드로이드 OS는 지정한 URL을 기본 브라우저로 실행

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCache(webView, "test");
            }
        });
    }

    public void makeCache(View v, String filename){

        String StoragePath =
                Environment.getExternalStorageDirectory().getAbsolutePath();
        String savePath = StoragePath + "/SDCARD";
        File f = new File(savePath);
        if (!f.isDirectory())f.mkdirs();

        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();
        FileOutputStream fos;
        try{
            fos = new FileOutputStream(savePath+"/"+filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,fos);
            //String imageSaveUri = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "사진저장", "저장되었습니다");
            //Uri uri = Uri.parse(imageSaveUri);
            //sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

            Toast.makeText(getApplicationContext(), "찍은 사진이 저장되었습니다", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private WebView webView;
    private Button button;
}
