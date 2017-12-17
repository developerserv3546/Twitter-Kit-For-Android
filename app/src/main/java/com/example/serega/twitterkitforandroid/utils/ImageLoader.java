package com.example.serega.twitterkitforandroid.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ImageLoader {

    private static final String NORMAL = "_normal";
    private static final String BIGGER = "_bigger";

    public static void setProfileBanner(final LinearLayout layout, String profileBannerUrl) {

        if (profileBannerUrl != null && !profileBannerUrl.isEmpty()) {

            final Context context = layout.getContext();

            Picasso.with(context)
                    .load(profileBannerUrl)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            if (bitmap != null) {
                                try {
                                    layout.setBackground(
                                            new BitmapDrawable(context.getResources(), bitmap));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });
        }

    }

    public static void setProfileImage(ImageView imageView, String profileImageUrl) {

        if (profileImageUrl != null && !profileImageUrl.isEmpty()) {

            profileImageUrl = profileImageUrl.replace(NORMAL, BIGGER);

            Picasso.with(imageView.getContext())
                    .load(profileImageUrl)
                    .into(imageView);
        }
    }
}
