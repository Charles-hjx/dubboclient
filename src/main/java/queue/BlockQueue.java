package queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 阻塞队列
 * 类似于一个 管程模型。 所谓的管程：就是封装共享变量 以及对共享变量的 操作
 *
 * @Author: hjx
 * @Date: 2019/7/31
 * @Version 1.0
 */
public class BlockQueue<T> {

    public static final int  capacity = 10;

    Queue<T> queue = new LinkedList<T>();

    final Lock lock = new ReentrantLock();

    //条件变量 队列不满
    Condition notFull = lock.newCondition();

    //队列不为空
    Condition notEmpty = lock.newCondition();


    public void enQuene(T t){
        lock.lock();
        lock.tryLock();
        //队列已满
        try {
          while (queue.size() == 10) {
                  //阻塞 并等待队列不满
                  notFull.await();
          }
            queue.add(t);
            //入队后 通知 可以出队了
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void deQueue(){
        lock.lock();

        try {
            while (queue.size() < 1) {
                notEmpty.await();
            }
            //当满足 不为空时 可出队
            queue.poll();
            notFull.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

}








