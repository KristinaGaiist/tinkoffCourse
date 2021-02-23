package unit2.task3;

public class MessengerFactory {

    public static Messenger createMessenger() {
        return new InMemoryMessenger();
    }
}
