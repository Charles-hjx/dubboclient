package cuncurrent;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 单例 的 Allocator
 * 账户管理员：保证一次性申请需要的 所有资源
 * 破坏占有且等待（占有一个资源 且不释放，等待另外一个资源）
 *
 * @Author: hjx
 * @Date: 2019/7/31
 * @Version 1.0
 */
public class Allocator {
    //使用一个list 来存储已经被申请了 的 资源
    private final List<Object> als = new LinkedList<Object>();

    private Allocator(){}


    //获取单例 Allocator
    public static Allocator getInstance(){
        return AllocatorSingle.install;
    }


    //改进 1
    synchronized void apply1(Object from,Object to){
        //使用while 经典写法
        while(als.contains(from) || als.contains(to)){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        als.add(from);
        als.add(to);

    }

    /**
     * 释放资源
     * @param from
     * @param to
     */
    synchronized void free1(Object from,Object to){
        als.remove(from);
        als.remove(to);
        notifyAll(); //一般使用 notifyAll()。notify()是会随机地通知等待队列中的一个线程，而notifyAll()会通知等待队列中的所有线程。
    }





    /**
     * 申请资源
     * @param from
     * @param to
     * @return
     */
    synchronized boolean apply(Object from,Object to){
        if(als.contains(from) || als.contains(to)){
            return false;
        }else {
            als.add(from);
            als.add(to);
            return true;
        }
    }

    /**
     * 释放资源
     * @param from
     * @param to
     */
    synchronized void free(Object from,Object to){
        als.remove(from);
        als.remove(to);
    }


    private static class AllocatorSingle{
        private static Allocator install = new Allocator();
    }

}
