package unit2.task3;

public class LocationAttachment implements Attachment {

    private final String address;

    public LocationAttachment(String address) {
        this.address = address;
    }

    @Override
    public String print() {
        return address;
    }
}
