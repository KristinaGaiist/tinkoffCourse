package unit2.task3;

public class VideoAttachment implements Attachment {

    private final String videoName;
    private final int size;
    private final String extension;

    public VideoAttachment(String videoName, int size, String extension) {
        this.videoName = videoName;
        this.size = size;
        this.extension = extension;
    }

    @Override
    public String print() {
        return videoName + " " + size + " " + extension;
    }
}
