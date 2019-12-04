package com.cgy.mycollections.testsources;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Description :
 * Author :cgy
 * Date :2019/12/4
 */
public class ExecutorTest {

//    ExecutorService newFixedThreadPool() : 创建固定大小的线程池
//    ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
//    ExecutorService newSingleThreadExecutor() : 创建单个线程池。 线程池中只有一个线程
//
//    ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务

    public static void main(String[] args) {
        testExecutors();
    }

    private static void testExecutors() {
        //1. 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(5);

        List<Future<Integer>> list = new ArrayList<Future<Integer>>();

        for (int i = 0; i < 10; i++) {
            final int start = i;
            Future<Integer> future = pool.submit(new Callable<Integer>() {
                public Integer call() throws Exception {
                    int sum = start;
                    for (int i = 0; i <= 100; i++) {
                        sum += i;
                    }
                    System.out.println(start + "--future call:" + sum);
                    return sum;
                }
            });
            list.add(future);
        }

        pool.shutdown();
        try {
            for (Future<Integer> future : list) {

                System.out.println("future.get:" + future.get());

            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
