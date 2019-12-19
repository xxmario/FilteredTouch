package com.example.filteredtouch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

class TransparentView extends FrameLayout implements View.OnTouchListener {

    private static final String TAG = "TransparentView";
    private Context mContext;

    private Button stopButton;
    private View transparentView;
    private OnTransparentTouchListener listener;

    public TransparentView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TransparentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public TransparentView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }


    private void init() {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.transparent, this, true);
        setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorTransparentGray));

        stopButton = findViewById(R.id.stopButton);
        stopButton.setOnTouchListener(this);
        transparentView = findViewById(R.id.transparentView);
        transparentView.setOnTouchListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(mContext instanceof OnTransparentTouchListener){
            listener = (OnTransparentTouchListener) mContext;
        } else {
            throw new RuntimeException(mContext.toString()
                    + " class must implement the OnTransparentTouchListener interface");
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int positionX = (int)event.getX(0);
        int positionY = (int)event.getY(0);

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                listener.onActionDown(positionX, positionY);
                break;
            case MotionEvent.ACTION_UP:
                if(view.getId() == R.id.stopButton){
                    listener.onStop();
                } else if(view.getId() == R.id.transparentView){
                    listener.onActionUp(positionX, positionY);
                }
                break;
        }

        return true;
    }
}
