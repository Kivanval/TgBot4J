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
            OK, Let's give your list a title.
            If you don't want to do this yet, just write <b>/auto.</b>""");

    public static final String LISTS = "lists";

    public static final String LISTS_DESCRIPTION = "shows existing lists created";

    public static final String JOIN = "join";

    public static final String JOIN_DESCRIPTION = "join the list by id";

    public static final String JOIN_REPLY = "to be continued....";
    public static final String HELP = "help";
    public static final String HELP_DESCRIPTION = "gives out a help message with command descriptions";
    public static final String HELP_REPLY = parseToUnicode("""
            I can help you create and manage your own lists.
            The <a href="(https://github.com/Kivanval/TgBot4J">source code</a> of this bot, if you are interested in reading! :wink:
                    
            You can control me by sending these commands:
                        
            <b>Common Commands</b>
            <b>/%s</b> - %s
            <b>/%s</b> - %s
                        
            <b>View the lists</b>
            <b>/%s</b> - %s
                        
            <b>Manage lists</b>
            <b>/%s</b> - %s
            """).formatted(START, START_DESCRIPTION, HELP, HELP_DESCRIPTION,
            LISTS, LISTS_DESCRIPTION,
            CREATE, CREATE_DESCRIPTION
    );
}
