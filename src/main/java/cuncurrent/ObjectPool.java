package cuncurrent;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 *
 * 对象池
 *
 * @Author: hjx
 * @Date: 2019/7/31
 * @Version 1.0
 */
public class ObjectPool<T,R> {
    final List<T> pool;

    final Semaphore sem;

    public ObjectPool(int size, T t) {
        this.pool = new Vector<T>(){};
        for (int i = 0; i < size; i++) {
            pool.add(t);
        }
        sem = new Semaphore(size);
    }

    // 利⽤对象池的对象，调⽤func

    R exec(Function<T,R> func){
        T t = null;
        try {
            sem.acquire(); //相当于加1
            t = pool.remove(0);
            return func.apply(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            pool.add(t);
            sem.release(); //相当于 -1
        }
        return null;
    }

    public static void main(String[] args) {
        ObjectPool<Account,String> pool = new ObjectPool<Account,String>(10,new Account(1000));

        //t 就是获取到对象
        pool.exec(t ->{
            System.out.println(t);
            return t.toString();
        });

    }

}

















