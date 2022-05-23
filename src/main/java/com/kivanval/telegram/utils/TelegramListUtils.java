package com.kivanval.telegram.utils;

import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.models.TelegramUser;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class TelegramListUtils {

    private TelegramListUtils() {
    }

    public static String getAutoTitle() {
        List<Emoji> emojis = new ArrayList<>(EmojiManager.getAll());
        Collections.shuffle(emojis);
        return emojis.stream().limit(5).map(Emoji::getUnicode).collect(Collectors.joining());
    }

    public static String getInfo(TelegramList list) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd h:mm a");
        final StringBuilder sb = new StringBuilder();
        sb.append("<b><u>")
                .append(list.getTitle())
                .append("</u></b>");
        sb.append("\n\n<b>creator</b>: ")
                .append("<b><a href=\"tg://user?id=%d\">".formatted(list.getCreator().getId()))
                .append(TelegramUserUtils.getName(list.getCreator()))
                .append("</a></b>");
        sb.append("\n\n<b>id</b>: ")
                .append("<code>")
                .append(list.getId())
                .append("</code>");
        sb.append("\n\n<b>state</b>: ")
                .append(list.getState());
        if (list.getMaxSize() != null) {
            sb.append("\n<b>max size</b>: ")
                    .append(list.getMaxSize());
        }
        sb.append("\n\n<b>start date</b>: ")
                .append(dateFormat.format(list.getStartDate()));
        if (list.getEndDate() != null) {
            sb.append("\n<b>end date</b>: ")
                    .append(dateFormat.format(list.getEndDate()));
        }
        return sb.toString();
    }

    public static String getInfo(List<TelegramList> lists) {
        TelegramUser creator = lists.get(0).getCreator();
        final StringBuilder sb = new StringBuilder();
        sb.append("<b>creator</b>: ")
                .append("<b><a href=\"tg://user?id=%d\">".formatted(creator.getId()))
                .append(TelegramUserUtils.getName(creator))
                .append("</a></b>");
        sb.append("\n\n<b>number of lists</b>: ")
                .append(lists.size());
        sb.append("\n<b>number of people on the lists</b>: ")
                .append(lists.stream().map(TelegramList::getListedPlaces).mapToInt(List::size).sum());
        return sb.toString();
    }
}
