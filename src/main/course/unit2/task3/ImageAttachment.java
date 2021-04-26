package unit2.task3;

public class ImageAttachment implements Attachment {

    private final String imageUrl;
    private final int wight;
    private final int height;
    private final String extension;

    public ImageAttachment(String imageUrl, int wight, int height, String extension) {
        this.imageUrl = imageUrl;
        this.wight = wight;
        this.height = height;
        this.extension = extension;
    }

    @Override
    public String print() {
        return imageUrl + " " + wight + " " + height + " " + extension;
    }
}
