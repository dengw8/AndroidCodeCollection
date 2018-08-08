package Object.wait.notify;

import java.util.PriorityQueue;

public class Producer extends Thread{
    private PriorityQueue<Integer> queue;

    Producer(PriorityQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        produce();
    }

    private void produce() {
        while(true) {
            synchronized (queue) {
                while(queue.size() == 10) {
                    try {
                        System.out.println("队列已满，生产者等待中......");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        queue.notify();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.offer(1);
                queue.notifyAll();
                System.out.println("生产者生产了一个数据！");
            }
        }
    }
}
