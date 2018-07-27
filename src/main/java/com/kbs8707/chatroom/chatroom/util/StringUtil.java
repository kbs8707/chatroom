package com.kbs8707.chatroom.chatroom.util;

import java.util.Map;

public class StringUtil {

    public static String mapToString (Map<String,String> map) {
        if (map.isEmpty()) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        for (String key : map.keySet()) {
            str.append(map.get(key)).append(",");
        }
        return str.toString().substring(0, str.length() - 1);
    }
}
