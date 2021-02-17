package unit2.task3;

public class FileAttachment implements Attachment {

    private final String fileUrl;
    private final int size;
    private final String filename;

    public FileAttachment(String fileUrl, int size, String filename) {
        this.fileUrl = fileUrl;
        this.size = size;
        this.filename = filename;
    }

    @Override
    public String print() {
        return filename + " " + fileUrl + " " + size;
    }
}
