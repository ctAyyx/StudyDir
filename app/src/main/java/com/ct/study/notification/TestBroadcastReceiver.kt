package com.ct.study.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.ct.study.R

/**
 * @ClassName : TestBroadcastReceiver
 * @CreateDate : 2020/4/22 15:35
 * @Author : CT
 * @Description :
 *
 */
class TestBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("TAG", "收到的消息$intent")
        if (intent == null)
            return

        handleReply(context, intent)

    }

    /**
     * 处理 回复消息
     * */
    private fun handleReply(context: Context?, intent: Intent) {

        //要从通知回复界面接收用户输入，请调用 RemoteInput.getResultsFromIntent() 并将 BroadcastReceiver 收到的 Intent 传递给它：
        val bundle = RemoteInput.getResultsFromIntent(intent)
        val code = RemoteInput.getResultsSource(intent)
        val msg = bundle?.getString("Reply")
        Log.e("TAG", "$code 收到的数据:$msg")

        //处理完文本后，必须使用相同的 ID 和标记（如果使用）调用 NotificationManagerCompat.notify() 来更新通知
        if (context == null)
            return
        val replyNotification = NotificationCompat.Builder(context, "normal")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("普通通知")
            .setContentText("OK 收到消息了")
            .build()
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(100, replyNotification)
    }

}