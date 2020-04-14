package org.examples.rocketmq.consumer.utils;

/**
 * @Version:
 * @Date: 2020/4/14
 * @Company: ruixiaoyun.ltd
 */
public class ThreadLocalMessageType {

    private static ThreadLocal<String> threadLocalMessageType = new ThreadLocal<>();

    public static void set(String type) {
        threadLocalMessageType.set(type);
    }

    public static String get() {
        return threadLocalMessageType.get();
    }

    public static void clear() {
        threadLocalMessageType.remove();
    }
}
