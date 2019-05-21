package com.example.demo.threads.executor;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server6 {

    public static class Task implements Runnable {
        private String name;

        public Task(String name) {
            this.name = name;
        }

        public void run() {
            System.out.printf("%s: Starting at : %s\n", name, new Date());
        }
    }

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor=(ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        System.out.printf("Main: Starting at: %s\n",new Date());
        Task task=new Task("Task");
        executor.scheduleAtFixedRate(task,1, 2, TimeUnit.SECONDS);

        //executor.shutdown();

        /*try {
            executor.awaitTermination(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.printf("Main: Ends at: %s\n",new Date());

    }
}
