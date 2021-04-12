package unit9.task4;

import java.util.concurrent.CompletableFuture;

public class Leg2 implements Runnable {

    private final String name;
    private final Object lock;

    private boolean current;
    private Leg2 nextLeg;

    public Leg2(String name, boolean current, Object lock) {
        this.name = name;
        this.lock = lock;
        this.current = current;
    }

    public static void main(String[] args) {
        Object object = new Object();
        Leg2 left = new Leg2("left", true, object);
        Leg2 right = new Leg2("right", false, object);
        left.setNextLeg(right);
        right.setNextLeg(left);

        CompletableFuture.allOf(
            CompletableFuture.runAsync(left),
            CompletableFuture.runAsync(right)
        ).join();
    }

    public void setTurn() {
        current = true;
    }

    public void setNextLeg(Leg2 nextLeg) {
        this.nextLeg = nextLeg;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                if (current) {
                    System.out.println(name);
                    current = false;
                    nextLeg.setTurn();
                }
            }
        }
    }
}
