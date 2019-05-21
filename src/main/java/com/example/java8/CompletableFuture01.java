package com.example.java8;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CompletableFuture01 {

    public static void main(String[] args) {
        Shop shop = new Shop("BESTSHOP");
        long start = System.nanoTime();
        Future<Double> future = shop.getPriceAsync("product");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("invocation return after " + invocationTime + " msecs");

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            double price = future.get();
            System.out.println("price is " + price);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long returnTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("price return after " + returnTime + " msecs");
    }
}

/**
 * Shop
 */
@Data
@AllArgsConstructor
class Shop {

    /**
     *
     */
    private String name;

    private double calculatePrice(String product) {
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);

    }

    private void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    public Future<Double> getPriceAsync(String product){
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(()->{
            try {   
                double price = calculatePrice(product);
                if(!new Random().nextBoolean())
                    throw new RuntimeException("abc");
                future.complete(price);
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        }).start();
        return future;
    }

    public Future<Double> getPriceAsync2(String product){
        return CompletableFuture.supplyAsync(()->calculatePrice(product));
    }


}