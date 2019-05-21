package com.example.demo.threads.executor;

import java.util.concurrent.*;

public class Server7 {

    public static class ExecutableTask implements Callable<String> {
        private String name;

        public String getName() {
            return name;
        }

        public ExecutableTask(String name) {
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            try {
                long duration = (long) (Math.random() * 10);
                System.out.printf("%s: Waiting %d seconds for results.\n", this.name, duration);
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
            }
            return "Hello, world. I'm " + name;
        }
    }



    public static class ResultTask extends FutureTask<String> {

        private String name;

        public ResultTask(Callable<String> callable) {
            super(callable);
            this.name=((ExecutableTask)callable).getName();
        }

        @Override
        protected void done() {
            if (isCancelled()) {
                System.out.printf("%s: Has been canceled\n",name);
            } else {
                System.out.printf("%s: Has finished\n",name);
            }
        }

    }

    public static void main(String[] args) {
        ExecutorService executor=(ExecutorService) Executors.newCachedThreadPool();
        ResultTask resultTasks[]=new ResultTask[5];

        for (int i=0; i<5; i++) {
            ExecutableTask executableTask=new ExecutableTask("Task "+i);
            resultTasks[i]=new ResultTask(executableTask);
            executor.submit(resultTasks[i]);
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        for (int i=0; i<resultTasks.length; i++) {
            resultTasks[i].cancel(true);
        }

        for (int i=0; i<resultTasks.length; i++) {
            try {
                if (!resultTasks[i].isCancelled()){
                    System.out.printf("%s\n",resultTasks[i].get());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }
}
