package io.github.wong1988.tbs;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.tencent.smtt.sdk.TbsReaderView;

public class TbsReader {

    /**
     * @param localPath 本地文件地址
     * @return =null说明不可加载 !=null 添加到布局里即可
     */
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
}
