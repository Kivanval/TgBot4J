package com.kivanval.telegram.abilities;

import com.kivanval.telegram.bot.TelegramBot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.util.AbilityExtension;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListInfoAbility implements AbilityExtension {
    private TelegramBot bot;

    public ReplyFlow replyToListInfo() {

        return null;
    }
}
