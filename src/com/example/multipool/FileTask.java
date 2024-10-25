package com.example.multipool;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class FileTask implements Callable<String> {
    public String inputPath;
    public String outputPath;
    //    public Array2DRowRealMatrix cie1964_xyz_data;
    ArrayList<ArrayList<Double>> csv_data_raw;
    // 声明方法可能抛出的IOException
    public FileTask(String inputPath,String outputPath){
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }
    public void readcsv(String file_path) {
        File check_file_obj = new File(file_path);
//        System.out.println(check_file_obj.isFile());    // debug
        try (BufferedReader br = Files.newBufferedReader(Paths.get(file_path))) {   // 用Reader的子类BufferedReader，读入是字符串，替代FileInputStream
            // 按字节读入，请用FileInputStream或BufferedInputStream https://www.runoob.com/java/java-files-io.html

            String DELIMITER = ",";
            String line;
            ArrayList<ArrayList<Double>> lines = new ArrayList<>(); // 指定泛型类型
            while ((line = br.readLine()) != null) {
                ArrayList<Double> column_i = new ArrayList<>();
                String[] column = line.split(DELIMITER);
                for (String element : column) {
                    double ele_int = Double.parseDouble(element);   // csv读入默认是String，要转换成double
                    column_i.add(ele_int);
                }
                lines.add(column_i);
            }
            // br.close(); // 释放资源
            // debug
//             System.out.println(lines);  // debug
            this.csv_data_raw = lines;
//            this.csv_data = new Array2DRowRealMatrix(temp_array);
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println("IOException");
        }
    }
    public void readcsv(){
        this.readcsv(this.inputPath);
    }
    public void writecsvHead(String outputPath){
        // write first 10 lines into file

        try {    // 用FileOutputStream，写入内容是字节)
            File outf = new File(outputPath);
            if (!outf.exists()){
                outf.createNewFile();
            }
            FileOutputStream fp = new FileOutputStream(outf);
            int row_len = this.csv_data_raw.get(0).size();
            OutputStreamWriter osr = new OutputStreamWriter(fp,"UTF-8");
            for(int i=0;i<10;i++){
                for (int j=0;j<row_len;j++){
                    osr.append(String.valueOf(this.csv_data_raw.get(i).get(j))); // double转字符串输入
                    osr.append(",");
                }
                osr.append("\r\n");
            }
            osr.close();    // 执行缓冲区的写入
            // fp.close();
        }catch (IOException e) {
            // TODO: handle exception
            System.out.println("IOException");
        }
    }
    public void writecsvHead(){
        // write first 10 lines into file
        this.writecsvHead(this.outputPath);
    }


    @Override
    public String call() throws Exception { // 在此处实现自定义的文件处理函数
        try {
            System.out.println("Processing file: " + this.inputPath);
            this.readcsv();
            // 模拟文件读取
            Thread.sleep(1000);  // 模拟 1 秒的文件读取时间
            // 模拟数据处理
            Thread.sleep(2000);  // 模拟 2 秒的数据处理时间
            this.writecsvHead();
            // 返回处理结果
            return "File processed: " + this.outputPath;
        } catch (InterruptedException e) {
            System.out.println("Task was interrupted for file: " + this.inputPath);
            throw e;
        }
    }


    public static void main(String[] args){
        FileTask myobj = new FileTask("test_data1.csv","test_data1_out.csv");
        myobj.readcsv();
        myobj.writecsvHead();
    }

}