package com.example.demo.netty.lesson01;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;

public class Client {

    public static void main(String[] args) {
        /*ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 10, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10000));
        executor.submit(new ClientRunnable("127.0.0.1", 8080));*/
        //for (int i = 0; i < 100; i++)
        new Thread(new ClientRunnable("127.0.0.1", 8080)).start();
    }
}

class ClientRunnable implements Runnable {

    private String host;
    private int port;

    public ClientRunnable(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        NioEventLoopGroup work = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(work)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                //.handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            work.shutdownGracefully();
        }
    }
}

class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int i = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int j = 0; j < 100; j++) {
            ByteBuf req = Unpooled.copiedBuffer("currentTime".getBytes());
            ctx.writeAndFlush(req);
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] resp = new byte[msg.readableBytes()];
        msg.readBytes(resp);
        System.out.println("获得响应：" + i++ + new String(resp, StandardCharsets.UTF_8));
        /*ByteBuf req = Unpooled.copiedBuffer("currentTime".getBytes());
        ctx.writeAndFlush(req);*/
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //super.channelReadComplete(ctx);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
        ctx.close();
    }
}
