import java.util.concurrent.locks.ReentrantLock;

public class LiveLockExample {
    final ReentrantLock lock1 = new ReentrantLock();
    final ReentrantLock lock2 = new ReentrantLock();

    Thread thread1 = new Thread(() -> {
        while (true) {
            if (lock1.tryLock()) {
                System.out.println("Thread 1: взял lock1");

                try {
                    Thread.sleep(1000);

                    if (lock2.tryLock()) {
                        try {
                            System.out.println("Thread 1: взял lock2");
                            break;
                        } finally {
                            lock2.unlock();
                        }
                    } else {
                        System.out.println("Thread 1: lock2 занят, отпускаю lock1");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock1.unlock();
                }
            }
        }
    });

    Thread thread2 = new Thread(() -> {
        while (true) {
            if (lock2.tryLock()) {
                System.out.println("Thread 2: взял lock2");

                try {
                    Thread.sleep(1000);

                    if (lock1.tryLock()) {
                        try {
                            System.out.println("Thread 2: взял lock1");
                            break;
                        } finally {
                            lock1.unlock();
                        }
                    } else {
                        System.out.println("Thread 2: lock1 занят, отпускаю lock2");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock2.unlock();
                }
            }
        }
    });

    public static void main(String[] args) {
        LiveLockExample example = new LiveLockExample();

        example.thread1.start();
        example.thread2.start();
    }
}