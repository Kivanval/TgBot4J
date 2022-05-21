package com.kivanval.telegram.abilities;

import com.kivanval.telegram.constants.AbilityConstant;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class HelpAbility implements AbilityExtension {

    public Ability replyToHelp() {
        return Ability.builder()
                .name(AbilityConstant.HELP)
                .info(AbilityConstant.HELP_DESCRIPTION)
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .action(ctx -> ctx.bot().silent()
                        .execute(SendMessage.builder()
                                .disableWebPagePreview(true)
                                .parseMode("HTML")
                                .chatId(String.valueOf(ctx.chatId()))
                                .text(AbilityConstant.HELP_REPLY)
                                .build()
                        )
                )
                .build();
    }
}
