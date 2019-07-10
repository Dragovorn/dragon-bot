package com.dragovorn.ircbot.api.util;

import java.util.Map;

public final class StringUtil {

    public static String convertToUrlParams(Map<String, Object> data) {
        StringBuilder builder = new StringBuilder();
        builder.append("?");

        int iteration = 0;

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            builder.append(entry.getKey()).append('=').append(entry.getValue().toString());

            if (++iteration < data.size()) {
                builder.append("&");
            }
        }

        return builder.toString();
    }
}
