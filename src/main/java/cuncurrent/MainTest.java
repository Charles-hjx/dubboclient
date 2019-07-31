package cuncurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: hjx
 * @Date: 2019/7/31
 * @Version 1.0
 */
public class MainTest {

    public static void main(String[] args) throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(1000);

        final Account account = new Account(1000);
        final Account target = new Account(1000);

        for(int  i = 0 ; i < 1000; i++){
            new Thread(new Runnable() {
                public void run() {
                    account.transfer3(target,1);
                    latch.countDown();
                    System.out.println("lathCount:"+latch.getCount());
                }
            }).start();
        }
        latch.await();

        System.out.println("当前账户："+account.getBalance());
        System.out.println("目标账户:"+target.getBalance());

    }

}
