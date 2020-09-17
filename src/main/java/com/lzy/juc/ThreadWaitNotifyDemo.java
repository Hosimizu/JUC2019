package com.lzy.juc;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//资源列

class AirConditioner{
    private int number=0;
    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();
        try {
            //1 判断
            while(number!=0){
                condition.await();
            }
            //干活
            number++;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            //通知
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            //1 判断
            while(number==0){
                condition.await();
            }
            //干活
            number--;
            System.out.println(Thread.currentThread().getName()+"\t"+number);
            //通知
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
/*    对于某一个参数的版本，实现中断和虚假唤醒是可能的，而且此方法应始终在循环中使用：

    synchronized (obj) {
        while (<condition does not hold>)
        obj.wait();
... // Perform action appropriate to condition
    }*/

 /*   public synchronized void increment() throws InterruptedException {
        //1 判断
        while(number!=0){
            this.wait();
        }
        //干活
        number++;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        //通知
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        //1 判断
        while (number == 0) {
            this.wait();
        }
        //干活
        number--;
        System.out.println(Thread.currentThread().getName() + "\t" + number);
        //通知
        this.notifyAll();
    }*/



}
/**
题目：现在两个线程，可以操作初始值为零的一个变量，
实现一个线程对该变量加一，一个线程对该变量减一，
实现交替，来10轮，变量初始值为0
1   高内聚低耦合的前提下，线程操作资源类
2   判断、干活、通知
3   多线程交互中，必须要防止多线程的虚假唤醒，也即（判断只用while，不能用if ） 变量中途被修改了要重新做一次判断
 */
public class ThreadWaitNotifyDemo {
    public static void main(String[] args){
        AirConditioner airConditioner = new AirConditioner();
        new Thread(()->{
            //fori  +  tab
            for (int i = 0; i <=10 ; i++) {
                try {
                    airConditioner.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();
        new Thread(()->{
            //fori  +  tab
            for (int i = 0; i <=10 ; i++) {
                try {
                    airConditioner.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();
        new Thread(()->{
            //fori  +  tab
            for (int i = 0; i <=10 ; i++) {
                try {
                    airConditioner.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();
        new Thread(()->{
            //fori  +  tab
            for (int i = 0; i <=10 ; i++) {
                try {
                    airConditioner.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}
