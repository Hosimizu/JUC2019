package com.lzy.juc;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {

    public static void main(String[] args) throws Exception {
        //同步，异步，异步回调

        //同步
//        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(()->{
//            System.out.println(Thread.currentThread().getName()+"\t completableFuture1");
//        });
//        completableFuture1.get();

        //异步回调
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"\t completableFuture2");
            int i = 10/0;
            return 1024;
        });

        System.out.println(completableFuture2.whenComplete((t, u) -> {
            System.out.println("-------t=" + t);
            System.out.println("-------u=" + u);
            //自动return供给者函数的返回值
        }).exceptionally(f -> {
            System.out.println("-----exception:" + f.getMessage());
            return 444;
        }).get());

    }
}