package com.kivanval.telegram.constants;

import static com.vdurmont.emoji.EmojiParser.parseToUnicode;

public final class AbilityConstant {

    private AbilityConstant() {
    }

    public static final String GREETING_REPLY = parseToUnicode("Героям слава! :heart::ua:");

    public static final String START = "start";
    public static final String START_DESCRIPTION = "gives out a welcome message";
    public static final String START_REPLY = parseToUnicode("""
            Hello, dear user! :blush:
            I will help you create and manage your own lists.
            Use command <b>/help</b> to find out the bot's functionality! :wink:""");

    public static final String CREATE = "create";

    public static final String CREATE_DESCRIPTION = "create new list with an alias <b>[optional]</b>";

    public static final String CREATE_REPLY = parseToUnicode("""
            OK, Let's give your list an alias.
            If you don't want to do this yet, just write <b>/empty.</b>""");

    public static final String MY_LISTS = "mylists";

    public static final String MY_LISTS_DESCRIPTION = "shows existing lists created";

    public static final String LIST_INFO = "linfo";

    public static final String LIST_INFO_DESCRIPTION = "shows list properties by ID/Alias";

    public static final String LIST_INFO_REPLY = "OK, but first choose an access method";
    public static final String HELP = "help";
    public static final String HELP_DESCRIPTION = "gives out a help message with command descriptions";

    public static final String LISTS_NAVIGATION_TITLE = parseToUnicode("<u><b>List Information</b></u>");
    public static final String GENERAL_LISTS_TITLE = parseToUnicode("<u><b>General List Information</b></u>");

    public static final String REPLY_ERROR = "Something went wrong, try again later...";
    public static final String HELP_REPLY = parseToUnicode("""
            I can help you create and manage your own lists.
            The <a href="(https://github.com/Kivanval/TgBot4J">source code</a> of this bot, if you are interested in reading! :wink:
                    
            You can control me by sending these commands:
                        
            <b>Common Commands</b>
            <b>/%s</b> - %s
            <b>/%s</b> - %s
                        
            <b>View the lists</b>
            <b>/%s</b> - %s
            <b>/%s</b> - %s
                        
            <b>Manage lists</b>
            <b>/%s</b> - %s
            """).formatted(START, START_DESCRIPTION, HELP, HELP_DESCRIPTION,
            MY_LISTS, MY_LISTS_DESCRIPTION, LIST_INFO, LIST_INFO_DESCRIPTION,
            CREATE, CREATE_DESCRIPTION
    );
}
