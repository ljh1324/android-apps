package com.sample.androidstudiondktemp;

/**
 * Created by Administrator on 2016-06-07.
 */
public class NDKTemp {

    // Hello JNI
    public native String stringFromJNI();

    static {
        System.loadLibrary("NDKTemp");
    }
}