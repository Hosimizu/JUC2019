package com.lzy.juc;

import java.util.concurrent.*;

/**
 * 继承Thread类、实现runnable接口
 * java多线程中，第三种获得多线程的方式
 */
class MyThread implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        try { TimeUnit.SECONDS.sleep(2); }catch (Exception e) {e.printStackTrace();}
        System.out.println("******come in here");
        return 1024;
    }
}
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
/*        MyThread myThread = new MyThread();
        Thread t1 =new Thread();
        t1.start();*/

        //Thread(Runnable target, String name)
        //多态 可以穿Runnable子接口
        //FutureTask(Callable<V> callable) < RunnableFuture<V> < Runnable
        FutureTask futureTask=new FutureTask(new MyThread());
        //实现了Runnable接口就完事了 多态性
        new Thread(futureTask,"A").start();
        new Thread(futureTask,"B").start();
        System.out.println(Thread.currentThread().getName()+"******开始计算");
        System.out.println(futureTask.get());
        System.out.println(Thread.currentThread().getName()+"******计算完成");
    }
}
/**
 *
 *
 在主线程中需要执行比较耗时的操作时，但又不想阻塞主线程时，可以把这些作业交给Future对象在后台完成，
 当主线程将来需要时，就可以通过Future对象获得后台作业的计算结果或者执行状态。

 一般FutureTask多用于耗时的计算，主线程可以在完成自己的任务后，再去获取结果。

 仅在计算完成时才能检索结果；如果计算尚未完成，则阻塞 get 方法。一旦计算完成，
 就不能再重新开始或取消计算。get方法而获取结果只有在计算完成时获取，否则会一直阻塞直到任务转入完成状态，
 然后会返回结果或者抛出异常。

 只计算一次
 get方法放到最后
 */
