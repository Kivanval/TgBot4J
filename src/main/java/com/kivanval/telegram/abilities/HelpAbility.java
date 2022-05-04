package com.kivanval.telegram.abilities;

import com.kivanval.telegram.constants.AbilityConstants;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class HelpAbility implements AbilityExtension {

    public Ability replyToHelp() {
        return Ability.builder()
                .name("help")
                .info(AbilityConstants.HELP_DESCRIPTION)
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .action(ctx -> ctx.bot().silent()
                        .execute(SendMessage.builder()
                                .disableWebPagePreview(true)
                                .parseMode("Markdown")
                                .chatId(String.valueOf(AbilityUtils.getChatId(ctx.update())))
                                .text(AbilityConstants.HELP_REPLY)
                                .build()
                        )
                )
                .build();
    }
}
