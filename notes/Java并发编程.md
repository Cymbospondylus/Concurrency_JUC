# Java并发编程

## 进程和线程

### 1.进程与线程

- #### 进程是资源分配的最小单位

  当一个程序被运行时，这个程序的代码（数据和指令）从磁盘加载到内存中，此时操作系统就开启了一个进程，即进程可以视为程序的一个实例。

- #### 线程是CPU调度的最小单位

  一个线程就是一个指令流，也叫一条执行路径（execution path），将指令流中的一条条指令以一定的顺序交给CPU执行。

  

### 2.进程间通信与线程间通信

- 同一台计算机的进程通信成为IPC（Inter-process communication）。
- 不同计算机的进程通信需要通过网络，并且遵守相同的协议，如HTTP协议。
- 线程间通信更高效，线程上下文切换成本低于进程间上下文切换（状态保存在PCB中），线程创建成本也低于进程创建成本。



### 3.并行和并发

- 并发（Concurrency）是同一时间处理（dealing with）多件事情的能力
- 并行（Parallel）是同一时间同时做（doing）多件事情的能力



## 4.@FunctionalInterface注解

- JDK中只有一个抽象方法的接口可以添加函数式接口@FunctionalInterface注解，这个接口的实现可以被lambda表达式简化



## 5.组合由于继承原则

- 组合由于继承原则：若某个场景的代码复用既可以通过类继承实现，也可以通过对象组合实现，尽量选择对象组合的方式（Favor object composition over class inheritance）。举个栗子，尽量使用Runnable作为任务，用Thread作为线程的组合方式，而不是继承Thread类重写run方法来实现创建线程。

## 5.FutureTask和Future

- FutureTask类间接实现了Runnable接口，所以和实现Runnable一样可以作为一个任务，用于配合Thread线程类。同时还实现了Future接口，通过get（）方法返回任务执行的结果。

- FutureTask类能够接收Callable的实现类作为参数，用于返回线程任务执行的结果。