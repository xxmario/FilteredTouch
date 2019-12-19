package com.example.filteredtouch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class MyService extends Service implements OnTransparentTouchListener {

    private static final String TAG = "MyService";
    private Singleton mSingleton = Singleton.getInstance();
    private MyWindowManager windowManager;
    private TransparentView transparentView;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        transparentView = new TransparentView(this);
        windowManager = new MyWindowManager(this);
        windowManager.addView(transparentView);
    }

    @Override
    public void onStop() {
        windowManager.destroy();
        stopAllServices();
        startMainActivity();
    }

    private void stopAllServices() {
        if(mSingleton.mA11yService!=null){
            Intent stopServiceIntent = new Intent(this, MyAccessibilityService.class);
            stopService(stopServiceIntent);
        }
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onActionUp(int x, int y) {
        sendClick(x, y);
        transparentView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorTransparentGray));
    }

    @Override
    public void onActionDown(int x, int y) {
        transparentView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorTransparentGreen));
    }


    public void sendClick(int x, int y){
        A11yGestures ac = new AccessibilityClick();
        if(mSingleton.mA11yService!=null) {
            ac.click(mSingleton.mA11yService, x, y);
        } else{
            Toast.makeText(this, "please enable a11y service", Toast.LENGTH_SHORT).show();
        }
    }
}
