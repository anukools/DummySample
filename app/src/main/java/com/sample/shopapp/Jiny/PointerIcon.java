package com.sample.shopapp.Jiny;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sample.shopapp.R;


/**
 * Created by Anukool Srivastav on 4/29/2017.
 */

public class PointerIcon {

    private String TAG = this.getClass().getSimpleName();


    private WindowManager windowManager;
    private Context context;
    private LayoutInflater inflater;

    private WindowManager.LayoutParams pointerLayoutParams;
    private WindowManager.LayoutParams paymentLayoutParams;


    // dimensions
    private int totalScreenWidth;
    private int totalScreenHeight;
    private int statusBarHeight;

    private ImageView pointerView;
    private LinearLayout paymentView;

    public PointerIcon(WindowManager windowManager, Context context, LayoutInflater inflater) {
        this.windowManager = windowManager;
        this.context = context;
        this.inflater = inflater;

        totalScreenWidth = AppUtils.getScreenWidth(context);
        totalScreenHeight = AppUtils.getScreenHeight(context);
        statusBarHeight = AppUtils.getStatusBarHeight(context);

        try {
            this.createPointerView();
            this.createPaymentView();
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

        pointerLayoutParams = new WindowManager.LayoutParams(
                100,
                100,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                PixelFormat.TRANSLUCENT);
        pointerLayoutParams.gravity = Gravity.TOP | Gravity.END;
        pointerLayoutParams.x = 0;
        pointerLayoutParams.y = totalScreenHeight / 2 - 150;
        pointerLayoutParams.dimAmount = 0.6f;
        pointerLayoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        windowManager.addView(pointerView, pointerLayoutParams);

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
                        pointerLayoutParams.gravity = gravity;
                        pointerLayoutParams.x = Math.round(xCord);
                        pointerLayoutParams.y = Math.round(yCord - statusBarHeight);

                        windowManager.updateViewLayout(pointerView, pointerLayoutParams);
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
        pointerLayoutParams.x = Math.round(x);
        pointerLayoutParams.y = Math.round(y);

        windowManager.updateViewLayout(pointerView, pointerLayoutParams);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void removePointer() {
        if (pointerView != null && pointerView.isAttachedToWindow())
            windowManager.removeView(pointerView);
        if (paymentView != null && paymentView.isAttachedToWindow())
            paymentView.removeView(pointerView);
    }

    public void createPaymentView() {
        paymentView = (LinearLayout) inflater.inflate(R.layout.payment_branch_layout, null, false);
        paymentView.setVisibility(View.GONE);
        ImageView cardOption = (ImageView) paymentView.findViewById(R.id.payment_method_card);
        ImageView codOption = (ImageView) paymentView.findViewById(R.id.payment_method_cod);

        cardOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidePaymentView();
            }
        });

        codOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidePaymentView();
            }
        });


        paymentLayoutParams = new WindowManager.LayoutParams(
                windowManager.getDefaultDisplay().getWidth(),
                windowManager.getDefaultDisplay().getHeight(),
                0,
                0,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                PixelFormat.TRANSLUCENT);

        paymentLayoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_DIM_BEHIND
                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;

        paymentLayoutParams.width = totalScreenWidth;
        paymentLayoutParams.height = totalScreenHeight - statusBarHeight;

        paymentLayoutParams.x = 0;
        paymentLayoutParams.y = statusBarHeight;
        paymentLayoutParams.gravity = Gravity.TOP | Gravity.START;
        paymentLayoutParams.dimAmount = 0.4f;

        paymentLayoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        windowManager.addView(paymentView, paymentLayoutParams);
    }

    public void hidePaymentView() {
        if (paymentView != null) {
            if (paymentView.getVisibility() == View.VISIBLE) {
                paymentView.setVisibility(View.GONE);
            }
        }
    }

    public void showPaymentView() {
        try {
            if (paymentView != null) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        paymentView.setVisibility(View.VISIBLE);

                        paymentView.bringToFront();

                    }
                }, 300);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
