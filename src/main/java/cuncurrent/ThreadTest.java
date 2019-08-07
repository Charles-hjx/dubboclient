package cuncurrent;

import java.util.Timer;

/**
 * @Author: hjx
 * @Date: 2019/7/31
 * @Version 1.0
 */
public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t = tt();
        t.start();
        Thread.sleep(1000);
        t.interrupt();
    }

    private static Thread tt() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                Thread thread = Thread.currentThread();
                while (true){
                    System.out.println("aaa");
                    if(thread.isInterrupted()){
                        System.out.println("thread.isInterrupted()");
                        break;
                    }

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        //重置中断标志（抛出异常会消除中断标志，thread.isInterrupted()就不可能为true，
                        // 这段代码大部分时间都处于 sleep状态，接收到中断后 会抛出异常）
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }

                }

                System.out.println("======break");
            }
        });
        return t;
    }


}
