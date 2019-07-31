package cuncurrent;

/**
 * @Author: hjx
 * @Date: 2019/7/31
 * @Version 1.0
 */
public class Account {

    private int balance;

    //allocator 为单例
//    private Allocator allocator;

    public Account(int balance){
        this.balance = balance;
    }
    //调用了 使用 wait() 的 的方式
    void transfer3(Account target, int atm){
        Allocator.getInstance().apply1(this,target);
        //只要获取了 两个资源 才执行下面的操作
        try {
            synchronized (this){
                synchronized (target){
                    if(this.balance > atm) {
                        this.balance -= atm;
                        target.setBalance(target.getBalance()+atm);
                    }
                }
            }
        } finally {
            Allocator.getInstance().free1(this,target);
        }


    }


    //转账
    void transfer1(Account target , int amt){
        Long time1 = System.currentTimeMillis();
        while (true){//申请所有资源 破坏占有 且等待
            Long time2 = System.currentTimeMillis();
            if(Allocator.getInstance().apply(this,target)){
                //锁定当前账号
                try {
                    synchronized (this) {
                        //锁定目标账号
                        synchronized (target) {
                            if (this.balance > amt) {
                                this.balance -= amt;
                                target.balance += amt;
                            } else {
                                throw new RuntimeException("余额不足");
                            }
                        }
                    }
                } finally {
                    Allocator.getInstance().free(this,target);
                    break;
                }
            }else {
                //大于8秒 退出
                if(time2-time1 > 8000){
                    break;
                }
            }


        }
    }

    //破坏相互循环等待的条件 ，需要对资源进行排序，然后按序申请资源。这个实现非常简单，我们假设每个账户都有不同
    //的属性 id，这个 id 可以作为排序字段，申请的时候，我们可以按照从小到大的顺序来申请。比如下面代码
    //中，①~⑥处的代码对转出账户（this）和转入账户（target）排序，然后按照序号从小到大的顺序锁定账
    //户。这样就不存在“循环”等待了。

    private int id;

    void transfer2(Account target , int amt){
        Account left = this;
        Account right = target;

        if(left.id > right.id){
            left = target;
            right = this;
        }

        //锁定序号小的账户
        synchronized (left){
            //锁定序号小的账户
            synchronized (right){
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                } else {
                    throw new RuntimeException("余额不足");
                }
            }
        }

    }

    //原始
    void transfer0(Account target , int amt){

                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                } else {
                    throw new RuntimeException("余额不足");
                }

    }


    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
