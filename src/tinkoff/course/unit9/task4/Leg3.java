package unit9.task4;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

public class Leg3 implements Runnable {

    private final String name;
    private final Semaphore semaphore;

    private boolean current;
    private Leg3 nextLeg;

    public Leg3(String name, boolean current, Semaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
        this.current = current;
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        Leg3 left = new Leg3("left", true, semaphore);
        Leg3 right = new Leg3("right", false, semaphore);
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

    public void setNextLeg(Leg3 nextLeg) {
        this.nextLeg = nextLeg;
    }

    @Override
    public void run() {
        while (true) {
            try {
                makeTurnSync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void makeTurnSync() throws InterruptedException {
        semaphore.acquire();
        try {
            makeTurn();
        } finally {
            semaphore.release();
        }
    }

    private void makeTurn() {
        if (current) {
            System.out.println(name);
            current = false;
            nextLeg.setTurn();
        }
    }
}
