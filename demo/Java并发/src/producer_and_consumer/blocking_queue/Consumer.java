package producer_and_consumer.blocking_queue;

import java.util.concurrent.ArrayBlockingQueue;

class Consumer extends Thread{

    private ArrayBlockingQueue<Integer> queue;

    Consumer(ArrayBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        consume();
    }

    private void consume() {
        while(true){
            try {
                queue.take();
                System.out.println("消费者消费了一个数据！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}