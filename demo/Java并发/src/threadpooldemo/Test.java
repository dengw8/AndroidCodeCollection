package threadpooldemo;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) {
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(2);
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("新的线程 1 正在执行耗时任务");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("新的线程 2 正在执行耗时任务");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("新的线程 3 正在执行耗时任务");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
