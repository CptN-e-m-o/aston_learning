public class EndlessLoop {
    private final Object turnLock = new Object();

    private boolean isThread1Turn = true;

    Thread thread1 = new Thread(() -> {
        while (true) {
            synchronized (turnLock) {
                while (!isThread1Turn) {
                    try {
                        turnLock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                System.out.println("1");

                isThread1Turn = false;
                turnLock.notifyAll();
            }
        }
    });

    Thread thread2 = new Thread(() -> {
        while (true) {
            synchronized (turnLock) {
                while (isThread1Turn) {
                    try {
                        turnLock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                System.out.println("2");

                isThread1Turn = true;

                turnLock.notifyAll();
            }
        }
    });

    public static void main(String[] args) {
        EndlessLoop example = new EndlessLoop();

        example.thread1.start();
        example.thread2.start();
    }
}