package com.example.filteredtouch;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;


public class AccessibilityClick implements A11yGestures {

    private static final String TAG = "AccessibilityClick";

    private static AccessibilityService service;
    private static AccessibilityNodeInfoCompat mNode;


    @Override
    public void click(AccessibilityService caller, int mPosX, int mPosY) {
        service = caller;

        mNode = findNode();
        AccessibilityNodeInfoCompat comp = findComponentClickable(mNode, mPosX, mPosY);

        if (comp != null) {
            boolean s = comp.performAction(AccessibilityNodeInfoCompat.ACTION_CLICK);
        }
        mNode.recycle();
    }


    private static AccessibilityNodeInfoCompat findNode(){
        AccessibilityNodeInfoCompat node = getCursor();
        int index = 0;

        while (node == null && index < 20) {
            node = getCursor();
            index++;
        }
        return node;
    }


    public static AccessibilityNodeInfoCompat getCursor() {
        final AccessibilityNodeInfoCompat compatRoot, focusedNode;

        final AccessibilityNodeInfo activeRoot = service.getRootInActiveWindow();
        if (activeRoot == null) {
            return null;
        }

        compatRoot = AccessibilityNodeInfoCompat.wrap(activeRoot);
        focusedNode = compatRoot.findFocus(AccessibilityNodeInfoCompat.FOCUS_ACCESSIBILITY);

        if (focusedNode == null) {
            Log.d(TAG, "focused node is null");
            return compatRoot;
        }

        return focusedNode;
    }


    private static AccessibilityNodeInfoCompat findComponentClickable(AccessibilityNodeInfoCompat root, int posX, int posY) {
        try {
            Rect window = new Rect();
            AccessibilityNodeInfoCompat node = null;

            for (int i = 0; i < root.getChildCount(); i++) {
                root.getChild(i).getBoundsInScreen(window);
                if (window.contains(posX, posY)) {
                    if (root.getChild(i).getChildCount() > 0) {
                        node = findComponentClickable(root.getChild(i), posX, posY);
                    }
                    if (node == null && root.getChild(i).isClickable()) {
                        node = root.getChild(i);
                    }
                }

            }
            return node;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
