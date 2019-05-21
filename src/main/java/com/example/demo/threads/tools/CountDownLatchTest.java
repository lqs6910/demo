package com.example.demo.threads.tools;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {

    //1.   创建一个类名为 Videoconference 并特别实现 Runnable 接口。这个类将实现 video-conference 系统。
    public static class Videoconference implements Runnable {

        //2.   声明 CountDownLatch 对象名为 controller。
        private final CountDownLatch controller;

        //3.   实现类的构造函数，初始 CountDownLatch 属性。Videoconference 类接收将要等待的参与者的量为参数。
        public Videoconference(int number) {
            controller = new CountDownLatch(number);
        }

        //4.   实现 arrive() 方法。每次有参与者到达都会调用此方法。它接收String类型的参数名为 name。
        public void arrive(String name) {
            //5.   首先，它输出某某参数已经到达。
            System.out.printf("%s has arrived. ", name);
            //6.   然后，调用CountDownLatch对象的 countDown() 方法。
            controller.countDown();
            //7.	最后，使用CountDownLatch对象的 getCount() 方法输出另一条关于还未确定到达的参与者数。
            System.out.printf("VideoConference:Waiting for %d participants.\n", controller.getCount());
        }

        //8.   实现video-conference 系统的主方法。它是每个Runnable都必须有的 run() 方法。
        @Override
        public void run() {
            //9.   首先，使用 getCount() 方法来输出这次video conference的参与值的数量信息。
            System.out.printf("VideoConference: Initialization: %d participants.\n", controller.getCount());
            //10. 然后， 使用 await() 方法来等待全部的参与者。由于此法会抛出 InterruptedException 异常，所以要包含处理代码。
            try {
                controller.await();
                //11. 最后，输出信息表明全部参与者已经到达。
                System.out.printf("VideoConference: All the participants have come\n");
                System.out.printf("VideoConference: Let's start...\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //12. 创建 Participant 类并实现 Runnable 接口。这个类表示每个video conference的参与者。
    public static class Participant implements Runnable {
        //13. 声明一个私有 Videoconference 属性名为 conference.
        private Videoconference conference;
        //14. 声明一个私有 String 属性名为 name。
        private String name;

        //15. 实现类的构造函数，初始化那2个属性。
        public Participant(Videoconference conference, String name) {
            this.conference = conference;
            this.name = name;
        }

        //16. 实现参与者的run() 方法。
        @Override
        public void run() {
            //17.  首先，让线程随机休眠一段时间。
            long duration = (long) (Math.random() * 10);
            try {
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //18. 然后，使用Videoconference 对象的arrive() 方法来表明参与者的到达。
            conference.arrive(name);
        }
    }

    //19. 最后，实现例子的 main() 方法。
    public static void main(String[] args) {
        //20. 创建 Videoconference 对象名为 conference，将等待10个参与者。
        Videoconference conference = new Videoconference(10);
        //21. 创建 Thread 来运行这个 Videoconference 对象并开始运行。
        Thread threadConference = new Thread(conference);
        threadConference.start();
        //22. 创建 10个 Participant 对象，为每个对象各创建一个 Thread 对象来运行他们，开始运行全部的线程。
        for (int i = 0; i < 10 ;i++){
            Participant p = new Participant(conference, "Participant " + i);
            Thread t = new Thread(p);
            t.start();
        }
    }
}