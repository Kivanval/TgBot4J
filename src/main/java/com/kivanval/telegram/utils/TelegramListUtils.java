package com.kivanval.telegram.utils;

import com.kivanval.telegram.models.TelegramList;

import java.time.format.DateTimeFormatter;

public final class TelegramListUtils {
    private TelegramListUtils() {
    }

    public static String getInfo(TelegramList list) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd h:mm a");
        final StringBuilder sb = new StringBuilder();
        sb.append("<b>creator</b>: <code>")
                .append(TelegramUserUtils.getName(list.getCreator())).append("</code>");
        sb.append("\n\n<b>id</b>: ")
                .append(list.getId());
        sb.append("\n<b>alias</b>: ")
                .append(list.getAlias() != null ? list.getAlias() : "undefined");
        sb.append("\n\n<b>state</b>: ")
                .append(list.getState());
        sb.append("\n<b>max size</b>: ")
                .append(list.getMaxSize() != null ? list.getMaxSize() : "undefined");
        sb.append("\n<b>start date</b>: ")
                .append(dateFormat.format(list.getStartDate()));
        sb.append("\n<b>end date</b>: ")
                .append(list.getEndDate() != null ? dateFormat.format(list.getEndDate()) : "undefined");
        return sb.toString();
    }
}
