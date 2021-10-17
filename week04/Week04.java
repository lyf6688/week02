package org.example.week04.study.java0.study;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 * Unit test for simple App.
 */
public class Week04 {

    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    static String value = null;

    @Test
    public void shouldAnswerWithTrue() {
        System.out.println(test12());
    }

    //用CountDownLatch
    private static String test1() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        executorService.execute(() -> {
            value = "test1";
            countDownLatch.countDown();
        });
        try {

            countDownLatch.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }

    //用CyclicBarrier
    private static String test2() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            value = "test2";
        });
        executorService.execute(() -> {

            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(value);
        return value;
    }

    //用Phaser
    class Test03Phaser extends Phaser {
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    value ="test3" ;
                    return true;
                default:
                    System.out.println("结束");
                    return true;
            }
        }
    }
    class Test03Thread implements Runnable {
        private  Phaser phaser;
        public Test03Thread(Test03Phaser test03Phaser){
            this.phaser = test03Phaser;
        }
        @Override
        public void run() {
            phaser.arriveAndAwaitAdvance();
            System.out.println("全部完成");
        }
    }

    private  String test3() {
        Test03Phaser test03Phaser = new Test03Phaser();
        Test03Thread test03Thread = new Test03Thread(test03Phaser);
        test03Phaser.register();
        Thread thread1 = new Thread(test03Thread);
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        return value;
    }

    //用synchronized
    private static String test4() {
        Object o = new Object();
        synchronized (o) {
            executorService.execute(() -> {
                synchronized (o) {
                    value = "test4";
                    o.notifyAll();
                }

            });
            try {
                o.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    //用Future
    private static String test5() {
        Future<String> submit = executorService.submit(() -> {
            return "test5";
        });
        String s = null;
        try {
            s = submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return s;
    }

    //用ExecutorCompletionService
    private static String test6() {
        ExecutorCompletionService<String> executorCompletionService = new ExecutorCompletionService(executorService);
        executorCompletionService.submit(() -> {
            return "test6";
        });
        String o = null;
        try {
            o = executorCompletionService.take().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return o;
    }

    //用ForkJoin
    class Test07RecursiveTask extends RecursiveTask<String> {
        public  String n;
        Test07RecursiveTask(String n){
            this.n = n;
        }

        public  String compute(){
            if(n.length() <= 1){
                return n;
            }
            Test07RecursiveTask f1 = new Test07RecursiveTask(n.substring(1));
            // 创建子任务 异常激素三
            f1.fork();
            Test07RecursiveTask f2 = new Test07RecursiveTask(n.substring(2));
            // 等待子任务结果，并合并结果
            return f2.compute() + f1.join();

        }

    }

    private  String test7() {
        ForkJoinPool fjp = new ForkJoinPool(1);
        Test07RecursiveTask test07RecursiveTask = new Test07RecursiveTask("test7");
        String result =fjp.invoke(test07RecursiveTask);
        return result;
    }

    //用CompletableFuture
    private  String test8() {
        CompletableFuture<String> future =  CompletableFuture.supplyAsync(()->{
                    return "test08";
                }

        );
        String value = null;
        try {
            value = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return value;
    }

    //用ReentrantLock
    private static String test09() {
        ReentrantLock reentrantLock = new ReentrantLock( );
        Condition condition = reentrantLock.newCondition();
        try {
            reentrantLock.lockInterruptibly();
            executorService.execute(() -> {
                try {
                    reentrantLock.lockInterruptibly();
                    value = "test09";
                    condition.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    reentrantLock.unlock();
                }


            });
            condition.await();

        } catch (InterruptedException e) {
            e.printStackTrace();

        }finally {
            reentrantLock.unlock();
        }
        return value;
    }

    //用ReentrantReadWriteLock
    private static String test10() {
        ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock( );
        ReentrantReadWriteLock.WriteLock writeLock = reentrantLock.writeLock();
        Condition condition = writeLock.newCondition();
        try {
            writeLock.lockInterruptibly();
            executorService.execute(() -> {
                try {
                    writeLock.lockInterruptibly();
                    value = "test09";
                    condition.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    writeLock.unlock();
                }


            });
            condition.await();

        } catch (InterruptedException e) {
            e.printStackTrace();

        }finally {
            writeLock.unlock();
        }
        return value;
    }

    //用LockSupport
    private static String test11() {
        Thread thread = Thread.currentThread();
        executorService.execute(() -> {
            value = "test11";
            LockSupport.unpark(thread);
        });
        LockSupport.park();
        return value;
    }

    //用Exchanger
    private static String test12() {
        Exchanger<String> exchanger = new Exchanger<>();
        executorService.execute(() -> {
            try {
                String str = exchanger.exchange("test12");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        try {
            value = exchanger.exchange("结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }
}
