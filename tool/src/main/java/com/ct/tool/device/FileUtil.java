package com.ct.tool.device;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;


import androidx.core.content.FileProvider;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtil {
    private static final String address = "com.ct.baseapp.device.CFileProvider";

    private static String getAppDir() {
        return Environment.getExternalStorageDirectory() + "/" + "test";//App.applicationContext.getPackageName();
    }


    private static String mkdirs(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }

    public static File mkFile(String filePath) {

        if (TextUtils.isEmpty(filePath))
            throw new NullPointerException("文件路径不能为空!");

        File file = new File(filePath);

        if (file.exists() && !file.isDirectory())
            throw new ClassFormatError("只是目录路径,需要一个文件路径");

        if (file.exists() && !file.delete())
            throw new SecurityException("文件已存在,并且无法删除");

        return file;
    }


    /*
     * 在公共目录创建文件(不随应用卸载而删除)
     * */

    public static String mkEverDir(String fileName) {

        return mkdirs(getAppDir() + "/" + fileName);
    }


    /*
     *
     * 创建一个随应用卸载而卸载的文件
     * */
    public static File mkAssociatedDir(Context context, String fileName) {
        return context.getExternalFilesDir(fileName);
    }


    /*
     * 获取缓存目录
     * */
    public static File getCacheDir(Context context) {

        if (DeviceUtil.sDCardIsAvailable())
            return context.getExternalCacheDir();
        return context.getCacheDir();
    }

    /**
     * 获取文件创建时间
     */
    public static String getMkTime(File file) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                .format(new Date(file.lastModified()));
    }


    /**
     * 判断一个文件是否存在指定目录下
     */
    public static boolean checkFile(File dir, String fileName) {
        if (!dir.isDirectory()) {
            return false;
        }

        File[] files = dir.listFiles();

        for (File file : files) {
            if (!file.isDirectory()) {
                if (fileName.equals(file.getName()))
                    return true;
            }

        }
        return false;
    }


    /**
     * 获取缓存大小
     * <p>
     * 通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     * 通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     */
    public static String getCacheSize(Context context) {
        long fileSize = 0;
        String cacheSize = "0KB";

        File fileDir = context.getFilesDir();
        File cacheDir = context.getCacheDir();
        File externalCacheDir = context.getExternalCacheDir();

        fileSize += getDirSize(fileDir);
        fileSize += getDirSize(cacheDir);
        fileSize += getDirSize(externalCacheDir);
        if (fileSize > 0)
            cacheSize = formatFileSize(fileSize);
        return cacheSize;

    }

    /**
     * 计算目录文件大小
     */
    public static long getDirSize(File dir) {
        if (dir == null)
            return 0;
        if (!dir.isDirectory())
            return 0;

        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile())
                dirSize += file.length();
            else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file);
            }
        }
        return dirSize;
    }


    /**
     * 转换文件大小
     */
    public static String formatFileSize(long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString;
        if (fileSize < 1024)
            fileSizeString = df.format((double) fileSize) + "B";
        else if (fileSize < 1024 * 1024)
            fileSizeString = df.format((double) fileSize / 1024) + "KB";
        else if (fileSize < 1024 * 1024 * 1024)
            fileSizeString = df.format((double) fileSize / (1024 * 1024)) + "MB";
        else
            fileSizeString = df.format((double) fileSize / (1024 * 1024 * 1024)) + "G";
        return fileSizeString;
    }


    /**
     * 清空应用缓存
     */
    public static void cleanAppCache(Context context) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanFiles(context);
    }

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     */
    public static void deleteFilesByDirectory(File fileDir) {
        if (fileDir != null && fileDir.exists() && fileDir.isDirectory()) {
            File[] files = fileDir.listFiles();
            for (File file : files) {
                if (file.isDirectory())
                    deleteFilesByDirectory(file);
                file.delete();
            }
            fileDir.delete();
        }
    }

    //==================================================   适配7.0  ====================================================================//

    /**
     * 获取Uri
     *
     * @param context
     * @param file
     */
    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            fileUri = getUriForFile24(context, file);
        else
            fileUri = Uri.fromFile(file);
        return fileUri;

    }

    /**
     * 获取Uri(API>=24)
     */
    public static Uri getUriForFile24(Context context, File file) {

        return FileProvider.getUriForFile(context, context.getPackageName() + ".device.CFileProvider", file);

    }

    /**
     * 设置Intent的grant权限(API>=24)
     */
    public static void setIntentDataAndType(Context context, Intent intent, String type, File file, boolean writeAble) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setDataAndType(getUriForFile(context, file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble)
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        } else
            intent.setDataAndType(Uri.fromFile(file), type);

    }


    //==================================================   适配7.0  ====================================================================//


    //==================================================  打开文件  ===================================================================//
    // android获取一个用于打开HTML文件的intent
    public static Intent getHtmlFileIntent(Context context, File file) {

        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = CFileProvider.getUriForFile(context, address, file);
            intent.setDataAndType(uri, "text/html");
        } else {
            Uri uri = Uri.parse(file.toString()).buildUpon()
                    .encodedAuthority("com.android.htmlfileprovider")
                    .scheme("content").encodedPath(file.toString()).build();
            intent.setDataAndType(uri, "text/html");
        }

        return intent;
    }

    // android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = CFileProvider.getUriForFile(context, address, file);
            intent.setDataAndType(uri, "image/*");
        } else {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "image/*");
        }

        return intent;
    }

    // android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = CFileProvider.getUriForFile(context, address, file);
            intent.setDataAndType(uri, "application/pdf");
        } else {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
        }

        return intent;
    }

    // android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = CFileProvider.getUriForFile(context, address, file);
            intent.setDataAndType(uri, "text/plain");
        } else {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "text/plain");
        }

        return intent;
    }

    // android获取一个用于打开音频文件的intent
    public static Intent getAudioFileIntent(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = CFileProvider.getUriForFile(context, address, file);
            intent.setDataAndType(uri, "audio/*");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "audio/*");
        }

        return intent;
    }

    // android获取一个用于打开视频文件的intent
    public static Intent getVideoFileIntent(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = CFileProvider.getUriForFile(context, address, file);
            intent.setDataAndType(uri, "video/*");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "video/*");
        }

        return intent;
    }

    // android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = CFileProvider.getUriForFile(context, address, file);
            intent.setDataAndType(uri, "application/x-chm");
        } else {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/x-chm");
        }

        return intent;
    }

    // android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = CFileProvider.getUriForFile(context, address, file);
            intent.setDataAndType(uri, "application/msword");
        } else {


            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/msword");
        }


        return intent;
    }

    // android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = CFileProvider.getUriForFile(context, address, file);
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        }

        return intent;
    }

    // android获取一个用于打开PPT文件的intent
    public static Intent getPPTFileIntent(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = CFileProvider.getUriForFile(context, address, file);
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        }

        return intent;
    }

    // android获取一个用于打开apk文件的intent
    public static Intent getApkFileIntent(Context context, File file) {
        Intent intent = new Intent();

        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = CFileProvider.getUriForFile(context, address, file);
            intent.setDataAndType(uri,
                    "application/vnd.android.package-archive");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }

        return intent;
    }
    //==================================================  打开文件  ===================================================================//

}
