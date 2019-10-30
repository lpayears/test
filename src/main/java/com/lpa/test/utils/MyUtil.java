package com.lpa.test.utils;

import java.util.List;

public class MyUtil {

    public static boolean StringNull(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static boolean ListNull(List<?> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean ObjectNull(Object o) {
        if (o == null) {
            return true;
        }
        return false;
    }
}
