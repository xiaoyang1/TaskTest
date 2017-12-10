package test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CephTest {
    public static void main(String[] args) {
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        File savefolder = new File("/home/ceph/testfile/");
        CephTask task = new CephTask(savefolder);
        System.out.printf("起始时间：%s\n\n", new SimpleDateFormat("HH:mm:ss").format(new Date()));

        timer.scheduleAtFixedRate(task,0,1, TimeUnit.HOURS);
    }
}
