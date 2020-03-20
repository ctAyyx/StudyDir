package com.ct.kt

import android.util.Log
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 *
 * Kotlin 协程构建器s
 * suspend 标记函数为挂起函数
 * async 并发操作 类似launch
 * Global.launch{} -->启动一个全局协程 生命周期受主线程影响
 *
 * runBlocking{} -->启动一个协程 阻塞当前线程 直到runBlocking里面的协程执行完毕
 *
 * coroutineScope{} -->启动一个协程 不阻塞当前线程 会阻塞协程 直到coroutineScope里面的协程执行完毕
 *
 * withTimeOut()/withTimeOutNull -->指定时间超时自动取消协程
 *
 *
 * */

private const val TAG = "KT"

class CoroutinesBuilder {


    //针对Global.launch{}启动协程的生命周期
    fun globalMain() {

        //直接运行在主线程
        GlobalScope.launch {
            delay(3000)
            log("当前协程所在线程1 ${Thread.currentThread().name} ")
        }


        Thread {
            log("当前线程 ${Thread.currentThread().name} ")
            GlobalScope.launch {
                delay(1000)
                log("当前协程所在线程2 ${Thread.currentThread().name} ")
            }

            log("当前线程 ${Thread.currentThread().name} End")
        }.start()
        Thread.sleep(2000)
        //结论: 无论在哪里启动Global 协程生命周期受主线程影响
    }

    //关于runBlock
    fun runBlockMain() {
        Thread {
            log("0线程已经启动...")
            runBlocking {
                delay(3000)
                log("线程runBlocking ----0")
            }
        }.start()
        runBlocking {
            delay(6000)
            log("主线程runBlocking ----1")


        }
        Thread {
            log("2线程已经启动...")
            runBlocking {
                delay(3000)
                log("线程runBlocking ----2")
            }
        }.start()

        //结论 : runBlocking阻塞当主线程 不管在那个线程中使用
    }


    //关于 runBlocking 和 coroutineScope
    fun runBlockAndCoroutineScope() {
        //runBlocking 会阻塞当前线程
        runBlocking {
            launch {
                delay(1000)
                log("runBlocking -- launch 01")
            }
            log("runBlocking -- over")
        }
        //coroutineScope不会阻塞当前线程 他是一个挂起函数 必选要在协程中调用
//但是他会阻塞当前协程
        GlobalScope.launch {
            coroutineScope {

                launch {
                    delay(3000)
                    log("coroutineScope -- launch 01")
                }
                log("coroutineScope -- over")
            }

        }


    }

    fun runBlockAndCoroutineScope2() {
        //runBlocking 会阻塞当前线程
        runBlocking {
            launch {
                delay(1000)
                log("runBlocking -- launch 01")
            }

            coroutineScope {
                log("coroutineScope -- start")
                launch {
                    delay(6000)
                    log("coroutineScope -- launch 01")
                }
                log("coroutineScope -- over")
            }
            launch {
                delay(1000)
                log("runBlocking -- launch 02")
            }

            log("runBlocking -- over")
        }


    }


    /***********************************协程的取消 超时***********************************/

    //取消协程
    //使用job.cancel取消协程
    //使用job.join 等待该作业结束
    //main 函数调用了 job.cancel，我们在其它的协程中就看不到任何输出，因为它被取消了
    //正在计算的协程如果没有检查取消 是不会取消执行的
    //这是我们可以使用 isActive来检查协程是否取消
    fun cancelJob() {
        runBlocking {

            val job = launch {

                try {
                    repeat(10) {
                        log("Job:当前完成度:$it")
                        delay(500)
                    }
                } finally {
                    //这里面不能再执行耗时操作了
                    log("Job:运行Finally...")
                    //如果一定要做耗时操作 则可以使用  withContext(NonCancellable)
                    withContext(NonCancellable) {
                        delay(2000)
                        log("Job:运行Finally...等待2秒")
                    }

                }

            }

            delay(1499)
            log("主线程准备关闭")
            job.cancel()
            job.join()//在这里调用 等待协程结束finally代码块
            log("主线程关闭")
        }
    }


    //超时 可以使用withTimeOut 协程运行超过指定时间会抛出一个TimeoutCancellationException
    fun timeOutJob() {
        runBlocking {

            try {
                withTimeout(1300) {
                    launch {
                        repeat(50) {
                            log("JOb:测试超时--$it")
                            delay(500)
                        }
                    }
                }
            } catch (e: TimeoutCancellationException) {
                log("协程超时捕获。。。。")
            }


        }
    }

    //或则使用withTimeOutNull
    fun timeOutJob2() {

        runBlocking {
            val result = withTimeoutOrNull(1300) {
                launch {
                    repeat(20) {
                        log("JOb:测试超时--$it")
                        delay(500)
                    }
                }
            }

            //协程超时 返回值为Null
            log("获取的结果$result")
        }
    }


    //并发执行协程
    fun asyncJob() {

        runBlocking {
            val time = measureTimeMillis {
//                val num1 = num1()
//                val num2 = num2()
//                log("计算的和:${num1 + num2}")

                //用async修饰
//                val num1 = async { num1() }
//                val num2 = async { num2() }
//                log("计算的和:${num1.await() + num2.await()}")

                //惰性并发
                val num1 = async(start = CoroutineStart.LAZY) { num1() }
                val num2 = async(start = CoroutineStart.LAZY) { num2() }
                num1.start()
                num2.start()
                log("计算的和:${num1.await() + num2.await()}")
            }
            log("耗时--》$time")
        }
    }

    suspend fun num1(): Int {
        delay(1200)
        return 20
    }

    suspend fun num2(): Int {
        delay(2000)
        return 30
    }


    fun doWork() {
        //启动一个耗时操作的协程
        //这里使用runBlocking直接阻塞主线程
        runBlocking {
            delay(20 * 1000)
            log("网络请求完成 获取数据....")
        }
    }


    /********************************协程上下文 与 调度器***********************************************/
    //在不同线程中跳转
    fun checkThread() {
        runBlocking {
            newSingleThreadContext("Thread-01").use { t01 ->
                log("start in t01 ----")
                launch (t01) {
                    log("start in t01")
                    delay(2000)
                }
                withContext(this.coroutineContext) {
                    log("Back in ")
                }
            }
        }


    }

}

fun log(msg: String) {
    println("[${Thread.currentThread().name}] $msg")
    // Log.e(TAG, msg)
}

fun main() {

    println("=======Main Start=========")
    val builder = CoroutinesBuilder()
    //针对Global协程的生命周期范围
    //builder.globalMain()
    //针对runBlocking协程影响范围
    //builder.runBlockMain()
    //针对 runBlocking 和 coroutineScope
    //builder.runBlockAndCoroutineScope2()

    /***************协程的取消 超时******************/
    //取消协程
    //builder.cancelJob()
    //超时自动取消协程
    //builder.timeOutJob2()
    //并发操作
    //builder.asyncJob()
    builder.checkThread()
    println("=======Main Een=========")
}
