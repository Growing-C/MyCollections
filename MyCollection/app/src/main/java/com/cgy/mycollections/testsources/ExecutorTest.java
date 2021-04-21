package com.cgy.mycollections.testsources;

import android.os.Looper;
import android.os.MessageQueue;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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


    /**
     * 测试单线程线程池
     * 1.future.get是阻塞的
     */
    public static void testSingleExecutor() {
        new Thread() {
            @Override
            public void run() {
                Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                    @Override
                    public boolean queueIdle() {

                        return false;
                    }
                });
                ScheduledExecutorService mScheduledThreadPool = Executors.newSingleThreadScheduledExecutor(new XpThreadFactory("nav_io", Thread.NORM_PRIORITY));
//        new ScheduledThreadPoolExecutor(1,
//                Integer.MAX_VALUE, 60L,
//                TimeUnit.SECONDS, new SynchronousQueue<>(),
//                new XpThreadFactory("nav_cache", Thread.NORM_PRIORITY));
                new ThreadPoolExecutor(2,
                        Integer.MAX_VALUE, 60L,
                        TimeUnit.SECONDS, new SynchronousQueue<>(),
                        new XpThreadFactory("nav_cache", Thread.NORM_PRIORITY));
                long startTime = System.currentTimeMillis();
                for (int i = 0; i < 2; i++) {
                    final int index = i;
                    try {
                        log("future begin:");
                        ScheduledFuture future = mScheduledThreadPool.schedule(new TaskWrapper(new Runnable() {
                            @Override
                            public void run() {
                                log((System.currentTimeMillis() - startTime) + "--schedule task start:" + index);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                log((System.currentTimeMillis() - startTime) + "--schedule task end:" + index);
                            }
                        }), 900, TimeUnit.MILLISECONDS);

                        log("future get start:");
                        log("future get:" + future.get(2000, TimeUnit.MILLISECONDS));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                    }
                }

//        mScheduledThreadPool.schedule(new Runnable() {
//            @Override
//            public void run() {
//                log((System.currentTimeMillis() - startTime) + "--schedule task 1 start");
//                try {
//                    for (int i = 0; i < 10; i++) {
//                        Thread.sleep(1000);
//                        log((System.currentTimeMillis() - startTime) + "--schedule task 1 middle:" + i);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                int a = 1 / 0;
//                log((System.currentTimeMillis() - startTime) + "--schedule task 1 end");
//            }
//        }, 2, TimeUnit.SECONDS);

//        mScheduledThreadPool.schedule(new Runnable() {
//            @Override
//            public void run() {
//                log((System.currentTimeMillis() - startTime) + "--schedule task out 2 ");
//            }
//        }, 3, TimeUnit.SECONDS);
//
//        mScheduledThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                log((System.currentTimeMillis() - startTime) + "--execute task 3 ");
//            }
//        });
            }
        }.start();
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

    static class XpThreadFactory implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final AtomicInteger mThreadNumber = new AtomicInteger(1);
        private final String mNamePrefix;
        private final int mPriority;

        public XpThreadFactory(String name, int priority) {
            if (null == name || name.isEmpty()) {
                name = "pool";
            }
            mNamePrefix = name + "-" + POOL_NUMBER.getAndIncrement() + "-thread-";
            mPriority = priority;
        }

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            log("newThread");
            Thread thread = new Thread(runnable, mNamePrefix + mThreadNumber.getAndIncrement());
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != mPriority) {
                thread.setPriority(mPriority);
            }
            return thread;
        }
    }

    private static class TaskWrapper implements Runnable {
        Runnable mTask;

        public TaskWrapper(@NonNull Runnable task) {
            mTask = task;
        }

        @Override
        public void run() {
            log("task start");
            mTask.run();
//            printDebugStackTrace(new Error());
            log("task end");
        }
    }

    private static void log(String s) {
        System.out.println("-----ThreadPoolLog : " + s);
    }

    public static final String printDebugStackTrace(Error e) {
        if (null == e) {
            return null;
        }
        long start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString()).append("\n");
        log(e.toString());
        StackTraceElement[] elements = e.getStackTrace();
        if (elements != null) {
            for (StackTraceElement ele : elements) {
                sb.append(ele.toString());
                log(ele.toString());
            }
        }
        for (Throwable throwable : e.getSuppressed()) {
            sb.append(printRootStackTrace(throwable));
        }
        Throwable throwable = e.getCause();
        if (null != throwable) {
            sb.append(printRootStackTrace(throwable));
        }
        long end = System.currentTimeMillis();
        log("printDebugStackTrace cost time:" + (end - start));
        return sb.toString();
    }

    public static String printRootStackTrace(Throwable t) {
        long start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append(t.toString()).append("\n");
        log(t.toString());
        for (StackTraceElement ste : t.getStackTrace()) {
            sb.append(ste.toString()).append("\n");
            log(ste.toString());
        }
        Throwable cause = t.getCause();
        if (cause != null) {
            sb.append("Caused by: ").append("\n");
            log("Caused by: ");
            sb.append(printRootStackTrace(cause));
        }
        long end = System.currentTimeMillis();
        log("printRootStackTrace cost time:" + (end - start));
        return sb.toString();
    }
}
