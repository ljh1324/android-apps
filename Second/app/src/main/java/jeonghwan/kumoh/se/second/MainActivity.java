package jeonghwan.kumoh.se.second;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                inflateLayout();
            }
        });
    }

    private void inflateLayout() {
        LinearLayout contentsLayout = (LinearLayout) findViewById(R.id.contentsLayout);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.botton, contentsLayout, true);

        Button btnSelect = (Button) findViewById(R.id.btnSelect);
        final CheckBox allDay = (CheckBox) findViewById(R.id.allDays);

        btnSelect.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (allDay.isChecked()) {
                    allDay.setChecked(false);
                } else {
                    allDay.setChecked(true);
                }
            }
        });
    }
}

