package com.ct.ipc.socket

import android.util.Log
import java.io.*
import java.net.Socket

class SocketBuilder {
    fun createClientSocket() {

        Thread(Runnable {
            try {
                var client: Socket? = null
                while (client == null) {
                    client = Socket("localhost", 8888)
                    Log.e("TAG","尝试链接服务端'")
                }
                val outWriter =
                    PrintWriter(BufferedWriter(OutputStreamWriter(client.getOutputStream())), true)
                val inReader = BufferedReader(InputStreamReader(client.getInputStream()))


                for (i in 0..5) {
                    Log.e("TAG","开始发送数据....")
                    outWriter.println("我是客户端----$i")
                    Log.e("TAG","等待接收数据....")
                    Thread.sleep(2000)
                    Log.e("TAG", "获取服务端信息:${inReader.readLine()}")
                    Thread.sleep(1000)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("TAG", "Socket异常：${e.message}")
            }
        }).start()
    }
}