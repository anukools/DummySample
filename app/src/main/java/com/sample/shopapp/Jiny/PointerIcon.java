package com.sample.shopapp.Jiny;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sample.shopapp.R;


/**
 * Created by Anukool Srivastav on 4/29/2017.
 */

public class PointerIcon {

    private String TAG = this.getClass().getSimpleName();


    private WindowManager windowManager;
    private Context context;
    private LayoutInflater inflater;

    private WindowManager.LayoutParams layoutParams;


    // dimensions
    private int totalScreenWidth;
    private int totalScreenHeight;
    private int statusBarHeight;

    private ImageView pointerView;

    public PointerIcon(WindowManager windowManager, Context context, LayoutInflater inflater) {
        this.windowManager = windowManager;
        this.context = context;
        this.inflater = inflater;

        totalScreenWidth = AppUtils.getScreenWidth(context);
        totalScreenHeight = AppUtils.getScreenHeight(context);
        statusBarHeight = AppUtils.getStatusBarHeight(context);

        try {
            this.createPointerView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPointerView() {
        pointerView = (ImageView) inflater.inflate(R.layout.dummy_layout, null, false);
        pointerView.setBackgroundResource(R.drawable.pointer_animation);
        pointerView.setVisibility(View.GONE);
        AnimationDrawable animationDrawable = (AnimationDrawable) pointerView.getBackground();
        animationDrawable.start();
        animationDrawable.setOneShot(false);

        layoutParams = new WindowManager.LayoutParams(
                100,
                100,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                PixelFormat.TRANSLUCENT);
        layoutParams.gravity = Gravity.TOP | Gravity.END;
        layoutParams.x = 0;
        layoutParams.y = totalScreenHeight / 2 - 150;
        layoutParams.dimAmount = 0.6f;
        layoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        windowManager.addView(pointerView, layoutParams);

    }


    public void hide() {
        if (pointerView != null) {
            if (pointerView.getVisibility() == View.VISIBLE) {
                Log.e("Pointer :", "hide");
                pointerView.setVisibility(View.GONE);
            }
        }
    }

    public void show(final float xCord, final float yCord, final int gravity) {
        try {
            if (pointerView != null) {
                Log.e("Pointer :", "Show");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        pointerView.setVisibility(View.VISIBLE);
                        layoutParams.gravity = gravity;
                        layoutParams.x = Math.round(xCord);
                        layoutParams.y = Math.round(yCord);

                        windowManager.updateViewLayout(pointerView, layoutParams);
                    }
                }, 200);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animateView(int x, int y) {
        if (pointerView.getVisibility() == View.GONE) {
            pointerView.setVisibility(View.VISIBLE);
        }
        layoutParams.x = Math.round(x);
        layoutParams.y = Math.round(y);

        windowManager.updateViewLayout(pointerView, layoutParams);

    }

    public void removePointer() {
        if (pointerView != null)
            windowManager.removeView(pointerView);
    }
}
