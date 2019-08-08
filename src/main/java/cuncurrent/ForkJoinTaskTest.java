package cuncurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @Author: hjx
 * @Date: 2019/8/7
 * @Version 1.0
 */
public class ForkJoinTaskTest{

    public static void main(String[] args) {
        //创建分治任务线程池(4个处理线程)
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        //创建分治任务
        Fibonacci fib = new Fibonacci(30);

        Long  start = System.currentTimeMillis();
        //启动分治任务 等待获得结果
        Integer  result = forkJoinPool.invoke(fib);
        System.out.println(result);
        Long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    //递归任务
    static class Fibonacci extends RecursiveTask<Integer>{

        final int n;

        Fibonacci(int n) { this.n = n;}

        @Override
        protected Integer compute() {
            if(n <= 1) return n;
            Fibonacci f1= new Fibonacci(n-1);
            //创建子任务
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            return f2.compute() + f1.join();
        }
    }
}
