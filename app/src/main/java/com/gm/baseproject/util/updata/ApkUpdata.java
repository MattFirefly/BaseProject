package com.gm.baseproject.util.updata;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.gm.baseproject.util.preference.PreferenceConstants;
import com.gm.baseproject.util.preference.PreferenceUtils;

import static android.app.DownloadManager.STATUS_FAILED;
import static android.app.DownloadManager.STATUS_PAUSED;
import static android.app.DownloadManager.STATUS_PENDING;
import static android.app.DownloadManager.STATUS_RUNNING;
import static android.app.DownloadManager.STATUS_SUCCESSFUL;

/**
 * 项目名称：intelligent-warehouse-rfidhandler-inventroy
 * 类描述：
 * 创建人：zhanggangmin
 * 创建时间：2017/5/7 19:50
 * 修改人：zhanggangmin
 * 修改时间：2017/5/7 19:50
 * 修改备注：
 */
public class ApkUpdata {

    private static ApkUpdata instance;
    private Context context;
    private DownloadManager downloadManager;
    private long mReference = 0;

    private ApkUpdata(Context context) {
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        this.context = context.getApplicationContext();
    }

    public static ApkUpdata getInstance(Context context) {
        if (instance == null) {
            instance = new ApkUpdata(context);
        }
        return instance;
    }

    public void start(String url, String name) {
        Request request = new Request(Uri.parse(url));
        //下载网络需求  手机数据流量、wifi
        request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);
        //设置是否允许漫游网络 建立请求 默认true
        request.setAllowedOverRoaming(true);
        //设置通知类型
        setNotification(request,name,"");
        //设置下载路径
        setDownloadFilePath(request, name);

        /*在默认的情况下，通过Download Manager下载的文件是不能被Media Scanner扫描到的 。
        进而这些下载的文件（音乐、视频等）就不会在Gallery 和  Music Player这样的应用中看到。
        为了让下载的音乐文件可以被其他应用扫描到，我们需要调用Request对象的
         */
        request.allowScanningByMediaScanner();

        /*如果我们希望下载的文件可以被系统的Downloads应用扫描到并管理，
        我们需要调用Request对象的setVisibleInDownloadsUi方法，传递参数true。*/
        request.setVisibleInDownloadsUi(true);

        //设置请求的Mime
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        //request.setMimeType(mimeTypeMap.getMimeTypeFromExtension(url));
        request.setMimeType("application/vnd.android.package-archive");
        //开始下载
        mReference = downloadManager.enqueue(request);
        PreferenceUtils.setPrefLong(context, PreferenceConstants.DOWNLOADID, mReference);
    }

    /**
     * 下载管理器中有很多下载项，怎么知道一个资源已经下载过，避免重复下载呢？
     * 我的项目中的需求就是apk更新下载，用户点击更新确定按钮，第一次是直接下载，
     * 后面如果用户连续点击更新确定按钮，就不要重复下载了。
     * 可以看出来查询和操作数据库查询一样的
     */
    public boolean isExist() {
        Query query = new Query();
        query.setFilterById(mReference);
        Cursor cursor = downloadManager.query(query);
        return cursor.moveToFirst();
//        if (!cursor.moveToFirst()) {// 没有记录
//
//        } else {
//            //有记录
//        }
    }

    /**
     * 设置状态栏中显示Notification
     */
    void setNotification(Request request, String tiltle, String description) {
        //设置Notification的标题
        request.setTitle(tiltle);

        //设置描述
        request.setDescription(description);

        //request.setNotificationVisibility( Request.VISIBILITY_VISIBLE ) ;

        request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //request.setNotificationVisibility( Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION ) ;

        //request.setNotificationVisibility( Request.VISIBILITY_HIDDEN ) ;
    }

    /**
     * 设置下载文件存储目录
     */
    void setDownloadFilePath(Request request, String appname) {
        /**
         * 方法1:
         * 目录: Android -> data -> com.app -> files -> Download -> 微信.apk
         * 这个文件是你的应用所专用的,软件卸载后，下载的文件将随着卸载全部被删除
         */

        //request.setDestinationInExternalFilesDir( this , Environment.DIRECTORY_DOWNLOADS ,  "微信.apk" );

        /**
         * 方法2:
         * 下载的文件存放地址  SD卡 download文件夹，pp.jpg
         * 软件卸载后，下载的文件会保留
         */
        //在SD卡上创建一个文件夹
        //request.setDestinationInExternalPublicDir(  "/mydownfile/"  , "weixin.apk" ) ;


        /**
         * 方法3:
         * 如果下载的文件希望被其他的应用共享
         * 特别是那些你下载下来希望被Media Scanner扫描到的文件（比如音乐文件）
         */
        //request.setDestinationInExternalPublicDir( Environment.DIRECTORY_MUSIC,  "笨小孩.mp3" );

        /**
         * 方法4
         * 文件将存放在外部存储的确实download文件内，如果无此文件夹，创建之，如果有，下面将返回false。
         * 系统有个下载文件夹，比如小米手机系统下载文件夹  SD卡--> Download文件夹
         */
        //创建目录
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();

        //设置文件存放路径
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, appname);
    }

    public void cancle() {
        //取消下载， 如果一个下载被取消了，所有相关联的文件，部分下载的文件和完全下载的文件都会被删除。
        downloadManager.remove(mReference);
    }


    /**
     * 查看下载状态
     */
    public void look() {
        Query query = new Query();
        query.setFilterById(mReference);
        Cursor cursor = downloadManager.query(query);

        if (cursor == null) {
            Toast.makeText(context, "Download not found!", Toast.LENGTH_LONG).show();
        } else {  //以下是从游标中进行信息提取
            cursor.moveToFirst();
            String msg = statusMessage(cursor);
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 查询状态
     *
     * @param c
     * @return
     */
    private String statusMessage(Cursor c) {
        switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case STATUS_FAILED:
                return "Download failed";
            case STATUS_PAUSED:
                return "Download paused";
            case STATUS_PENDING:
                return "Download pending";
            case STATUS_RUNNING:
                return "Download in progress!";
            case STATUS_SUCCESSFUL:
                return "Download finished";
            default:
                return "Unknown Information";
        }
    }

    /**
     * 获取下载状态
     *
     * @param downloadId an ID for the download, unique across the system.
     *                   This ID is used to make future calls related to this download.
     * @return int
     * @see DownloadManager#STATUS_PENDING
     * @see DownloadManager#STATUS_PAUSED
     * @see DownloadManager#STATUS_RUNNING
     * @see DownloadManager#STATUS_SUCCESSFUL
     * @see DownloadManager#STATUS_FAILED
     */
    public int getDownloadStatus(long downloadId) {
        Query query = new Query().setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));

                }
            } finally {
                c.close();
            }
        }
        return -1;
    }


    /**
     * @param downloadId
     * @return 下载文件的url
     */
    public Uri getDownloadUri(long downloadId) {
        return downloadManager.getUriForDownloadedFile(downloadId);
    }


    public DownloadManager getDownloadManager() {
        return downloadManager;
    }
}
