package blocking_queue;

import java.util.concurrent.ArrayBlockingQueue;

class Producer extends Thread{

    private ArrayBlockingQueue<Integer> queue;

    Producer(ArrayBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        produce();
    }

    private void produce() {
        while(true){
            try {
                queue.put(1);
                System.out.println("生产者生产了一个数据！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
