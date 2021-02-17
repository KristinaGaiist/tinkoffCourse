package unit2.task3;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    private Account account;
    private List<Message> messages;

    public Chat(Account account) {
        this.account = account;
        this.messages = new ArrayList<>();
    }

    public Account getAccount() {
        return account;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void deleteMessage(Message message) {
        messages.remove(message);
    }
}
