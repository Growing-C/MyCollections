package com.example.testsourcelib;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * Created by RB-cgy on 2018/6/15.
 */
public class TestMain {

    public static void main(String[] args) {
//        testRound();
//        testDirectSetOrAdd();
//        getMaxNotRepeatStringLenInString("qwertyuiopqqasdfghjklzaazxcvbnmlkjzz");
//        testThreadParams();
        testPoolBytes();
    }

    /**
     * 测试byte缓存功能，在多线程的时候的表现
     */
    public static void testPoolBytes() {
        final Random random = new Random(10);
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    long sleepTime = random.nextInt(20);
                    PoolBytes poolBytes0 = PoolBytes.obtainBytes(10245);
                    try {
//                        System.out.println("000 sleep:" + sleepTime);
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    poolBytes0.recycle();
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    long sleepTime = random.nextInt(20);
                    PoolBytes poolBytes0 = PoolBytes.obtainBytes(1024);
                    try {
//                        System.out.println("111 sleep:" + sleepTime);
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    poolBytes0.recycle();
                }
            }
        }.start();

    }

    /**
     * 测试不同线程操作变量
     * 结论
     * 1.不同线程set和get如果set用synchronized 而get不用的话 get的值有可能会在set中间获取到导致值是前一个值，出现不正确
     * 2.volatile对这种情况不起作用，多线程操作情况下必须set ，get都使用synchronized
     * 3.set，get都使用synchronized的时候不会出现正在set的时候调用get
     * 4.使用了synchronized 不使用volatile也没关系，get到的变量值也会是正确的
     * 5.volatile最适合使用的地方是一个线程写、其它线程读的场合，如果有多个线程并发写操作，仍然需要使用锁或者线程安全的容器或者原子变量来代替。
     * <p>
     * 某一个线程进入synchronized代码块前后，执行过程入如下：
     * a.线程获得互斥锁
     * b.清空工作内存
     * c.从主内存拷贝共享变量最新的值到工作内存成为副本
     * d.执行代码
     * e.将修改后的副本的值刷新回主内存中
     * f.线程释放锁
     * <p>
     * 所以set,get都加了synchronized的时候不需要使用volatile
     * 而get没加的时候有可能获取到的值不正确 ,此时用volatile也没用，因为volatile不能保证变量更改的原子性。
     * 单例由于指令重排可能判断是否为空的时候返回不会空实际仍然为空，所以需要加volatile
     * <p>
     * volatile适用情况
     * a.对变量的写入操作不依赖当前值
     * 比如自增自减、number = number + 5等（不满足）
     * b.当前volatile变量不依赖于别的volatile变量
     * 比如 volatile_var > volatile_var2这个不等式（不满足）
     * <p>
     * synchronized和volatile比较
     * a. volatile不需要同步操作，所以效率更高，不会阻塞线程，但是适用情况比较窄
     * b. volatile读变量相当于加锁（即进入synchronized代码块），而写变量相当于解锁（退出synchronized代码块）
     * c. synchronized既能保证共享变量可见性，也可以保证锁内操作的原子性；volatile只能保证可见性
     */
    public static void testThreadParams() {
        final ThreadClass threadClass = new ThreadClass();
        new Thread() {
            @Override
            public void run() {
                super.run();

                for (int i = 0; i < 1000; i++) {
                    threadClass.setParam(i);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                System.out.println("thread 1 set thread param:");
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 1000; i++) {
                    threadClass.getParam();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    System.out.println("thread 2 get thread param:" + threadClass.getParam());
                }
            }
        }.start();

    }

    private static class ThreadClass {
        private volatile int param;

        public synchronized void setParam(int param) {
            System.out.println("set param start-----------------" + param);
            this.param = param;
            System.out.println("set param end---------------" + param);
        }

        public int getParam() {
            System.out.println("get param:" + param);
            return param;
        }
    }

    public static void testCompletableFuture() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

    }

    /**
     * 找到一串输入字符串中 最大不重复字符串的长度
     * <p>
     * 例  fffdsfdtlsdj  这串字符串中最大不重复串为 fdtls 即长度为 5
     *
     * @param input
     * @return
     */
    public static void getMaxNotRepeatStringLenInString(String input) {
        if (input == null || input.length() == 0) {
            System.out.println("null string len is 0");
            return;
        }

        HashSet<Character> charSet = new HashSet<>();
        HashMap<Integer, String> maxStrings = new HashMap<>();
        int rightIndex = 0;
        int maxLen = 0;
        for (int i = 0, len = input.length(); i < len; i++) {
            while (rightIndex < len && !charSet.contains(input.charAt(rightIndex))) {
                charSet.add(input.charAt(rightIndex));
                rightIndex++;
            }
            maxLen = Math.max(maxLen, charSet.size());
            if (charSet.size() == maxLen) {
                String pre = maxStrings.get(maxLen);
                String maxString = input.substring(i, rightIndex);
                maxStrings.put(maxLen, pre == null ? maxString : pre + "," + maxString);
            }
            if (rightIndex == len) {
                //已经到最后一个了，不可能再增加了
                System.out.println(input + "--maxLen->" + maxLen);
                System.out.println("--all strings->" + maxStrings.get(maxLen));
                break;
            }

            charSet.remove(input.charAt(i));
        }
    }

    /**
     * 测试直接赋值和通过加减赋值性能差距
     * 10000000 级别都是2ms 几乎可以忽略不记
     */
    private static void testDirectSetOrAdd() {
        int a = 1;
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            int b = i;
        }
        System.out.println("direct cost time :" + (System.currentTimeMillis() - currentTime));
        currentTime = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            int b = i + 1;
        }
        System.out.println("add cost time :" + (System.currentTimeMillis() - currentTime));
    }

    /**
     * 测试四舍五入
     */
    public static void testRound() {
        System.out.println("向上取整:" + (int) Math.ceil(96.1));// 97 (去掉小数凑整:不管小数是多少，都进一)
        System.out.println("向下取整" + (int) Math.floor(96.8));// 96 (去掉小数凑整:不论小数是多少，都不进位)
        System.out.println("四舍五入取整:" + Math.round(96.1));// 96 (这个好理解，不解释)
        System.out.println("四舍五入取整:" + Math.round(96.8));// 97
    }


}
