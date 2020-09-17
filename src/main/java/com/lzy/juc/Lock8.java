package com.lzy.juc;

import java.util.concurrent.TimeUnit;

class Phone{
    synchronized void sendEmail() throws Exception{
        try { TimeUnit.SECONDS.sleep(4); }catch (Exception e) {e.printStackTrace();}
        System.out.println("--------sendEmail");
    }
    synchronized void sendSMS() throws Exception{
        System.out.println("--------sendSMS");
    }

    void hello(){
        System.out.println("--------hello");
    }
}
/**
* 题目：多线程8锁
* 1 标准访问，请问先打印邮件还是短信    需要争抢时间片
* 2 邮件方法暂停4  秒钟，请问先打印邮件还是短信？ 邮件 sleep不会释放锁 但会释放cpu时间片
* 3 新增一个普通方法hello()，请问先打印邮件还是hello?  hello
* 4 两部手机，请问先打印邮件还是短信？ 短信 对象锁不是同一个锁
*
* 5 两个静态同步方法，同一部手机，请问先打印邮件还是短信？邮件 锁的是class对象 Phone.class
* 6 两个静态同步方法，两部手机，请问先打印邮件还是短信？邮件 锁的是class对象
*
* 7 一个普通同步方法，一个静态同步方法，一部手机，请问先打印邮件还是短信？短信 不是同一个锁！
* 8 一个普通同步方法(SMS)，一个静态同步方法(Email)，两部手机，请问先打印邮件还是短信？短信
* 
* 笔记
* 一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中一个synchronized方法了，
* 其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法
* 锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它synchronized方法
*
* 加个普通方法后发现和同步锁无关
* 换成两个对象后，不是同一把锁了，情况即刻变化了。
*
* 都换成静态同步方法后，情况又变化了
* new this，具体的一部部手机
* 静态class，唯一的一个模板
*
* 所有的非静态方法用的都是同一把锁——实例对象本身，
*
* synchronized实现同步的基础：Java中的每一个对象都可以作为锁。
* 具体表现为以下3种形式。
* 对于普通同步方法，锁是当前实例对象。
* 对于静态同步方法，锁是当前类的Class对象。
* 对于同步方法快，锁是synchronized括号里面配置的对象。
*
* 当一个线程试图访问同步代码块，它首先必须得到锁，退出或跑出异常时必须释放锁。
*
* 也就是说如果一个实例对象的非静态同步方法获取锁后，该实例对象的其他非静态同步方法必须等待获取锁的方法释放锁后才能获取锁，
* 可是别的实例对象的非静态同步方法因为跟该实例对象的非静态同步方法用的是不同的锁，
* 所以毋须等待该实例对象已获取锁的非静态同步方法释放锁就可以获取他们自己的锁。
*
* 所有的静态同步方法用的也是同一把锁——类对象本身，
* 这两把锁（this/class）是两个不同的对象，所以静态同步方法与非静态同步方法之间是不会有竞态条件的。
* 但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后能获取锁，
* 而不管是同一个实例对象的静态同步方法之间，
* 还是不同的实例对象的静态同步方法之间。只要它们同一个类的实例对象!
* */
public class Lock8 {
    public static void main(String[] args) throws InterruptedException {
        Phone phone=new Phone();
        Phone phone2=new Phone();
        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"A").start();

        Thread.sleep(200);

        new Thread(() -> {
            try {
                phone.hello();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"B").start();
    }
}
