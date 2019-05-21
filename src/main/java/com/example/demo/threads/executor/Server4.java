package com.example.demo.threads.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Server4 {
    public enum Type{
        LDAP,DataBase


    }

    public static class UserValidator {
        private Type type;

        public UserValidator(Type type) {
            this.type = type;
        }

        public boolean validate(String name, String password) {

            try {
                long duration = (long) (Math.random() * 10);
                System.out.printf("Validator %s: Validating a user during %d seconds\n", this.type, duration);
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                return false;
            }
            switch (type){
                case LDAP:
                    return false;
                case DataBase:
                    return true;
                default:
                    return false;
            }

        }

        public Type getType() {
            return type;
        }
    }

    public static class TaskValidator implements Callable<String> {

        private UserValidator validator;
        private String user;
        private String password;

        public TaskValidator(UserValidator validator, String user, String password) {
            this.validator = validator;
            this.user = user;
            this.password = password;
        }

        @Override
        public String call() throws Exception {
            if (!validator.validate(user, password)) {
                System.out.printf("%s: The user has not been found\n", validator.getType());
                throw new Exception("Error validating user");
            }
            System.out.printf("%s: The user has been found\n", validator.getType());
            return validator.getType().name();
        }
    }

    public static void main(String[] args) {
        String user = "test";
        String password = "test";
        UserValidator ldapValidator=new UserValidator(Type.LDAP);
        UserValidator dbValidator=new UserValidator(Type.DataBase);

        TaskValidator ldapTask=new TaskValidator(ldapValidator,user, password);
        TaskValidator dbTask=new TaskValidator(dbValidator,user,password);

        List<TaskValidator> taskList=new ArrayList<>();
        taskList.add(ldapTask);
        taskList.add(dbTask);

        ExecutorService executor= Executors.newCachedThreadPool();
        String result;
        /*List<Future<String>> results;*/

        try {
            result = executor.invokeAny(taskList);
            System.out.printf("Main: Result: %s\n",result);

            /*results = executor.invokeAll(taskList);
            for (Future<String> future : results) {
                System.out.printf("Main: Results: %s\n",future.get());
            }*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}