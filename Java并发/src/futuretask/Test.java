package futuretask;

import java.util.concurrent.*;

public class Test {
    private final ConcurrentMap<Object, Future<String>> taskCache = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        // 定义一个 Callable 类型的任务
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("现在进入了子线程！");
                Thread.sleep(2000);
                System.out.println("子线程任务执行完毕！");
                return "我是返回值";
            }
        };
        // 使用Calllable实例化一个FutureTask
        FutureTask<String> task = new FutureTask<>(callable);

        // 开启一个新线程开始任务
        new Thread(task).start();

        // 判断任务是否已经完成
        if(task.isDone() == false) {
            System.out.println("还在执行中");
        } else {
            System.out.println("执行完了");
        }

        // 得到新线程执行任务的返回值
        String reponse = "";
        try {
            // 这个时候主线程会被阻塞，直到子线程执行完获取到返回值
            reponse = task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("reponse: " + reponse);
    }


    /* 代码场景
     * 有多个线程执行若干个任务，每个任务最多只能被执行一次。
     * 当多个线程试图同时执行同一个任务的时候，只允许一个线程执行任务，
     * 其他线程需要等待这个任务执行完后才能继续执行
     */
    private String executionTask(final String taskName) throws ExecutionException, InterruptedException {
        while(true) {
            Future<String> future = taskCache.get(taskName);

            if(future != null) {
                Callable<String> task = new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return taskName;
                    }
                };

                FutureTask<String> futureTask = new FutureTask<>(task);
                future = taskCache.putIfAbsent(taskName, futureTask);
                if(future == null) {
                    future = futureTask;
                    futureTask.run();
                }
            }

            try {
                return future.get();
            } catch (CancellationException e) {
                taskCache.remove(taskName, future);
            }
        }
    }
}
