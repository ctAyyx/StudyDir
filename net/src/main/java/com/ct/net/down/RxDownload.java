package com.ct.net.down;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * 文件下载
 */
public class RxDownload {

    public void startDown(Context context) {
        DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(""));
        //移动网络下是否允许漫游
        downloadRequest.setAllowedOverRoaming(true);
        //在通知栏里面显示
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        //
        downloadRequest.setTitle("下载APK");
        //
        downloadRequest.setDescription("下载描述。。。");
        //
        downloadRequest.setVisibleInDownloadsUi(true);

        File downFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "test.apk");
        downloadRequest.setDestinationUri(Uri.fromFile(downFile));
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        long downId = downloadManager.enqueue(downloadRequest);
    }

}
