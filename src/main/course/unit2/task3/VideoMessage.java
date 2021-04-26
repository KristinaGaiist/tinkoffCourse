package unit2.task3;

import java.util.List;

public class VideoMessage extends Message {

    private final String videoUrl;

    public VideoMessage(Account sender, String videoUrl, List<Attachment> attachments) {
        super(sender, attachments);
        this.videoUrl = videoUrl;
    }

    @Override
    public String getContent() {
        return videoUrl;
    }
}
