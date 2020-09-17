package com.lzy.juc;
//只要里面只有一个方法，他底层都会自动加这个注解
@FunctionalInterface
interface Foo {
//    void sayHello();
    int add(int x,int y);
    //java8以后可以在接口里面实现方法
    default double div(double x,double y){
        System.out.println("******* hello java");
        return x/y;
    }
    static int mv(int x,int y){
        return x*y;
    }
}

/**
 * 2   Lambda Express
 *   2.1 口诀：
 *       拷贝小括号，写死右箭头，落地大括号
 *   2.2 @FunctionalInterface
 *   2.3 default
 *   2.4 静态方法实现
 * */
public class LambdaExpressDemo {
    public static void main(String[] args) {
//        Foo foo = new Foo() {//匿名内部类
//            @Override
//            public void sayHello() {
//                System.out.println("******hello java");
//            }
//        };
        //当前接口里面有且仅有一个方法，这个接口就叫做函数式接口
        Foo foo = (int x,int y) -> {
//            System.out.println("******hello java lambda Express");
            System.out.println("come in here");
            return x+y;
        };
        System.out.println(foo.div(1, 2));
        System.out.println(Foo.mv(3, 5));
//        foo.sayHello();
    }
}
