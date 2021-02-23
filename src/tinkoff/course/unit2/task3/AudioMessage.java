package unit2.task3;

import java.util.List;

public class AudioMessage extends Message {

    private final String audioUrl;

    public AudioMessage(Account sender, String audioUrl, List<Attachment> attachments) {
        super(sender, attachments);
        this.audioUrl = audioUrl;
    }

    @Override
    public String getContent() {
        return audioUrl;
    }
}
