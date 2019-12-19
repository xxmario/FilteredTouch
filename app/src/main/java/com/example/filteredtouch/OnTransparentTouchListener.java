package com.example.filteredtouch;


public interface OnTransparentTouchListener {

    void onStop();
    void onActionUp(int x, int y);
    void onActionDown(int x, int y);
}
