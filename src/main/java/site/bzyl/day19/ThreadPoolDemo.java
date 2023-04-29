package site.bzyl.day19;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.ThreadPoolDemo")
public class ThreadPoolDemo {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(1, 1, TimeUnit.SECONDS, 1,
                (queue, task) -> {
                    // 1. 死等
                    /*queue.put(task);*/

                    // 2. 带超时等待
                    /*if (!queue.put(task, 1, TimeUnit.SECONDS)) {
                        log.debug("等待超时，加入阻塞队列失败，放弃task-{}", task);
                    }*/

                    // 3. 让调用者放弃执行
                    /*log.debug("放弃...task-{}", task);*/

                    // 4. 让调用者抛出异常
                    /*throw new RuntimeException("任务执行失败 task：" + task);*/

                    // 5.让调用者自己执行任务
                    /*task.run();*/
                });
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                try {
                    Thread.sleep(1000);
                    log.debug("任务执行完毕-{}", finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

@FunctionalInterface
interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}

@Slf4j(topic = "c.ThreadPool")
class ThreadPool {
    // 任务队列
    private BlockingQueue<Runnable> taskQueue;

    // 线程集合
    private HashSet<Worker> workers = new HashSet<>();

    // 核心线程数
    private int coreSize;

    // 超时时间
    private long timeout;

    // 时间单位
    private TimeUnit unit;

    // 线程池拒绝策略
    RejectPolicy<Runnable> rejectPolicy;

    public ThreadPool(int coreSize, long timeout, TimeUnit unit, int queueCapacity, RejectPolicy<Runnable> rejectPolicy) {
        log.debug("初始化线程池...核心数:{}, 超时时间:{}, 时间单位:{}, 阻塞队列长度:{}",
                coreSize, timeout, unit, queueCapacity);
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.unit = unit;
        this.taskQueue = new BlockingQueue<>(queueCapacity);
        this.rejectPolicy = rejectPolicy;
    }

    // 执行任务
    public void execute(Runnable task) {
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                log.debug("创建worker-{}, task-{}", worker, task);
                workers.add(worker);
                worker.start();
            } else {
                // 死等策略
                // taskQueue.put(task);
                taskQueue.tryPut(rejectPolicy, task);
            }
        }
    }


    // 包装线程对象的内部类
    class Worker extends Thread {
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            while (task != null || (task = taskQueue.poll(timeout, unit)) != null) {
                log.debug("正在执行...worker-{}, task-{}", this, task);
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                log.debug("移除worker-{}", this);
                workers.remove(this);
            }
        }
    }
}

@Slf4j(topic = "c.BlockingQueue")
class BlockingQueue<T> {
    // 1.队列
    private Deque<T> queue = new ArrayDeque<>();

    // 2.队列长度
    private int capacity;

    // 3.锁
    private final ReentrantLock lock = new ReentrantLock();

    // 4.生产者条件变量
    private final Condition fullWaitSet = lock.newCondition();

    // 5.消费者条件变量
    private final Condition emptyWaitSet = lock.newCondition();


    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    // 带超时的阻塞获取
    public T poll(long timeout, TimeUnit unit) {
        lock.lock();

        try {
            // 单位统一为 纳秒 为的是调用 Condition 的 awaitNanos 方法
            long nanos = unit.toNanos(timeout);
            while (queue.size() == 0) {
                try {
                    // 返回的是剩余时间
                    if (nanos <= 0) {
                        return null;
                    }
                    // 防止虚假唤醒
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T task = queue.removeFirst();
            fullWaitSet.signal();
            return task;
        } finally {
            lock.unlock();
        }
    }


    // 阻塞获取
    public T poll() {
        lock.lock();

        try {
            while (queue.size() == 0) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T task = queue.removeFirst();
            fullWaitSet.signal();
            return task;
        } finally {
            lock.unlock();
        }
    }


    // 带超时阻塞添加
    public boolean put(T task, long timeout, TimeUnit timeUnit) {
        lock.lock();
        try {
            long nanos = timeUnit.toNanos(timeout);
            while (queue.size() == capacity) {
                if (nanos <= 0)
                    return false;
                log.debug("等待加入任务队列-{}", task);
                try {
                    nanos = fullWaitSet.awaitNanos(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("加入任务队列...task-{}", task);
            queue.addLast(task);
            emptyWaitSet.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    // 阻塞添加
    public void put(T task) {
        lock.lock();

        try {
            while (queue.size() == capacity) {
                log.debug("等待加入任务队列-{}", task);
                fullWaitSet.await();
            }
            log.debug("加入任务队列...task-{}", task);
            queue.addLast(task);
            emptyWaitSet.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    // 获取队列长度
    public int size() {
        lock.lock();

        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    // 自定义拒绝策略的添加
    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();

        try {
            if (queue.size() == capacity) { // 队列已满
                rejectPolicy.reject(this, task);
            } else { // 队列未满
                log.debug("加入任务队列...task-{}", task);
                queue.addLast(task);
                emptyWaitSet.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}