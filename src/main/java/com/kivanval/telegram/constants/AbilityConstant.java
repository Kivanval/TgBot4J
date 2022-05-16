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
            Use command */help* to find out the bot's functionality! :wink:""");

    public static final String CREATE = "create";

    public static final String CREATE_DESCRIPTION = "create new list";

    public static final String HELP = "help";
    public static final String HELP_DESCRIPTION = "gives out a help message with command descriptions";

    public static final String HELP_REPLY = parseToUnicode("""
            I can help you create and manage your own lists.
            The [source code](https://github.com/Kivanval/TgBot4J) of this bot, if you are interested in reading! :wink:
                    
            You can control me by sending these commands:
                        
            *Common Commands*
            /%s - %s
            /%s - %s
                        
            *Manage lists*
            ---
            """).formatted(START, START_DESCRIPTION, HELP, HELP_DESCRIPTION);
}
