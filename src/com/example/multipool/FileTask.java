package com.example.multipool;
import java.util.concurrent.Callable;

public class FileTask implements Callable<String> {
    private String fileName;

    public FileTask(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String call() throws Exception {
        try {
            System.out.println("Processing file: " + fileName);
            // 模拟文件读取
            Thread.sleep(1000);  // 模拟 1 秒的文件读取时间
            // 模拟数据处理
            Thread.sleep(2000);  // 模拟 2 秒的数据处理时间
            // 返回处理结果
            return "File processed: " + fileName;
        } catch (InterruptedException e) {
            System.out.println("Task was interrupted for file: " + fileName);
            throw e;
        }
    }
}
