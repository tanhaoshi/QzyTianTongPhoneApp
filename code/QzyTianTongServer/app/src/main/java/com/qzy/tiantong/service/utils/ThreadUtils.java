package com.qzy.tiantong.service.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 *  THE AUTHOR : TANHAOSHI
 *  THE TIME   : 2019/1/7
 */
public final class ThreadUtils {

    private static final Map<Integer,Map<Integer,ExecutorService>> TYPE_PRIORITY_POOLS =
            new ConcurrentHashMap<>();

    private static final Map<Task,ScheduledExecutorService> TASK_SCHEDULED      =
            new ConcurrentHashMap<>();

    private static final byte TYPE_SINGLE = -1;
    private static final byte TYPE_CACHED = -2;
    private static final byte TYPE_IO     = -4;
    private static final byte TYPE_CPU    = -8;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static void removeScheduleByTask(final Task task){
        ScheduledExecutorService scheduled = TASK_SCHEDULED.get(task);
        if(scheduled != null){
            TASK_SCHEDULED.remove(task);
            scheduled.shutdownNow();
        }
    }

    /**
     * Return wether the thread is the main thread
     * @return true or false
     */
    public static boolean isMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static ExecutorService getCachedPool(){
        return getPoolByTypeAndPriority(TYPE_CACHED);
    }

    private static ExecutorService getPoolByTypeAndPriority(final int type) {
        return getPoolByTypeAndPriority(type, Thread.NORM_PRIORITY);
    }

    private static ExecutorService getPoolByTypeAndPriority(final int type, final int priority){
        ExecutorService pool;
        Map<Integer,ExecutorService> priorityPools = TYPE_PRIORITY_POOLS.get(type);
        if(priorityPools == null){
            priorityPools = new ConcurrentHashMap<>();
            pool = createPoolByTypeAndPriority(type,priority);
            priorityPools.put(priority,pool);
            TYPE_PRIORITY_POOLS.put(type,priorityPools);
        }else{
            pool = priorityPools.get(priority);
            if(pool == null){
                pool = createPoolByTypeAndPriority(type,priority);
                priorityPools.put(priority,pool);
            }
        }
        return pool;
    }

    private static ExecutorService createPoolByTypeAndPriority(final int type, final int priority){
        switch (type){
            case TYPE_SINGLE:
                return Executors.newSingleThreadExecutor(
                        new UtilsThreadFactory("single",priority)
                );
            case TYPE_CACHED:
                return Executors.newCachedThreadPool(
                        new UtilsThreadFactory("cached",priority)
                );
            case TYPE_IO:
                return new ThreadPoolExecutor(2*CPU_COUNT+1,2*CPU_COUNT+1,30, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(128),new UtilsThreadFactory("io",priority));
            case TYPE_CPU:
                return new ThreadPoolExecutor(CPU_COUNT+1,2*CPU_COUNT+1,30, TimeUnit.SECONDS
                ,new LinkedBlockingQueue<Runnable>(128),new UtilsThreadFactory("cpu",priority));
            default:
                return Executors.newFixedThreadPool(
                        type,
                        new UtilsThreadFactory("fixed("+type+")",priority)
                );
        }
    }

    public abstract static class Task<T> implements Runnable {

        private boolean isSchedule;

        private volatile int state;
        private static final int NEW        = 0;
        private static final int COMPLETING = 1;
        private static final int CANCELLED  = 2;
        private static final int EXCEPTIONAL= 3;

        public Task(){
            this.state = NEW;
        }

        @Nullable
        public abstract T doInBackground() throws Throwable;

        public abstract void onSuccess(@Nullable T result);

        public abstract void onCancel();

        public abstract void onFail(Throwable e);

        @Override
        public void run() {
            try{
                final T result = doInBackground();
                if(state != NEW) return;

                if(isSchedule){
                    Deliver.post(new Runnable() {
                        @Override
                        public void run() {
                            onSuccess(result);
                        }
                    });
                }else{
                    state = COMPLETING;
                    Deliver.post(new Runnable() {
                        @Override
                        public void run() {
                            onSuccess(result);
                            removeScheduleByTask(Task.this);
                        }
                    });
                }
            }catch (final Throwable throwable){
                if(state != NEW) return;
                state = EXCEPTIONAL;
                Deliver.post(new Runnable() {
                    @Override
                    public void run() {
                        onFail(throwable);
                        removeScheduleByTask(Task.this);
                    }
                });
            }
        }

        public void cancel(){
            if(state != NEW) return;

            state = CANCELLED;
            Deliver.post(new Runnable() {
                @Override
                public void run() {
                    onCancel();
                    removeScheduleByTask(Task.this);
                }
            });
        }
    }

    private static class Deliver{

        private static final Handler MAIN_HANDLER;

        static {
            Looper looper;

            try{
                looper = Looper.getMainLooper();
            }catch (Exception e){
                looper = null;
            }
            if(looper != null){
                MAIN_HANDLER = new Handler(looper);
            }else{
                MAIN_HANDLER = null;
            }
        }

        static void post(final Runnable runnable){
            if(MAIN_HANDLER != null){
                MAIN_HANDLER.post(runnable);
            }else{
                runnable.run();
            }
        }
    }

    private static final class UtilsThreadFactory extends AtomicLong
            implements ThreadFactory {

        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup mGroup;
        private final String namePrefix;
        private final        int           priority;

        UtilsThreadFactory(String prefix, int priority){
            SecurityManager s = System.getSecurityManager();
            mGroup = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = prefix + "-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
            this.priority = priority;
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(mGroup,r,namePrefix+getAndIncrement(),0){
                @Override
                public void run() {
                    try{
                        super.run();
                    }catch (Exception e){
                        Log.i("ThreadUtils","Request threw uncaught throwable");
                    }
                }
            };
            if(t.isDaemon()){
                t.setDaemon(false);
            }
            t.setPriority(priority);
            return t;
        }
    }
}
