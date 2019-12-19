package com.example.filteredtouch;


import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "MyAccessibilityService";
    private Singleton mSingleton = Singleton.getInstance();


    @Override
    public void onServiceConnected() {
        mSingleton.isA11yServiceEnabled = true;
        mSingleton.mA11yService = this;
    }


    @Override
    public void onInterrupt() {
        mSingleton.isA11yServiceEnabled = false;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onDestroy() {

        mSingleton.isA11yServiceEnabled = false;
        mSingleton.mA11yService = null;
        super.onDestroy();
    }

}
