package unit2.task3;

public class Main {

    public static void main(String... args) {

        Messenger messenger = MessengerFactory.createMessenger();
        Account kristina = new Account("Kristina");
        Account alina = new Account("Alina");

        messenger.sendMessage(kristina, new TextMessage(alina, "hello"));
        messenger.sendMessage(alina, new TextMessage(kristina, "hello!"));
        messenger.sendMessage(alina, new TextMessage(kristina, "how a u?"));

        System.out.println(messenger.readMessages(kristina));
        System.out.println(messenger.readMessages(alina));
    }
}
