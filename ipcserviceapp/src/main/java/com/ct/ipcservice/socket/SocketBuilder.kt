package com.ct.ipcservice.socket

import android.util.Log
import java.io.*
import java.net.ServerSocket

class SocketBuilder {

    fun createSocket() {

        try {

            Thread(Runnable {
                val socket = ServerSocket(8888)
                Log.e("TAG", "服务端准备成功....")
                while (true) {

                    val client = socket.accept()
                    Log.e("TAG", "接收到客户端---$client")
                    val inReader = BufferedReader(InputStreamReader(client.getInputStream()))
                    val outWriter =
                        PrintWriter(BufferedWriter(OutputStreamWriter(client.getOutputStream())))

                    while (true) {
                        val read = inReader.readLine()
                        Log.e("TAG", "获取客户端发送的消息:$read")
                        if ("Off" == read) {
                            outWriter.println("这是服务端发送的消息...OFF")
                            break
                        }
                        outWriter.println("这是服务端发送的消息...")
                        outWriter.flush()

                    }

                    outWriter.close()
                    inReader.close()
                    client.close()
                }


            }).start()


        } catch (e: Exception) {
            Log.e("TAG", "服务器异常:${e.message}'")
            e.printStackTrace()
        }
    }
}