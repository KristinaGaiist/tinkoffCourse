package unit2.task3;

import java.util.ArrayList;
import java.util.List;

public class TextMessage extends Message {

    private String text;

    public TextMessage(Account sender, String text, List<Attachment> attachments) {
        super(sender, attachments);
        this.text = text;
    }

    public TextMessage(Account sender, String text) {
        super(sender, new ArrayList<>());
        this.text = text;
    }

    public void editText(String text) {
        this.text = text;
    }


    @Override
    public String getContent() {
        return text;
    }
}
