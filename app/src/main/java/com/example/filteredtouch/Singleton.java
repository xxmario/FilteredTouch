package com.example.filteredtouch;


import android.content.Context;
import java.io.Serializable;

/**
 * from:
 * https://medium.com/@kevalpatel2106/how-to-make-the-perfect-singleton-de6b951dfdb0
 */
public class Singleton implements Serializable {
    private static final String TAG = "Singleton";

    private static Singleton mSingletonInstance;

    public static Context mContext;
    public static boolean isDrawOnTopEnabled = false;
    public static boolean isA11yServiceEnabled = false;
    public static boolean isAppRunning = false;
    public static MyAccessibilityService mA11yService = null;

    private Singleton(){

        //Prevent form the reflection api.
        if (mSingletonInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

    }


    public static Singleton getInstance() {
        if (mSingletonInstance == null) {
            synchronized (Singleton.class) {
                if (mSingletonInstance == null) mSingletonInstance = new Singleton();
            }
        }

        return mSingletonInstance;
    }

}
