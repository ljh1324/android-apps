package kr.co.company.fragmenttest1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentA extends Fragment {
    TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // �����׸�Ʈ�� ���̾ƿ��� ��â�Ѵ�. 
        View v = inflater.inflate(R.layout.fragment_a, container, false);
        tv = (TextView)v.findViewById(R.id.textView1);
        Button bt = (Button)v.findViewById(R.id.btnOK);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("Hi!!! This is Frag...");
            }
        });
        return v;
    }
}