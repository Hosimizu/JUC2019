package com.lzy.juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 题目:请举例说明集合类是不安全的
 * 1 故障现象 java.util.ConcurrentModificationException
 * 2 导致原因
 * 3 解决方案
 * 3.1 Vector
 * 3.2 Collections.synchronizedList(new ArrayList<>());
 * 3.3 new CopyOnWriteArrayList<>(); 读写分离提高性能
 * <p>
 * 4 优化建议（同样的错误，不出现第2次）
 */
public class NotSafeDemo {
    public static void main(String[] args) {
        //底层 node数组+node链表+红黑树  Node存的是K,V
        //可以设置容量和负载因子 构造方法里的两个参数
        //HashMap （16）到（16*0.75）会扩容 扩容到原来的一倍 HashSet扩容原来的一半
        //一般会将容量的默认值
        Map<String, String> map = new ConcurrentHashMap<>(); //Collections.synchronizedMap(new HashMap<>());//ew HashMap<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    private static void setNotSafe() {
        //HashSet底层是HashMap add调用map.put()
        Set<String> set = new CopyOnWriteArraySet<>();//Collections.synchronizedSet(new HashSet<>());//new HashSet<>();

        HashSet<Object> objects = new HashSet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    private static void listNotSafe() {
        //ArrayList线程不安全 线程安全但是访问性能下降
        List<String> list = new CopyOnWriteArrayList<>();//Collections.synchronizedList(new ArrayList<>());//new Vector<>();//new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
/**
 * 写时复制
 * CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器Object[]添加，
 * 而是先将当前容器Object[]进行Copy，复制出一个新的容器Object[] newElements，然后向新的容器Object[] newElements里添加元素。
 * 添加元素后，再将原容器的引用指向新的容器setArray(newElements)。
 * 这样做的好处是可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。
 * 所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * public boolean add(E e) {
 * final ReentrantLock lock = this.lock;
 * lock.lock();
 * try {
 * Object[] elements = getArray();
 * int len = elements.length;
 * Object[] newElements = Arrays.copyOf(elements, len + 1);
 * newElements[len] = e;
 * setArray(newElements);
 * return true;
 * } finally {
 * lock.unlock();
 * }
 * }
 */
