public class DeadLockExample {
    final Object lock1 = new Object();
    final Object lock2 = new Object();

    Thread thread1 = new Thread(() -> {
        synchronized (lock1) {
            System.out.println("1");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lock2) {
                System.out.println("2");
            }
        }
    });

    Thread thread2 = new Thread(() -> {
        synchronized (lock2) {
            System.out.println("2");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lock1) {
                System.out.println("1");
            }
        }
    });

    public static void main(String[] args) {
        DeadLockExample example = new DeadLockExample();

        example.thread1.start();
        example.thread2.start();
    }
}
