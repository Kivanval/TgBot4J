package com.kivanval.telegram.constants;

import static com.vdurmont.emoji.EmojiParser.parseToUnicode;

public interface AbilityConstants {

    String GREETING_REPLY = parseToUnicode("""
            Героям слава! :heart::ua:""");

    String START = "start";
    String START_DESCRIPTION = """
            gives out a welcome message""";
    String START_REPLY = parseToUnicode("""
            Hello, dear user! :blush:
            I will help you create and manage your own lists.
            Use command */help* to find out the bot's functionality! :wink:""");

    String CREATE = "create";

    String CREATE_DESCRIPTION = "create new list";

    String HELP = "help";
    String HELP_DESCRIPTION = """
            gives out a help message with command descriptions""";

    String HELP_REPLY = parseToUnicode("""
            I can help you create and manage your own lists.
            The [source code](https://github.com/Kivanval/TgBot4J) of this bot, if you are interested in reading! :wink:
                    
            You can control me by sending these commands:
                        
            *Common Commands*
            /%s - %s
            /%s - %s
                        
            *Manage lists*
            /%s - %s
            /%s - %s
            /%s - %s
                        
            """).formatted(START, START_DESCRIPTION, HELP, HELP_DESCRIPTION);
}
