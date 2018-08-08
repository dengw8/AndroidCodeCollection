package Object.wait.notify;

import java.util.PriorityQueue;

public class Consumer extends Thread {
    private PriorityQueue<Integer> queue;

    Consumer(PriorityQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        consume();
    }

    private void consume() {
        while(true) {
            synchronized (queue) {
                while(queue.size() == 0) {
                    try {
                        System.out.println("消费者等待数据中......");
                        queue.wait();
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                        queue.notify();
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.poll();
                queue.notify();
                System.out.println("消费者消费了一个数据！");
            }
        }
    }
}
