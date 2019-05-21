package com.example.demo.threads.lock;

public class Test {

    public static void main(String[] args) {
        /*Random random=new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(100));
        }*/
        FileMock mock=new FileMock(100, 10);
        Buffer buffer=new Buffer(20);
        Producer producer=new Producer(mock, buffer);
        Thread threadProducer=new Thread(producer,"Producer");
        Consumer consumers[]=new Consumer[3];
        Thread threadConsumers[]=new Thread[3];
        for (int i=0; i<3; i++){
            consumers[i]=new Consumer(buffer);
            threadConsumers[i]=new Thread(consumers[i],"Consumer "+i);
        }
        threadProducer.start();
        for (int i=0; i<3; i++){
            threadConsumers[i].start();
        }
    }
}
