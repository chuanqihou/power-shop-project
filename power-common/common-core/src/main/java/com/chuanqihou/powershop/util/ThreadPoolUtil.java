package com.chuanqihou.powershop.util;

import java.util.concurrent.*;

/**
 * @author 传奇后
 * @date 2023/6/28 10:09
 * @description 线程池工具类
 */
public class ThreadPoolUtil {
    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            10, // 线程池中的核心线程数量，线程数低于该值有新的任务则新建线程，设置为0则线程池中所有线程都是非核心线程
            100,    // 线程池中最大线程数量，若任务队列已满，且线程池线程数量小于该值，则会创建新的线程执行任务。
            30,     // 线程池中的非核心线程在空闲时最长保留时间
            TimeUnit.SECONDS,   // 非核心线程在哪空血时保留时间单位
            new LinkedBlockingQueue<Runnable>(2000), // 等待执行任务存储的队列，执行的是FIFIO（先进先出）原则（可以是：LinkedBlockingQueue、ArrayBlockingQueue）
            Executors.defaultThreadFactory(), // 创建线程的工厂类，通常使用：Executors.defaultThreadFactory()
            new ThreadPoolExecutor.AbortPolicy() // 拒绝策略，当队列和线程池都满了，执行拒绝策略，默认为：ThreadPoolExecutor.AbortPolicy() 即饱和策略 直接抛出RejectedExecutionException异常
    );
}
