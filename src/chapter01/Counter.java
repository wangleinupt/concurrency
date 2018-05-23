package chapter01;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private int i = 0;

    public static void main(String[] args) {
        final Counter counter = new Counter();

        ArrayList<Thread> threadArrayList = new ArrayList<>(600);

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        counter.safeCount();
                        counter.unSafeCount();
                    }
                }
            });

            threadArrayList.add(thread);
        }

        for (Thread t:
             threadArrayList) {
            t.start();
        }

        for (Thread t :
                threadArrayList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(counter.i);
        System.out.println(counter.atomicInteger);

    }

    public void safeCount() {
        for (; ; ) {
            int i = atomicInteger.get();

            boolean suc = atomicInteger.compareAndSet(i, ++i);

            if (suc) {
                break;
            }
        }
    }

    public void unSafeCount() {
        i++;
    }

}
