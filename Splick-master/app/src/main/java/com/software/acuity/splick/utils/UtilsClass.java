package com.software.acuity.splick.utils;

import android.content.Context;
import android.text.Html;
import android.widget.ImageView;

import com.software.acuity.splick.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UtilsClass {

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static void setImageUsingPicasso(Context context, String imagePath, ImageView imageView) {
        if (!imagePath.isEmpty()) {
            Picasso.with(context).load(imagePath + "")
                    //download URL
                    .placeholder(R.drawable.app_icon)//use defaul image//if failed
                    .into(imageView);
        }
    }

    public static String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }
    }
}
