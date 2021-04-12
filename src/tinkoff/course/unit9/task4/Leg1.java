package unit9.task4;

import java.util.concurrent.CompletableFuture;

public class Leg1 implements Runnable {

    private static String turn;
    private final String name;
    private final String nextTurn;
    private final Object lock;

    public Leg1(String name, String nextTurn, Object lock) {
        this.name = name;
        this.nextTurn = nextTurn;
        this.lock = lock;
    }

    public static void setTurn(String turn) {
        Leg1.turn = turn;
    }

    public static void main(String[] args) {
        Object object = new Object();
        Leg1.setTurn("left");
        CompletableFuture.allOf(
            CompletableFuture.runAsync(new Leg1("left", "right", object)),
            CompletableFuture.runAsync(new Leg1("right", "left", object))
        ).join();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                if (name.equals(turn)) {
                    System.out.println(name);
                    turn = nextTurn;
                }
            }
        }
    }
}
