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
        this.saveFolder = saveFolder ;
    }

    public void run() {
        int day = Calendar.getInstance().get(Calendar.DATE);
        int hour = Calendar.getInstance().get(Calendar.SECOND);
        File saveFolder1 =new File(saveFolder.getPath() + File.separator +day + File.separator +hour) ;
        saveFolder1.mkdirs();
       logger.info("创建文件夹：" + saveFolder1.getPath());
       for(int i = 0;i<50;i++){
           //开启两个线程
           Thread thread1 = new Thread(new WriteThread(2*i));
           Thread thread2 = new Thread(new WriteThread(2*i+1));
           thread1.start();
           thread2.start();
           try {
               thread1.join();
               thread2.join();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
       logger.info("已经完成一个小时的文件写入任务");
    }

    class WriteThread implements Runnable{
        private int index;

        public WriteThread(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            for (int count = 0;count<10000;count++){
                File newfile1 = new File(saveFolder.getPath()+ File.separator +index+ File.separator + count +".txt");
                newfile1.getParentFile().mkdirs();
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
                    logger.error("thread" + index +"   在" + count + "次创建文件出现异常: " + e.toString());
                }
            }
            logger.info("thread1  完成 10000  文件任务");
        }
    }
}
