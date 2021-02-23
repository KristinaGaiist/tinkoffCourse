package unit2.task3;

import java.util.List;

public class LocalMessenger extends Messenger {

    @Override
    public void sendMessage(Account to, Message message) {
        throw new RuntimeException("method not implemented");
    }

    @Override
    public List<Message> readMessages(Account account) {
        throw new RuntimeException("method not implemented");
    }
}
