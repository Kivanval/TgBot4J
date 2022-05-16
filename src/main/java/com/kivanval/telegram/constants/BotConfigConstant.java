package com.kivanval.telegram.constants;

import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.abilitybots.api.toggle.DefaultToggle;

public final class BotConfigConstant {

    private BotConfigConstant() {
    }

    public static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    public static final String BOT_USERNAME = System.getenv("BOT_USERNAME");

    public static final AbilityToggle ABILITY_TOGGLE = new DefaultToggle();
    public static final long CREATOR_ID = 932231787;


}
