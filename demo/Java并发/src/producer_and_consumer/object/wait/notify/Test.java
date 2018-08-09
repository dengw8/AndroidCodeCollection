package producer_and_consumer.object.wait.notify;

import java.util.PriorityQueue;

public class Test {
    private static PriorityQueue<Integer> queue = new PriorityQueue<Integer>(10);

    public static void main(String[] args)  {
        Consumer consumer1 = new Consumer(queue);
        Consumer consumer2 = new Consumer(queue);

        Producer producer = new Producer(queue);

        //producer.start();
        consumer1.start();
        consumer2.start();
    }
}
