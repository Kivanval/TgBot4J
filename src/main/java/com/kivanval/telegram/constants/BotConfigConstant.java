package com.kivanval.telegram.constants;

import com.kivanval.telegram.utils.DataSourceFactory;
import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.abilitybots.api.toggle.DefaultToggle;

import javax.sql.DataSource;

public final class BotConfigConstant {

    private BotConfigConstant() {
    }

    public static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    public static final String BOT_USERNAME = System.getenv("BOT_USERNAME");

    public static final AbilityToggle ABILITY_TOGGLE = new DefaultToggle();
    public static final long CREATOR_ID = 932231787;

    public static final DataSource DATA_SOURCE = DataSourceFactory.getPostgresDataSource();


}
