package com.kivanval.telegram.abilities;

import com.kivanval.telegram.constants.AbilityConstant;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class JoinAbility implements AbilityExtension {

    public Ability replyToJoin() {
        return Ability.builder()
                .name(AbilityConstant.JOIN)
                .info(AbilityConstant.JOIN_DESCRIPTION)
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .input(1)
                .action(ctx -> {
                    Long id = Long.parseLong(ctx.firstArg());

                    ctx.bot().silent()
                            .execute(SendMessage.builder()
                                    .disableWebPagePreview(true)
                                    .parseMode("HTML")
                                    .chatId(String.valueOf(ctx.chatId()))
                                    .text(AbilityConstant.JOIN_REPLY)
                                    .build());


                })
                .build();
    }
}
