package com.maojie.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: Utility class for managing ThreadLocal storage, allowing thread-specific data storage and retrieval.
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {

    // ThreadLocal object to store data specific to the current thread
    private static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * @Description: Retrieves the value stored in the current thread's ThreadLocal storage.
     * @return: T - The value associated with the current thread
     */
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    /**
     * @Description: Stores a value in the current thread's ThreadLocal storage.
     * @Param: value - The value to be stored
     * @return: void
     */
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }

    /**
     * @Description: Clears the current thread's ThreadLocal storage to prevent memory leaks.
     * @return: void
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
