package unit2.task3;

import java.util.Date;
import java.util.List;

public abstract class Message {

    private static int count = 0;

    private int id;
    private final Date date;
    private final Account sender;
    private final List<Attachment> attachments;

    public Message(Account sender, List<Attachment> attachments) {
        this.date = new Date();
        this.sender = sender;
        this.attachments = attachments;
        id = count++;
    }

    public int getId() {
        return id;
    }

    public abstract String getContent();

    @Override
    public String toString() {
        return getContent();
    }
}
