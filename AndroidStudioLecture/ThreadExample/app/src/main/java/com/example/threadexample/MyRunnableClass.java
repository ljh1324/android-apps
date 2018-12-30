package com.example.threadexample;

import android.util.Log;

public class MyRunnableClass implements Runnable {
	@Override
	public void run() {
		try {
			for (int i = 100; i < 105; i++){
				Thread.sleep(1000);
				Log.e ("t1:<<runnable>>", "runnable talking: " + i);
			}
		} catch (InterruptedException e) {
			Log.e ("t1:<<runnable>>",  e.getMessage() );
		}

	}//run

}//class
