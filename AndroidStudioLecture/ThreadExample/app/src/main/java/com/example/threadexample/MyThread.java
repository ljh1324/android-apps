package com.example.threadexample;

import android.util.Log;

public class MyThread extends Thread{

	@Override
	public void run() {
		super.run();
		try {
			for(int i=0; i<5; i++){
				Thread.sleep(1000);
				Log.e("t2:[thread]", "Thread talking: " + i);
			}
		} catch (InterruptedException e) {
			 Log.e("t2:[thread]", e.getMessage() ); 		
     }
	}//run

}//MyThread

