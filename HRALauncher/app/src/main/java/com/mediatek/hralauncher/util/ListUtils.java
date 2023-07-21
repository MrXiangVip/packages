package com.mediatek.hralauncher.util;

import java.util.List;

public class ListUtils {
    public static <T> int size(List<T> list) {
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}
