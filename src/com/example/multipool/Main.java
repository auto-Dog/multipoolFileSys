package com.example.multipool;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        // 创建文件处理系统，线程池大小为3
        FileProcessingSystem fileProcessingSystem = new FileProcessingSystem(3);

        // 提交文件处理任务
        List<Future<String>> futures = new ArrayList<>();
<<<<<<< Updated upstream
        futures.add(fileProcessingSystem.submitTask("test_data1.csv","test_data1_out.csv"));
        futures.add(fileProcessingSystem.submitTask("test_data2.csv","test_data2_out.csv"));
        futures.add(fileProcessingSystem.submitTask("test_data3.csv","test_data3_out.csv"));
        futures.add(fileProcessingSystem.submitTask("test_data4.csv","test_data4_out.csv"));
=======
        futures.add(fileProcessingSystem.submitTask("test_data1.csv","./out/test_data1_out.csv"));
        futures.add(fileProcessingSystem.submitTask("test_data2.csv","./out/test_data2_out.csv"));
        futures.add(fileProcessingSystem.submitTask("test_data3.csv","./out/test_data3_out.csv"));
        futures.add(fileProcessingSystem.submitTask("test_data4.csv","./out/test_data4_out.csv"));
>>>>>>> Stashed changes

        // 监控线程池状态
        fileProcessingSystem.printThreadPoolStatus();

        // 模拟任务取消
        Future<String> futureToCancel = futures.get(2); // 假设取消第三个任务
        fileProcessingSystem.cancelTask(futureToCancel);

        // 获取已完成任务的结果
        for (Future<String> future : futures) {
            try {
                if (!future.isCancelled()) {
                    System.out.println(future.get());  // 获取任务的结果
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // 关闭线程池
        fileProcessingSystem.shutdown();
    }
}
