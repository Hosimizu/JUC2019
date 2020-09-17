package com.lzy.juc;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//资源类
class Ticket {
    private int number = 80;
    private final Lock lock = new ReentrantLock();

    //Lock 实现提供了比使用 synchronized 方法和语句可获得的更广泛的锁定操作。
    //此实现允许更灵活的结构，可以具有差别很大的属性，可以支持多个相关的 Condition 对象。
    //缩小锁的范围，哪几行需要就哪里加
    //public synchronized void saleTicket() {
    public void saleTicket() {
        //ctrl+win+alt+t 直接try catch finally
        //也可以自己写trylock
        lock.lock();  // block until condition holds
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "\t卖出第：" + (number--) + "还剩下:" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/**
题目：三个售票员 卖出 30张票
 * 多线程编程的企业级套路+末班
 *1、在高内聚低耦合的前提下，线程（Thread）   操作（对外暴露的调用方法）  资源类（Ticket类）
 * 零耦合  人跟空调分开的（资源）
 * 高内聚  空调自身有温度调节功能
 *
 * */
public class SaleTicket {
    public static void main(String[] args) throws Exception {
        //先new一个资源类
        final Ticket ticket = new Ticket();
/*        Thread(Runnable target,String name), 分配新的 Thread 对象。
        传统的 使用匿名内部类的方法
        NEW RUNNABLE BLOCKED 新建 就绪 阻塞
        WAITING 一直等
        TIMED_WAITTING*/

        //lambdaExpress简化匿名内部类的
        new Thread(() -> {for (int i = 0; i < 100; i++) ticket.saleTicket();},"A").start();
        new Thread(() -> {for (int i = 0; i < 100; i++) ticket.saleTicket();},"B").start();
        new Thread(() -> {for (int i = 0; i < 100; i++) ticket.saleTicket();},"C").start();

/*        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    ticket.saleTicket();
                }
            }
        }, "A").start();
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 100; i++) {
                    ticket.saleTicket();
                }
            }
        }, "BB").start();*/


    }

}
