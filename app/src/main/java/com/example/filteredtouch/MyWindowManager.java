package com.example.filteredtouch;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

public class MyWindowManager {


    private WindowManager.LayoutParams mParams;
    private View mView = null;
    private Context mContext;
    private WindowManager mWindowManager;

    public MyWindowManager(Context context){
        mContext = context;
        init();
    }

    public void init() {

        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR,
                PixelFormat.TRANSLUCENT);

        mParams.gravity = Gravity.TOP | Gravity.LEFT;
        mParams.x = 0;
        mParams.y = 0;
        mWindowManager = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
    }

    public void addView(View view){
        mView = view;
        mWindowManager.addView(mView, mParams);
    }

    public void destroy() {
        if(mView != null) {
            mWindowManager.removeView(mView);
        }
    }
}
