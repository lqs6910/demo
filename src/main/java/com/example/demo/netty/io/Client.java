package com.example.demo.netty.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) {
        int port = 8080;
        for (int i = 0; i < 10000; i++) {
            try(
                    Socket socket = new Socket("127.0.0.1",port);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(),true)
            ){
                System.out.println("send request to server");
                out.println("time");
                System.out.println("server response is : " + in.readLine());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
