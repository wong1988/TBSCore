package io.github.wong1988.tbs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.RequiresPermission;

import com.tencent.smtt.sdk.TbsReaderView;
import com.tencent.smtt.utils.FileProvider;

import java.io.File;

import io.github.wong1988.tbs.utils.MediaFile;

public class TbsReader {

    /**
     * 注意：需要读取权限
     *
     * @param localPath 本地文件地址
     * @return =null说明不可加载 !=null 添加到布局里即可
     */
    @RequiresPermission(allOf = {Manifest.permission.READ_EXTERNAL_STORAGE})
    public static TbsReaderView preOpen(Context context, String localPath) {

        // 通过bundle把文件传给x5,打开的事情交由x5处理
        Bundle bundle = new Bundle();
        // 指定文件路径
        bundle.putString("filePath", localPath);
        // 指定腾讯文件缓存路径
        bundle.putString("tempPath", context.getExternalFilesDir(null).getAbsolutePath());
        TbsReaderView readerView = new TbsReaderView(context, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {

            }
        });

        //加载文件前的初始化工作,加载支持不同格式的插件
        boolean b = readerView.preOpen(parseFileType(localPath), false);
        if (b) {
            readerView.openFile(bundle);
            return readerView;
        }

        readerView = null;

        return null;
    }

    private static String parseFileType(String path) {
        if (TextUtils.isEmpty(path))
            return "";
        if (!path.contains("."))
            return "";
        return path.substring(path.lastIndexOf(".") + 1);
    }

    /**
     * 加载下个文件一定要停用上一个tbsReaderView
     */
    public static void stopTbsReader(TbsReaderView tbsReaderView) {
        if (tbsReaderView != null)
            tbsReaderView.onStop();
    }

    /**
     * 注意：需要读取权限
     * 使用第三方App打开文件
     */
    @RequiresPermission(allOf = {Manifest.permission.READ_EXTERNAL_STORAGE})
    public static void openByThirdPartyApp(String localPath) {

        String mimeTypeForFile = MediaFile.getMimeTypeForFile(localPath);

        if (mimeTypeForFile == null)
            mimeTypeForFile = "*/*";


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= 24) {
            Uri uri = FileProvider.a(TBSCore.getAppContext(), "io.github.wong1988.tbs.provider", new File(localPath));
            intent.setDataAndType(uri, mimeTypeForFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            intent.setDataAndType(Uri.fromFile(new File(localPath)), mimeTypeForFile);
        }

        TBSCore.getAppContext().startActivity(intent);

    }
}
