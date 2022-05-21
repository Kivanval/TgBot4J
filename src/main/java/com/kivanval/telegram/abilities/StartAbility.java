package com.kivanval.telegram.abilities;

import com.kivanval.telegram.constants.AbilityConstant;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class StartAbility implements AbilityExtension {

    public Ability replyToStart() {
        return Ability.builder()
                .name(AbilityConstant.START)
                .info(AbilityConstant.START_DESCRIPTION)
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .action(ctx -> ctx.bot().silent()
                        .execute(SendMessage.builder()
                                .disableWebPagePreview(true)
                                .parseMode("HTML")
                                .chatId(String.valueOf(ctx.chatId()))
                                .text(AbilityConstant.START_REPLY)
                                .build()
                        )
                )
                .build();
    }


}
