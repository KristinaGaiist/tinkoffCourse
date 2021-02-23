package unit2.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryMessenger extends Messenger {

    private final List<Chat> chats;

    public InMemoryMessenger() {
        chats = new ArrayList<>();
    }

    @Override
    public void sendMessage(Account to, Message message) {
        Optional<Chat> optionalChat = chats.stream()
                .filter(ch -> ch.getAccount().getUserName().equals(to.getUserName()))
                .findFirst();
        Chat chat;
        if (optionalChat.isPresent()) {
            chat = optionalChat.get();
            chat.addMessage(message);
        } else {
            chat = new Chat(to);
            chat.addMessage(message);
            chats.add(chat);
        }
    }

    @Override
    public List<Message> readMessages(Account account) {
        Optional<Chat> optionalChat = chats.stream()
                .filter(ch -> ch.getAccount().getUserName().equals(account.getUserName()))
                .findFirst();
        return optionalChat.isPresent() ? optionalChat.get().getMessages() : new ArrayList<>();
    }
}
