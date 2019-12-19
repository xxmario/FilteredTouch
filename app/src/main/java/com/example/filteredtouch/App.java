package com.example.filteredtouch;

import android.app.Application;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;

public class App extends Application {

    private Singleton mSingleton = Singleton.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();

        mSingleton.mContext = getApplicationContext();

        if(AndroidSettings.isDrawOnTopEnabled(getApplicationContext())){
            mSingleton.isDrawOnTopEnabled = true;
        } else {
            mSingleton.isDrawOnTopEnabled = false;
        }

        isA11ServiceEnabled();
        registerA11yServiceObserver();
    }

    private void isA11ServiceEnabled() {
        if(AndroidSettings.isA11yServiceEnabled(getApplicationContext(), MyAccessibilityService.class)){
            mSingleton.isA11yServiceEnabled = true;
        } else {
            mSingleton.isA11yServiceEnabled = false;
        }

    }

    private void registerA11yServiceObserver() {
        //todo: make Observer for "draw Ove other Apps" too
        Uri uri = Settings.Secure.getUriFor(Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        getApplicationContext().getContentResolver().registerContentObserver(uri, false, observerA11yService);
    }

    @Override
    public void onTerminate() {
        unregisterA11yServiceObserver();
        super.onTerminate();
    }

    private void unregisterA11yServiceObserver() {
        getApplicationContext().getContentResolver().unregisterContentObserver(observerA11yService);
    }

    ContentObserver observerA11yService = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            isA11ServiceEnabled();
        }
    };
}
