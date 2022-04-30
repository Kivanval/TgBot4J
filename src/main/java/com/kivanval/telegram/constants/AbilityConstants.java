package com.kivanval.telegram.constants;

import static com.vdurmont.emoji.EmojiParser.parseToUnicode;

public interface AbilityConstants {
    String START_DESCRIPTION = """
            start using the bot to organise your own queues""";
    String START_REPLY = parseToUnicode("""
            Hello, dear user! :blush:
            This bot will help you create and administer your own queues.
            Use command */help* to find out the bot's functionality! :wink:""");
}
