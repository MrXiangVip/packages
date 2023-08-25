package com.example.launcher.popup;

import android.util.Log;

import com.example.launcher.dot.DotInfo;
import com.example.launcher.util.ComponentKey;
import com.example.launcher.util.PackageUserKey;
import com.example.launcher.widget.WidgetListRowEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PopupDataProvider {
    private HashMap<ComponentKey, Integer> mDeepShortcutMap = new HashMap<>();
    private ArrayList<WidgetListRowEntry> mAllWidgets = new ArrayList<>();
    private PopupDataChangeListener mChangeListener = PopupDataChangeListener.INSTANCE;
    private final Consumer<Predicate<PackageUserKey>> mNotificationDotsChangeListener;

    private String TAG="PopupDataProvider";

    public PopupDataProvider(Consumer<Predicate<PackageUserKey>> notificationDotsChangeListener) {
        mNotificationDotsChangeListener = notificationDotsChangeListener;
    }

    public void setDeepShortcutMap(HashMap<ComponentKey, Integer> deepShortcutMapCopy) {
        mDeepShortcutMap = deepShortcutMapCopy;
        Log.d(TAG, "bindDeepShortcutMap: " + mDeepShortcutMap);
    }

    public void setAllWidgets(ArrayList<WidgetListRowEntry> allWidgets) {
        mAllWidgets = allWidgets;
        mChangeListener.onWidgetsBound();
    }

    public interface PopupDataChangeListener {

        PopupDataChangeListener INSTANCE = new PopupDataChangeListener() { };

        default void onNotificationDotsUpdated(Predicate<PackageUserKey> updatedDots) { }

        default void trimNotifications(Map<PackageUserKey, DotInfo> updatedDots) { }

        default void onWidgetsBound() { }
    }
}
