package io.github.wong1988.tbs;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;

import java.util.HashMap;

/**
 * 初始化X5
 */
public class TBSCore {

    private static TBSCore instance;

    private static Application mAppContext;
    private static boolean mDownloadWithoutWifi;


    private TBSCore() {

        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
        QbSdk.setDownloadWithoutWifi(mDownloadWithoutWifi);

        QbSdk.setTbsListener(
                new TbsListener() {
                    @Override
                    public void onDownloadFinish(int progress) {
                        Log.e("QbSdk", "onDownloadFinish -->下载X5内核完成：" + progress);
                    }

                    @Override
                    public void onInstallFinish(int progress) {
                        Log.e("QbSdk", "onInstallFinish -->安装X5内核进度：" + progress);
                    }

                    @Override
                    public void onDownloadProgress(int progress) {
                        Log.e("QbSdk", "onDownloadProgress -->下载X5内核进度：" + progress);
                    }
                });

        QbSdk.initX5Environment(mAppContext, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                // 内核初始化完成，可能为系统内核，也可能为系统内核
            }

            /**
             * 预初始化结束
             * 由于X5内核体积较大，需要依赖网络动态下发，所以当内核不存在的时候，默认会回调false，此时将会使用系统内核代替
             * @param isX5 是否使用X5内核，只有true可以加载TbsReaderView
             */
            @Override
            public void onViewInitFinished(boolean isX5) {

                if (!isX5)
                    // 如果是false，需要重新下载内核
                    // 手动解决24小时候才会重新下载的问题
                    TbsDownloader.startDownload(mAppContext);

            }
        });

    }

    /**
     * @param downloadWithoutWifi 无WIFI是否可以下载内核
     */
    public static void init(Application appContext, boolean downloadWithoutWifi) {
        if (instance == null)
            synchronized (TBSCore.class) {
                if (instance == null) {

                    if (mAppContext == null)
                        throw new RuntimeException("appContext is null");

                    mAppContext = appContext;
                    mDownloadWithoutWifi = downloadWithoutWifi;
                    instance = new TBSCore();
                }
            }
    }

}
