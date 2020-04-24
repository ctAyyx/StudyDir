package com.ct.study.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.ct.study.R

/**
 * @ClassName : RxNotificationBuilder
 * @CreateDate : 2020/4/22 14:30
 * @Author : CT
 * @Description :
 *
 */
object RxNotificationBuilder {

    /**
     * 创建一个普通的通知
     * */
    fun buildNormalNotification(
        channel_id: String,
        context: Context,
        title: String,
        msg: String,
        intent: Intent? = null,
        action: Intent? = null,
        reply: Intent? = null
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //在Android8.0(26)以上 必须要创建通知渠道 才会显示该通知
            val channelName = "普通通知" //渠道名
            val description = "显示普通消息的通知" //描述

            val importance = NotificationManager.IMPORTANCE_DEFAULT //设置通知的重要程度,在8.0以下是通过设置Priority
            //创建通知渠道
            val channel = NotificationChannel(channel_id, channelName, importance)
            channel.description = description

            //将该通知渠道绑定到系统,在此之后你将不能改变该渠道的重要性和其他行为
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        //创建通知 使用NotificationCompat可以向下兼容到4.0(14)
        //传递渠道ID是为了兼容8.0(26)
        val builder = NotificationCompat.Builder(context, channel_id)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(msg)
            .setAutoCancel(true) //设置当用户单击时 是否取消
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //就算在通知渠道中设置了importance 在这里也要设置Priority 这是为了兼容8.0以下的版本

        //如果点击通知需要跳转到一个Activity
        if (intent != null) {
            val pendingIntent =
                PendingIntent.getActivity(context, 200, intent, PendingIntent.FLAG_ONE_SHOT)
            builder.setContentIntent(pendingIntent)
        }

        //添加操作按钮
        //一个通知最多可以提供三个操作按钮
        //要添加操作按钮，请将 PendingIntent 传递给 addAction() 方法
        //在这里我们注册一个广播
        if (action != null) {
            action.putExtra("Name", "测试Action")
            val pendingIntent =
                PendingIntent.getBroadcast(context, 201, action, PendingIntent.FLAG_ONE_SHOT)
            builder.addAction(R.drawable.ic_clear_white_48dp, "打开广播", pendingIntent)
        }

        //添加回复操作
        //在Android 7.0(24)
        if (reply != null) {
            //第一步 创建远程输入对象
            //这里的Key就是将输入的数据放入Intent中的Key
            val remoteInput = RemoteInput.Builder("Reply")
                .setLabel("请输入")
                .build()
            //第二部 创建PendingIntent
            val replayIntent =
                PendingIntent.getBroadcast(context, 202, reply, PendingIntent.FLAG_UPDATE_CURRENT)

            //第三步 创建Action对象 并将远程输入对象附加到Action上
            val replyAction = NotificationCompat.Action.Builder(
                R.drawable.ic_clear_white_48dp,
                "回复消息",
                replayIntent
            )
                .addRemoteInput(remoteInput)
                .build()
            //添加到通知
            builder.addAction(replyAction)

            //后续操作 到Receiver中查看
        }


        //发送通知
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(100, builder.build())

    }


    /**
     * 创建一个带进度条的通知
     * */
    fun buildProgressNotification(
        channel_id: String,
        context: Context,
        title: String,
        msg: String
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelName = "下载通知"
            val description = "显示普通消息的通知"

            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(channel_id, channelName, importance)
            channel.description = description

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channel_id)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(msg)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        //设置进度条
        builder.setProgress(100, 0, false)
        NotificationManagerCompat.from(context).notify(101, builder.build())


    }


    /**
     * 创建紧急消息
     * */
    fun buildUrgencyNotification(
        channel_id: String,
        context: Context, title: String,
        msg: String,
        intent: Intent? = null
    ) {
        initChannel(context, channel_id, "紧急通知", NotificationManager.IMPORTANCE_HIGH, "")

        val builder = NotificationCompat.Builder(context, channel_id)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(msg)
           .setPriority(NotificationCompat.PRIORITY_HIGH)
        if (intent != null) {
            val pendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            builder.setFullScreenIntent(pendingIntent, true)
            builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            builder.setCategory(NotificationCompat.CATEGORY_MESSAGE)
            builder.setStyle(NotificationCompat.MessagingStyle("11"))
        }

        NotificationManagerCompat.from(context)
            .notify(102, builder.build())

    }


    private fun initChannel(
        context: Context,
        channel_id: String,
        channelName: String,
        importance: Int,
        channelDescription: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channel_id, channelName, importance)
            channel.description = channelDescription
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}