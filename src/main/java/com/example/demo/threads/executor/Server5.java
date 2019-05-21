package com.example.demo.threads.executor;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server5 {

    public static class Task implements Callable<String> {
        private String name;

        public Task(String name) {
            this.name = name;
        }

        public String call() throws Exception {
            System.out.printf("%s: Starting at : %s\n", name, new Date());
            return "Hello, world";
        }
    }

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor=(ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        System.out.printf("Main: Starting at: %s\n",new Date());
        for (int i=0; i<5; i++) {
            Task task=new Task("Task "+i);
            executor.schedule(task,i+3 , TimeUnit.SECONDS);
        }
        //executor.shutdown();

        try {
            executor.awaitTermination(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Main: Ends at: %s\n",new Date());

    }
}
