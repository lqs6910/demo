package com.example.demo.threads.executor;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server2 {

    private ThreadPoolExecutor executor;

    public Server2(){
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }

    public static class Task implements Runnable{

        private Date initDate;
        private String name;

        public Task(String name) {
            this.initDate = new Date();
            this.name = name;
        }

        @Override
        public void run() {
            System.out.printf("%s: Task %s: Created on: %s\n",Thread.currentThread().getName(),name,initDate);
            System.out.printf("%s: Task %s: Started on: %s\n",Thread.currentThread().getName(),name,new Date());
            try {
                Long duration=(long)(Math.random()*10);
                System.out.printf("%s: Task %s: Doing a task during %dseconds\n",Thread.currentThread().getName(),name,duration);
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s: Task %s: Finished on: %s\n",Thread.currentThread().getName(),name,new Date());
        }
    }

    public void executeTask(Task task) {
        System.out.printf("Server: A new task has arrived\n");
        executor.execute(task);
        System.out.printf("Server: Pool Size: %d\n",executor.getPoolSize());
        System.out.printf("Server: Active Count: %d\n",executor.getActiveCount());
        System.out.printf("Server: Completed Tasks: %d\n",executor.getCompletedTaskCount());
        System.out.printf("Server: Task Count: %d\n",executor.getTaskCount());
    }

    public void endServer() {
        executor.shutdown();
    }

    public static void main(String[] args) {
        Server2 server=new Server2();
        for (int i=0; i<10; i++){
            Task task=new Task("Task "+i);
            server.executeTask(task);
        }
        //System.out.printf("main Server: Completed Tasks: %d\n",server.executor.getCompletedTaskCount());
        server.endServer();
    }



}
