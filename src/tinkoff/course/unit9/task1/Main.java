package unit9.task1;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String... args) throws InterruptedException {
        Counter counter = new Counter();
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    System.out.println(counter.add());
                }
            });
            threadList.add(thread);
            thread.start();
        }

        for (Thread thread : threadList) {
            thread.join();
        }

        System.out.println("Вывод: " + counter.get());
    }
}
