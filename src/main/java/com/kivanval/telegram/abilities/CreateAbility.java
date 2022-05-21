package com.kivanval.telegram.abilities;

import com.kivanval.telegram.constants.AbilityConstant;
import com.kivanval.telegram.data.repositories.TelegramListRepository;
import com.kivanval.telegram.data.repositories.TelegramUserRepository;
import com.kivanval.telegram.models.TelegramList;
import com.kivanval.telegram.models.TelegramUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.db.Var;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateAbility implements AbilityExtension {
    private DBContext db;

    private TelegramUserRepository userRepository;

    private TelegramListRepository listRepository;

    public ReplyFlow replyToCreate() {
        final Var<Integer> id = db.getVar("ID");
        Reply replyToNo = Reply.of((bot, upd) -> {
            TelegramUser user = TelegramUser.from(upd.getMessage().getFrom());
            TelegramList list = new TelegramList();
            list.setCreator(user);
            list.setStartDate(LocalDateTime.now());
            list.setState(TelegramList.State.FREEZE);
            listRepository.update(list);
            bot.silent().send(AbilityConstant.CREATE_REPLY_NO, getChatId(upd));
        }, Flag.TEXT, hasMessageWith(AbilityConstant.CREATE_NO_BUTTON));


        int value = id.get();
        id.set(value + 1);
        return ReplyFlow.builder(db, value)
                .action((bot, upd) -> bot.silent().execute(
                        SendMessage.builder()
                                .chatId(String.valueOf(getChatId(upd)))
                                .text(AbilityConstant.CREATE_REPLY)
                                .replyMarkup(getChoiceKeyboard())
                                .build()
                ))
                .onlyIf(hasMessageWith("/" + AbilityConstant.CREATE))
                .next(replyToNo)
                .build();
    }

    private static ReplyKeyboardMarkup getChoiceKeyboard() {
        return ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .keyboardRow(new KeyboardRow(Arrays.asList(
                                KeyboardButton.builder().text(AbilityConstant.CREATE_YES_BUTTON).build(),
                                KeyboardButton.builder().text(AbilityConstant.CREATE_NO_BUTTON).build())
                        )
                )
                .build();
    }

    private static Predicate<Update> hasMessageWith(String msg) {
        return upd -> upd.getMessage().getText().equalsIgnoreCase(msg);
    }

}
