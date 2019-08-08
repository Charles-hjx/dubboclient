package cuncurrent;

import java.util.concurrent.*;

/**
 * @Author: hjx
 * @Date: 2019/8/7
 * @Version 1.0
 */
public class CompletableTest {
    public static void main(String[] args) {
        // 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);
        // 异步向电商S1询价
        Future<Integer> f1 =
                executor.submit(()->getPriceByS1());
        // 异步向电商S2询价
        Future<Integer> f2 =
                executor.submit(()->getPriceByS2());
        // 异步向电商S3询价
        Future<Integer> f3 =
                executor.submit(()->getPriceByS3());

        // 获取电商S1报价并异步保存
            executor.execute(()-> {
                try {
                    save(f1.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
            // 获取电商S2报价并异步保存
            executor.execute(()-> {
                try {
                    save(f2.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
            // 获取电商S3报价并异步保存
            executor.execute(()-> {
                try {
                    save(f3.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });


    }

    private static Integer getPriceByS1(){
        return 1;
    }

    private static Integer getPriceByS2(){
        return 2;
    }

    private static Integer getPriceByS3(){
        return 3;
    }

    private static void save(Integer price){

    }


    public static void com(){
//        CompletableFuture

    }

}
