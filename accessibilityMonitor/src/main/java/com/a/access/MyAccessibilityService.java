package com.a.access;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.lang.reflect.Field;

/**
 * Created by alvin on 17/1/10.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MyAccessibilityService extends AccessibilityService {

    private final String TAG = MyAccessibilityService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        String eventTypeName = getTypeName(eventType);
        Log.e(TAG, "eventType,eventTypeName:" + eventType + "," + eventTypeName);
        AccessibilityNodeInfo sourceNode = event.getSource();
        if (sourceNode == null) {
            return;
        }
        if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            Log.e(TAG, "onclick  " + sourceNode);
        }

        cycleAllNode(sourceNode, 0);
    }

    private void cycleAllNode(AccessibilityNodeInfo source, int deep) {
        if (source == null) {
            return;
        }
        int count = source.getChildCount();

        long mSourceNodeId = -1;

        try {
            Field field = AccessibilityNodeInfo.class.getDeclaredField("mSourceNodeId");
            field.setAccessible(true);

            mSourceNodeId = field.getLong(source);

        } catch (Exception e) {
            e.printStackTrace();
        }
//
        Log.d(TAG, "cycleAllNode deep:[" + deep + "] sourceId:[" + mSourceNodeId + "] text:" + source.getText()+"] class:["+source.getClassName()+"]");
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo child = source.getChild(i);
            cycleAllNode(child, deep + 1);
        }
    }


    private AccessibilityNodeInfo findNode(AccessibilityNodeInfo source, String nodeText, int deep) {

        if (source == null) {
            return null;
        }
        Log.d(TAG, "findNode deep:" + deep + " node:" + source);
        int count = source.getChildCount();
        if (count == 0) {
            if (TextUtils.isEmpty(source.getText())) {
                return null;
            }
            Log.d(TAG, "findNode deep:" + deep + " node.text:" + source.getText());
            if (nodeText.equalsIgnoreCase(String.valueOf(source.getText()))) {
                Log.d(TAG, "findNode node:" + source);
                return source;
            }
            return null;

        }

        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo child = source.getChild(i);
            AccessibilityNodeInfo node = findNode(child, nodeText, deep + 1);

            if (node != null) {
                return node;
            }

        }
        return null;
    }


    private static String getTypeName(int eventType) {

        String eventTypeName = "";
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventTypeName = "TYPE_VIEW_CLICKED";
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventTypeName = "TYPE_VIEW_FOCUSED";
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                eventTypeName = "TYPE_VIEW_LONG_CLICKED";
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                eventTypeName = "TYPE_VIEW_SELECTED";
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                eventTypeName = "TYPE_VIEW_TEXT_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                eventTypeName = "TYPE_WINDOW_STATE_CHANGED";
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                eventTypeName = "TYPE_NOTIFICATION_STATE_CHANGED";
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                eventTypeName = "TYPE_TOUCH_EXPLORATION_GESTURE_END";
                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                eventTypeName = "TYPE_ANNOUNCEMENT";
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                eventTypeName = "TYPE_TOUCH_EXPLORATION_GESTURE_START";
                break;

            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                eventTypeName = "TYPE_VIEW_HOVER_ENTER";
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                eventTypeName = "TYPE_VIEW_HOVER_EXIT";
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                eventTypeName = "TYPE_VIEW_SCROLLED";
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                eventTypeName = "TYPE_VIEW_TEXT_SELECTION_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                eventTypeName = "TYPE_WINDOW_CONTENT_CHANGED";
                break;
        }

        return eventTypeName;
    }

    @Override
    public void onInterrupt() {

    }
}

