package com.kivanval.telegram.constants;

import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.abilitybots.api.toggle.DefaultToggle;

public interface BotConfigConstants {
    String BOT_TOKEN = System.getenv("BOT_TOKEN");
    String BOT_USERNAME = System.getenv("BOT_USERNAME");

    AbilityToggle ABILITY_TOGGLE = new DefaultToggle();
    long CREATOR_ID = 932231787;


}
