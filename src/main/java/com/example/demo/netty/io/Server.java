package com.example.demo.netty.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {

    private static int port = 8080;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(port)) {
            Socket socket =  null;
            ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 100, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10000));
            while (true) {
                socket = server.accept();
                //new Thread(new ServerHandler(socket)).start();
                executor.submit(new ServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class ServerHandler implements Runnable {

    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true)
        ){
            String body = null;
            while (true) {
                body = in.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("server receive message is ："+ body);
                out.println("time".equalsIgnoreCase(body)?System.currentTimeMillis()+"":"bad");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
