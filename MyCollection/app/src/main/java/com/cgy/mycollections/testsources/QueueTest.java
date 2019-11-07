package com.cgy.mycollections.testsources;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Description :
 * Author :cgy
 * Date :2019/10/30
 */
public class QueueTest {
    public static void main(String[] args) {
        testBlockingQueue();
    }

    private static void testBlockingQueue() {
        //数组和链表的区别？
        //不同：链表是链式的存储结构；数组是顺序的存储结构。
        //链表通过指针来连接元素与元素，数组则是把所有元素按次序依次存储。
        //链表的插入删除元素相对数组较为简单，不需要移动元素，且较为容易实现长度扩充，但是寻找某个元素较为困难；
        //数组寻找某个元素较为简单，但插入与删除比较复杂，由于最大长度需要再编程一开始时指定，故当达到最大长度时，扩充长度不如链表方便。
        //相同：两种结构均可实现数据的顺序存储，构造出来的模型呈线性结构。

        //LinkedBlockingQueue 和 ArrayBlockingQueue 异同
        //同：两个queue都是fifo模式的,顺序队列
        //异：LinkedBlockingQueue是链表，ArrayBlockingQueue是数组结构
        //LinkedBlockingQueue默认容量无限，ArrayBlockingQueue需要指定容量
        //ArrayBlockingQueue 可以使用fair来指定不同线程对其使用的时候的顺序

        LinkedBlockingQueue<Integer> mCommandQueue = new LinkedBlockingQueue<>();//默认容量无限
        ArrayBlockingQueue<Integer> arrayQ = new ArrayBlockingQueue<>(20);

        Runnable putRun = new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("put:" + i);
                        arrayQ.put(i);
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable takeRun = new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("take:" + arrayQ.take());
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(putRun).start();
        new Thread(takeRun).start();
    }
}
