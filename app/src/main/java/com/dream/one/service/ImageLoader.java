package com.dream.one.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * Created by muli on 15/11/29.
 */
public class ImageLoader {

    public static Bitmap getImageByAsync(ImageView imageView,String url){

        Bitmap bitmap = BitmapFactory.decodeStream(null);

        return bitmap;

    }



}
