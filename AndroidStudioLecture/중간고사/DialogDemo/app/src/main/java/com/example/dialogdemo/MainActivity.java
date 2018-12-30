package com.example.dialogdemo;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
// example adapted from: 
// http://developer.android.com/reference/android/app/DialogFragment.html

public class MainActivity extends Activity implements OnClickListener {
    TextView txtMsg;
    Button btnCustomDialog;
    Button btnAlertDialog;
    Button btnDialogFragment;
    Button btnDialog9;
    Context activityContext;
    String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityContext = this;

        txtMsg = (TextView) findViewById(R.id.txtMsg);
        btnAlertDialog = (Button) findViewById(R.id.btn_alert_dialog1);
        btnCustomDialog = (Button) findViewById(R.id.btn_custom_dialog);
        btnDialogFragment = (Button) findViewById(R.id.btn_alert_dialog2);

        btnCustomDialog.setOnClickListener(this);
        btnAlertDialog.setOnClickListener(this);
        btnDialogFragment.setOnClickListener(this);

        btnDialog9 = (Button) findViewById(R.id.btn_alert_dialog9);///
        btnDialog9.setOnClickListener(this);///

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnAlertDialog.getId()) {
            showMyAlertDialog(this);
        }
        if (v.getId() == btnCustomDialog.getId()) {
            showCustomDialogBox();
        }
        if (v.getId() == btnDialogFragment.getId()) {
            showMyAlertDialogFragment(this);
        }
        if (v.getId() == btnDialog9.getId()) {
            showMyAlertDialog9(this);
        }

    }// onClick

    // ---------------------------------------------------------------------------------------
    private void showMyAlertDialog(MainActivity mainActivity) {

        new AlertDialog.Builder(mainActivity)
                .setTitle("Terminator")
                .setMessage("Are you sure that you want to quit?")
                .setIcon(R.drawable.ic_menu_end_conversation)

                // set three option buttons
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // actions serving "YES" button go here
                                msg = "YES " + Integer.toString(whichButton);            // Positive 버튼일 경우 1번
                                txtMsg.setText(msg);
                            }
                        })// setPositiveButton

                .setNeutralButton("Cancel",                                                    // 중간 버튼
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // actions serving "CANCEL" button go here
                                msg = "CANCEL " + Integer.toString(whichButton);        // NeutralButton 버튼일 경우 2번
                                txtMsg.setText(msg);
                            }// OnClick
                        })// setNeutralButton

                .setNegativeButton("NO", new DialogInterface.OnClickListener() {                // 주석처리 할 경우 No 버튼이 사라진다
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // actions serving "NO" button go here
                        msg = "NO " + Integer.toString(whichButton);                  // NegativeButton 버튼일 경우 3번
                        txtMsg.setText(msg);
                    }
                })// setNegativeButton

                .create().show();

    }// MakeAlertDialogBox

    // -----------------------------------------------------------------------
    private void showCustomDialogBox() {
        final Dialog customDialog = new Dialog(activityContext);            // 다이알로그의 아버지는 View 클래스이다.
        customDialog.setTitle("Custom Dialog Title");

        // inflate custom layout
        customDialog.setContentView(R.layout.custom_dialog_layout);

        ((TextView) customDialog.findViewById(R.id.sd_textView1))
                .setText("\nMessage line1\nMessage line2\n"
                        + "Dismiss: Back btn, Close, or touch outside");

        final EditText sd_txtInputData = (EditText) customDialog
                .findViewById(R.id.sd_editText1);

        ((Button) customDialog.findViewById(R.id.sd_btnClose))
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtMsg.setText(sd_txtInputData.getText().toString());
                        customDialog.dismiss();                            // 화면이 사라지게 한다.
                    }
                });

        customDialog.show();                                            // 화면에 보여주게 할려고 한다.

    }

    // -----------------------------------------------------------------------
    private void showMyAlertDialogFragment(MainActivity mainActivity) {
        DialogFragment dialogFragment = MyAlertDialogFragment
                .newInstance(R.string.title);
        dialogFragment.show(getFragmentManager(), "MYDIALOGFRAGMENT1");

    }

    private void showMyAlertDialog9(MainActivity mainActivity) {
        final CharSequence[] items = {"Red", "Green", "Blue"};
        new AlertDialog.Builder(mainActivity)
                .setTitle("색상을 선택하세요")        // 제목 설정
                .setItems(items, new DialogInterface.OnClickListener() {    // 목록 클릭시 설정
                    public void onClick(DialogInterface dialog, int index) {
                        txtMsg.setText(items[index]);
                    }
                })
                .create().show();
    }

    public void doPositiveClick(Date time) {
        txtMsg.setText("POSITIVE - DialogFragment picked @ " + time);
    }

    public void doNegativeClick(Date time) {
        txtMsg.setText("NEGATIVE - DialogFragment picked @ " + time);
    }

    public void doNeutralClick(Date time) {
        txtMsg.setText("NEUTRAL - DialogFragment picked @ " + time);
    }

}
