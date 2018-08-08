package blocking_queue;

import java.util.concurrent.ArrayBlockingQueue;

public class Test {
    private int queueSize = 10;
    private static ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

    public static void main(String[] args) {
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        producer.start();
        consumer.start();
    }
}