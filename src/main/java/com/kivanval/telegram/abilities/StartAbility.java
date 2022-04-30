package com.kivanval.telegram.abilities;

import com.kivanval.telegram.constants.AbilityConstants;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.abilitybots.api.util.AbilityUtils;

public class StartAbility implements AbilityExtension {

    public Ability replyToStart() {
        return Ability.builder()
                .name("start")
                .info(AbilityConstants.START_DESCRIPTION)
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .action(ctx -> ctx.bot().silent()
                        .sendMd(AbilityConstants.START_REPLY, AbilityUtils.getChatId(ctx.update())))
                .build();
    }
}
