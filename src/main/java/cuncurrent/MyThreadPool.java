package cuncurrent;

import com.sun.xml.internal.txw2.output.IndentingXMLFilter;
import queue.BlockQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: hjx
 * @Date: 2019/8/7
 * @Version 1.0
 */
public class MyThreadPool {
    //利用阻塞队列实现生产者 消费者模式
    BlockingQueue<Runnable> workQueue ;

    //保存内部工作线程
    List<WorkerThread> threads = new ArrayList<>();

    //构造方法
    public MyThreadPool(int poolSize, BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        //创建工作线程
        for(int idx = 0; idx < poolSize; idx++){
            WorkerThread work = new WorkerThread();
            work.start();
            threads.add(work);
        }
    }

    //提交任务
    void execute(Runnable command){
        try {
            workQueue.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(2);
        //创建线程池
        MyThreadPool pool = new MyThreadPool(10,workQueue);
        pool.execute(() ->{
            System.out.println("执行线程池");
        });
    }


    class WorkerThread extends Thread{
        @Override
        public void run() {
            while(true){
                try {
                    Runnable task = workQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
