package com.example.launcher.model;

import com.example.launcher.util.ComponentKey;
import com.example.launcher.util.IntArray;
import com.example.launcher.util.IntSet;
import com.example.launcher.widget.WidgetListRowEntry;

import java.util.ArrayList;
import java.util.HashMap;

public class BgDataModel {
    private static final String TAG = "BgDataModel";
    public int lastBindId = 0;
    public final ArrayList<ItemInfo> workspaceItems = new ArrayList<>();
    public final ArrayList<LauncherAppWidgetInfo> appWidgets = new ArrayList<>();
    public final HashMap<ComponentKey, Integer> deepShortcutMap = new HashMap<>();
    public final WidgetsModel widgetsModel = new WidgetsModel();

    public interface Callbacks {
        int FLAG_HAS_SHORTCUT_PERMISSION = 1 << 0;

        int FLAG_QUIET_MODE_ENABLED = 1 << 1;

        int FLAG_QUIET_MODE_CHANGE_PERMISSION = 1 << 2;

        void clearPendingBinds();
        void bindAllApplications(AppInfo[] apps, int flags);
        void bindDeepShortcutMap(HashMap<ComponentKey, Integer> deepShortcutMap);
        void bindAllWidgets(ArrayList<WidgetListRowEntry> widgets);

    }
    public synchronized IntArray collectWorkspaceScreens() {
        IntSet screenSet = new IntSet();
//        for (ItemInfo item: itemsIdMap) {
//            if (item.container == LauncherSettings.Favorites.CONTAINER_DESKTOP) {
//                screenSet.add(item.screenId);
//            }
//        }
//        if (FeatureFlags.QSB_ON_FIRST_SCREEN || screenSet.isEmpty()) {
//            screenSet.add(Workspace.FIRST_SCREEN_ID);
//        }
        return screenSet.getArray();
    }
}