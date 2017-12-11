package test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CephTest {
    public static void main(String[] args) {
//        采用单线程池，如果执行时间大于周期，会串行执行，如果线程池大小大于1，再没执行玩任务倒是间了会由空闲线程去执行。
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        File savefolder = new File("/home/ceph/testfile/");
        CephTask task = new CephTask(savefolder);
        System.out.printf("起始时间：%s\n\n", new SimpleDateFormat("HH:mm:ss").format(new Date()));

        //第一个参数是指多久后开始，第二个参数是多久执行一次，第三个是单位。这里1小时执行一次。
        timer.scheduleAtFixedRate(new CephTask(savefolder),0,1, TimeUnit.SECONDS);
    }
}
