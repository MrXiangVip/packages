package com.example.launcher.model;

import android.content.Context;

import androidx.annotation.Nullable;

import com.example.launcher.LauncherAppState;
import com.example.launcher.icons.ComponentWithLabelAndIcon;
import com.example.launcher.util.PackageUserKey;
import com.example.launcher.widget.WidgetListRowEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WidgetsModel {

    public synchronized ArrayList<WidgetListRowEntry> getWidgetsList(Context context) {
        ArrayList<WidgetListRowEntry> result = new ArrayList<>();
//        AlphabeticIndexCompat indexer = new AlphabeticIndexCompat(context);
//
//        WidgetItemComparator widgetComparator = new WidgetItemComparator();
//        for (Map.Entry<PackageItemInfo, ArrayList<WidgetItem>> entry : mWidgetsList.entrySet()) {
//            WidgetListRowEntry row = new WidgetListRowEntry(entry.getKey(), entry.getValue());
//            row.titleSectionName = (row.pkgItem.title == null) ? "" :
//                    indexer.computeSectionName(row.pkgItem.title);
//            Collections.sort(row.widgets, widgetComparator);
//            result.add(row);
//        }
        return result;
    }

    public List<ComponentWithLabelAndIcon> update(
            LauncherAppState app, @Nullable PackageUserKey packageUser) {

        List<ComponentWithLabelAndIcon> updatedItems = new ArrayList<>();
        return updatedItems;
    }
}
