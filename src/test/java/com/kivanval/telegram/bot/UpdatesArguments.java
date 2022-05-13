package com.kivanval.telegram.bot;

import org.junit.jupiter.params.provider.Arguments;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Stream;

class UpdatesArguments {
    static Stream<Arguments> provideUpdatesForCanReplyToGreeting() {
        final long CHAT_ID = 10903L;
        final int UPDATE_ID = 10903;
        final int MESSAGE_ID = 10903;

        Chat chat = new Chat();
        chat.setId(CHAT_ID);

        Message firstMessage = new Message();
        firstMessage.setMessageId(MESSAGE_ID);
        firstMessage.setText("Слава Україні!");
        firstMessage.setChat(chat);

        Update firstUpdate = new Update();
        firstUpdate.setUpdateId(UPDATE_ID);
        firstUpdate.setMessage(firstMessage);

        Message secondMessage = new Message();
        secondMessage.setMessageId(MESSAGE_ID);
        secondMessage.setText("Слава\nУкраїні!");
        secondMessage.setChat(chat);

        Update secondUpdate = new Update();
        secondUpdate.setUpdateId(UPDATE_ID);
        secondUpdate.setMessage(firstMessage);

        Message thirdMessage = new Message();
        thirdMessage.setMessageId(MESSAGE_ID);
        thirdMessage.setText("СЛАВА УКРАЇНІ! :)");
        thirdMessage.setChat(chat);

        Update thirdUpdate = new Update();
        thirdUpdate.setUpdateId(UPDATE_ID);
        thirdUpdate.setMessage(thirdMessage);

        return Stream.of(
                Arguments.of(firstUpdate),
                Arguments.of(secondUpdate),
                Arguments.of(thirdUpdate)

        );
    }
}
