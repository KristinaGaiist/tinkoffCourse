package unit2.task3;

import java.util.List;

public abstract class Messenger {

    public abstract void sendMessage(Account to, Message message);

    public abstract List<Message> readMessages(Account account);
}
