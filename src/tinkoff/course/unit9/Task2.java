package unit9;

public class Task2 {

    public static final Object LOCK_1 = new Object();
    public static final Object LOCK_2 = new Object();

    public static void main(String[] args) {
        ThreadOne threadOne = new ThreadOne();
        ThreadTwo threadTwo = new ThreadTwo();

        threadOne.start();
        threadTwo.start();
    }

    private static class ThreadOne extends Thread {

        public void run() {
            synchronized (LOCK_1) {
                System.out.println("ThreadOne захватил LOCK 1...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadOne ждет LOCK 2...");
                synchronized (LOCK_2) {
                    System.out.println("ThreadOne захватил LOCK 1 и LOCK 2...");
                }
            }
        }
    }

    private static class ThreadTwo extends Thread {

        public void run() {
            //Для того, чтобы предотвратить взаимную блокировку, нужно сначала захватить LOCK_1, а затем LOCK_2
            //(как в классе ThreadOne)
            synchronized (LOCK_2) {
                System.out.println("ThreadTwo захватил LOCK 2...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadOne ждет LOCK 1...");
                synchronized (LOCK_1) {
                    System.out.println("ThreadOne захватил LOCK 2 и LOCK 1...");
                }
            }
        }
    }
}
