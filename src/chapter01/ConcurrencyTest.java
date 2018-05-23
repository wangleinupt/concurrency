package chapter01;

public class ConcurrencyTest {
    private static long count = 1000000000L;

    public static void main(String[] args) throws InterruptedException{
        cocncurrency();
        serial();
    }

    public static void cocncurrency() throws InterruptedException {
        long start = System.currentTimeMillis();

        Thread thread = new Thread(new Runnable() {
            int a = 0;

            @Override
            public void run() {
                for (int i = 0; i < count; i++) {
                    a+= 5;
                }
            }
        });

        thread.start();

        int b = 0;

        for (int i = 0; i < count; i++) {
            b--;
        }

        thread.join();

        long times = System.currentTimeMillis() - start;

        System.out.println("concurrecny: " + times + "ms, b = " + b);
    }

    public static void serial() {
        long start = System.currentTimeMillis();

        int a = 0;

        int b = 0;

        for (int i = 0; i < count; i++) {
            a += 5;
        }

        for (int i = 0; i < count; i++) {
            b--;
        }

        long times = System.currentTimeMillis() - start;

        System.out.println("serial: " + times + "ms, b = " + b);
    }
}
