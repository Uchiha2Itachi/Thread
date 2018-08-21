package com.leimingtech.admin.threadpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ThreadForkJoinTest extends RecursiveTask<Long> {
    //设立一个最大计算容量
    private final int DEFAULT_CAPACITY = 1000;

    //用2个数字表示目前要计算的范围
    private int start;

    private int end;

    public ThreadForkJoinTest(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
//        StringBuilder sb =new StringBuilder();
        //分为两种情况进行出来 如果任务量在最大容量之内
        if(end - start < DEFAULT_CAPACITY){
            for (int num = start; start < end; num++) {
//                 sb.append((long)num * num+"\n");
                System.out.println((long)num * num+"="+num+"*"+num);
                return 0L;
            }
        }else{//如果超过了最大，那么就进行拆分处理
            //计算容量中间值
            int middle = (start + end)/2;
            //进行递归
            ThreadForkJoinTest fockJoinTest1 = new ThreadForkJoinTest(start, middle);
            ThreadForkJoinTest fockJoinTest2 = new ThreadForkJoinTest(middle + 1, end);
            //执行任务
            fockJoinTest1.fork();
            fockJoinTest2.fork();
            //等待任务执行并返回结果
            fockJoinTest1.join() ;
            fockJoinTest2.join();
            return 0L;
        }
        return 0L;
    }
    public static void main(String[] args) {
        System.out.println("开始");
        ForkJoinPool forkJoinPool = new ForkJoinPool(16);
        ThreadForkJoinTest fockJoinTest = new ThreadForkJoinTest(1, 100000000);
        long fockhoinStartTime = System.currentTimeMillis();
        //任务提交中invoke可以直接返回结果
        //long result =forkJoinPool.invoke(fockJoinTest);
        Future result = forkJoinPool.submit(fockJoinTest);

        //forkJoinPool.invoke(fockJoinTest);
        try {
            System.out.println("result is:" + result.get());
        }catch (Exception e){

        }

        System.out.println("计算结果耗时"+(System.currentTimeMillis() - fockhoinStartTime));
        //关闭线程池
        forkJoinPool.shutdown();
    }
}
