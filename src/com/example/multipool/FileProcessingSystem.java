package com.example.multipool;
import java.util.concurrent.*;

public class FileProcessingSystem {
    private ThreadPoolExecutor threadPool;

    public FileProcessingSystem(int poolSize) {
        this.threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);
    }

    // 提交文件处理任务
    public Future<String> submitTask(String fileName, String fileOut) {
        return threadPool.submit(new FileTask(fileName,fileOut));
    }

    // 监控线程池状态
    public void printThreadPoolStatus() {
        System.out.println("Current active threads: " + threadPool.getActiveCount());
        System.out.println("Total tasks completed: " + threadPool.getCompletedTaskCount());
        System.out.println("Total tasks in the queue: " + threadPool.getQueue().size());
    }

    // 关闭线程池
    public void shutdown() {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
        }
    }

    // 取消任务
    public void cancelTask(Future<String> future) {
        future.cancel(true); // true 表示正在执行的任务也会尝试取消
        System.out.println("Task cancelled");
    }
}
