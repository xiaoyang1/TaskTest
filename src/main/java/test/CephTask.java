package test;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CephTask implements Runnable {
    public static Logger logger = Logger.getLogger(CephTask.class);
    private final SimpleDateFormat dateFormat;

    private final  File saveFolder;  //文件路径只到文件夹外部

    public CephTask(File saveFolder) {
        this.dateFormat = new SimpleDateFormat("HH:mm:ss");
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        this.saveFolder =new File(saveFolder.getPath() + File.separator +day + File.separator +hour) ;
        saveFolder.mkdirs();
    }

    public void run() {
       for(int i = 0;i<50;i++){
           //开启两个线程
           Thread thread1 = new Thread(new Runnable() {
               public void run() {
                   for (int count = 0;count<9999;count++){
                       File newfile1 = new File(saveFolder.getPath() + File.separator + count +".txt");
                       FileWriter  fw1 = null;
                       try {
                           fw1 = new FileWriter(newfile1);
                           fw1.write('a');
                           fw1.close();
                       } catch (IOException e) {
                           if(fw1 != null) {
                               try {
                                   fw1.close();
                               } catch (IOException e1) {
                               }
                           }
                           logger.error("thread1  " + count + "出现异常: " + e.toString());
                       }
                   }
                   logger.info("thread1  完成 10000  文件任务");
               }
           });
           Thread thread2 = new Thread(new Runnable() {
               public void run() {
                   for (int count = 0;count<9999;count++){
                       File newfile2 = new File(saveFolder.getPath() + File.separator + count +".txt");
                       FileWriter  fw2 = null;
                       try {
                           fw2 = new FileWriter(newfile2);
                           fw2.write('a');
                           fw2.close();
                       } catch (IOException e) {
                           if(fw2 != null) {
                               try {
                                   fw2.close();
                               } catch (IOException e1) {
                               }
                           }
                           logger.error("thread1  " + count + "出现异常: " + e.toString());
                       }
                   }
                   logger.info("thread2  完成 10000  文件任务");
               }
           });
           thread1.start();
           thread2.start();
           try {
               thread1.join();
               thread2.join();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           logger.info("已经完成一个小时的文件写入任务");
       }
    }
}
