package com.ct.study.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ct.study.R
import com.ct.study.rv.RvActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 针对 Notification
 *
 * 1.状态栏与抽屉式通知
 * 2.浮动通知
 * 3.锁屏通知
 * 4.应用图标通知
 *  https://developer.android.google.cn/guide/topics/ui/notifiers/notifications
 *
 *  从 Android 8.0（API 级别 26）开始，所有通知都必须分到一个渠道，否则通知将不会显示
 * */
class NotificationActivity : AppCompatActivity() {

    private val CHANNEL_NORMAL = "com.ct.notify.normal"
    private val CHANNEL_PROGRESS = "com.ct.notify.progress"
    private val CHANNEL_FULL = "com.ct.notify.full"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
    }


    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_notify_normal -> RxNotificationBuilder.buildNormalNotification(
                CHANNEL_NORMAL,
                this,
                "普通通知",
                "这是一个很普通的通知",
                intent = Intent(this, RvActivity::class.java),
                action = Intent(this, TestBroadcastReceiver::class.java),
                reply = Intent(this, TestBroadcastReceiver::class.java)
            )
            R.id.btn_notify_progress -> RxNotificationBuilder.buildProgressNotification(
                CHANNEL_PROGRESS,
                this,
                "显示进度条",
                "数据下载中"
            )
            R.id.btn_notify_down -> down()
            R.id.btn_notify_full -> GlobalScope.launch {
                delay(3000)
                RxNotificationBuilder.buildUrgencyNotification(
                    CHANNEL_FULL, this@NotificationActivity, "紧急通知", "测试紧急通知",
                    Intent(this@NotificationActivity, RvActivity::class.java)
                )
            }
        }
    }


    /**
     * 模拟一个下载的耗时操作
     * 来更新通知
     * */
    private fun down() {

        object : Thread() {
            override fun run() {
                (1..10).forEach { it ->
                    sleep(1000)
                    val builder =
                        NotificationCompat.Builder(this@NotificationActivity, CHANNEL_PROGRESS)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("显示进度条")

                            .setPriority(NotificationCompat.PRIORITY_LOW) //不要有铃音

                    //移除通知 setProgress(0, 0, false)
                    if (it == 10) {
                        builder.setProgress(0, 0, false)
                            .setContentText("下载完成")
                    } else {
                        builder.setProgress(100, it * 10, false)
                            .setContentText("${it * 10}%")
                    }

                    NotificationManagerCompat.from(this@NotificationActivity)
                        .notify(101, builder.build())

                }


            }
        }.start()
    }
}
