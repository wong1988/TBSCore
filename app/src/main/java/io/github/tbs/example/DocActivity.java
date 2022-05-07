package io.github.tbs.example;

import android.os.Bundle;
import android.os.Environment;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.github.wong1988.tbs.TbsReader;

public class DocActivity extends AppCompatActivity {

    private TbsReaderView tbsReaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.layout);

        String copyUrl = new File(getExternalFilesDir(null).getAbsolutePath() + File.separator + "test.docx").getAbsolutePath();

//        String absolutePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/TestDoc.doc";

        try {
            copyFile(getAssets().open("测试.docx"), copyUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tbsReaderView = TbsReader.preOpen(this, copyUrl);

        if (tbsReaderView == null)
            Toast.makeText(this, "请使用第三方打开", Toast.LENGTH_SHORT).show();
        else
            frameLayout.addView(tbsReaderView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TbsReader.stopTbsReader(tbsReaderView);
    }

    public static void copyFile(InputStream in, String newPath) {
        try {
            FileOutputStream fs = new FileOutputStream(newPath);

            int byteread;
            byte[] buffer = new byte[4096];
            while ((byteread = in.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
                fs.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}