package com.sample.shopapp.Jiny;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringSystemListener;
import com.sample.shopapp.R;

/**
 * Created by Anukool Srivastav on 5/10/2017.
 */

public class JinyIconView implements View.OnTouchListener {
    private final JinyIcon jinyIcon;
    private final WindowManager windowManager;
    private final Context context;

    private View JinyViewIcon;
    private Point homeScreenSize;
    private int jinyIconRadius, statusBarHeight;

    private WindowManager.LayoutParams jinyParams;
    private SpringSystem system;
    private SpringConfig CONVERGING = SpringConfig.fromOrigamiTensionAndFriction(20, 3);
    private SpringConfig PEACE = SpringConfig.fromOrigamiTensionAndFriction(0, 100);
    private SpringConfig SUPERFAST = SpringConfig.fromOrigamiTensionAndFriction(100, 4);
    private SpringConfig COASTING = SpringConfig.fromOrigamiTensionAndFriction(0, 0.5);
    private Spring jinyIconSpringX, jinyIconSpringY, jinyIconMoveSpring;

    public JinyIconView(JinyIcon jinyIcon1, WindowManager windowManager, Context context) {
        this.jinyIcon = jinyIcon1;
        this.windowManager = windowManager;
        this.context = context;
        jinyParams = (WindowManager.LayoutParams) jinyIcon1.getView().getLayoutParams();
        JinyViewIcon = jinyIcon1.getView().findViewById(R.id.jinyView);

        homeScreenSize = new Point();
        homeScreenSize.x = AppUtils.getScreenWidth(context);
        homeScreenSize.y = AppUtils.getScreenHeight(context);
        jinyIconRadius = AppUtils.dpToPx(context, 30);
        statusBarHeight = AppUtils.getStatusBarHeight(context);

        this.createSpring();
        JinyViewIcon.setOnTouchListener(this);
    }


    private void createSpring() {
        system = SpringSystem.create();
        system.addListener(getSpringSystemListener());

        jinyIconSpringX = system.createSpring();
        jinyIconSpringY = system.createSpring();
        jinyIconSpringX.setCurrentValue(jinyParams.x);
        jinyIconSpringY.setCurrentValue(jinyParams.y);
        jinyIconSpringY.setVelocity(0);
        jinyIconSpringX.setVelocity(0);
        SpringListener springListener = new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                jinyParams.x = (int) (jinyIconSpringX.getCurrentValue());
                jinyParams.y = (int) (jinyIconSpringY.getCurrentValue());
                moveJinyIcon(0, 0);
            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        };
        jinyIconSpringX.addListener(springListener);
        jinyIconSpringY.addListener(springListener);

        // JinyIconTouch Spring
        jinyIconMoveSpring = system.createSpring();
        jinyIconMoveSpring.setCurrentValue(1.0);
        jinyIconMoveSpring.setSpringConfig(new SpringConfig(200, 20));
    }

    private SpringSystemListener getSpringSystemListener() {
        return new SpringSystemListener() {
            @Override
            public void onBeforeIntegrate(BaseSpringSystem springSystem) {

            }

            @Override
            public void onAfterIntegrate(BaseSpringSystem springSystem) {
                checkSnap();
            }
        };
    }

    private void checkSnap() {
        if (jinyParams.x >= homeScreenSize.x - jinyIconRadius) {
            jinyIconSpringX.setSpringConfig(CONVERGING);
            jinyIconSpringX.setEndValue(homeScreenSize.x - 2 * jinyIconRadius);
            jinyIconSpringY.setVelocity(0);
        }
        if (jinyParams.x <= 0) {
            jinyIconSpringX.setSpringConfig(CONVERGING);
            jinyIconSpringX.setEndValue(0);
            jinyIconSpringY.setVelocity(0);
        }
        if (jinyParams.y >= homeScreenSize.y - jinyIconRadius) {
            jinyIconSpringY.setSpringConfig(CONVERGING);
            jinyIconSpringY.setEndValue(homeScreenSize.y - (jinyIconRadius * 2));
            jinyIconSpringX.setVelocity(0);
        }
        if (jinyParams.y <= statusBarHeight) {
            jinyIconSpringY.setSpringConfig(CONVERGING);
            jinyIconSpringY.setEndValue(statusBarHeight);
            jinyIconSpringX.setVelocity(0);
        }

        if (exitButtonParamsY > 0 && closeIconRadius > 0
                && jinyIconRadius > 0 && jinyParams != null
                && AppUtils.dist(jinyParams.x, jinyParams.y, exitButtonParamsX, exitButtonParamsY) < attractionThreshold) {
            jinyIconSpringX.setSpringConfig(SUPERFAST);
            jinyIconSpringX.setEndValue(exitButtonParamsX);
            jinyIconSpringY.setSpringConfig(SUPERFAST);
            jinyIconSpringY.setEndValue(exitButtonParamsY + closeIconRadius - jinyIconRadius);
            if (!chatHeadAboutToClose) {
                chatHeadAboutToClose = true;
                Handler handler = new Handler();
                final Runnable r = new Runnable() {
                    public void run() {
                        jinyIconSpringX.setAtRest();
                        jinyIconSpringY.setAtRest();
                        if (isTouchReleased) {
                            hideExitButton();
                            jinyIcon.hide();
                        } else {
                            if (jinyIconSpringX.getEndValue() == exitButtonParamsX
                                    && jinyIconSpringY.getEndValue() == (exitButtonParamsY + closeIconRadius - jinyIconRadius)) {
                                hideExitButton();
                                jinyIcon.hide();
                            }
                        }
                    }
                };
                handler.postDelayed(r, 500);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void moveJinyIcon(float offsetX, float offsetY) {
        try {
            if(jinyIcon.getView().isAttachedToWindow()){
                jinyParams.x -= offsetX;
                jinyParams.y -= offsetY;
                windowManager.updateViewLayout(jinyIcon.getView(), jinyParams);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void moveJinyIconDirectly(float positionX, float positionY) {
        jinyIconSpringX.setCurrentValue(positionX).setAtRest();
        jinyIconSpringY.setCurrentValue(positionY).setAtRest();
    }

    public void moveJinyIconDirectlyPeacefully(float positionX, float positionY) {
        jinyIconSpringX.setVelocity(1);
        jinyIconSpringX.setSpringConfig(CONVERGING);
        jinyIconSpringX.setCurrentValue(positionX).setAtRest();
        jinyIconSpringY.setVelocity(1);
        jinyIconSpringY.setSpringConfig(CONVERGING);
        jinyIconSpringY.setCurrentValue(positionY).setAtRest();
    }


    private float downX;
    private float downY;
    private float lastX;
    private float lastY;
    private VelocityTracker velocityTracker;
    private boolean isDragging = false;
    private boolean isTouchReleased = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        try {
            float touchX = event.getRawX();
            float touchY = event.getRawY();
            boolean ret = false;
            isTouchReleased = false;
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    downX = touchX;
                    downY = touchY;
                    lastX = downX;
                    lastY = downY;
                    chatheadTouched();
                    velocityTracker = VelocityTracker.obtain();
                    addToVelocityTracker(velocityTracker, event);
                    isDragging = false;
                    ret = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float offsetX = lastX - touchX;
                    float offsetY = lastY - touchY;
                    if (Math.abs(touchX - downX) < 15 && Math.abs(touchY - downY) < 15) {
                        ret = true;
                        break;
                    }
                    if (!isDragging) JinyIconMoving();
                    isDragging = true;

                    if ((jinyIconSpringX.getEndValue() == exitButtonParamsX
                            && jinyIconSpringY.getEndValue() == (exitButtonParamsY + closeIconRadius - jinyIconRadius))) {
                        if (AppUtils.dist(exitButtonParamsX, exitButtonParamsY, lastX, lastY) > attractionThreshold) {
                            moveJinyIconDirectlyPeacefully(touchX - (AppUtils.dpToPx(context, 50) / 2), touchY - (AppUtils.dpToPx(context, 50) / 2));
                            moveJinyIconWithAnimation(offsetX, offsetY);
                            addToVelocityTracker(velocityTracker, event);
                        }
                    } else {
                        moveJinyIconWithAnimation(offsetX, offsetY);
                        addToVelocityTracker(velocityTracker, event);
                    }
                    ret = true;

                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (!isDragging && Math.abs(touchX - lastX) < 5 && Math.abs(touchY - lastY) < 5) {
                        v.performClick();
                    }
                    downX = 0;
                    downY = 0;
                    addToVelocityTracker(velocityTracker, event);
                    setJinyIconSpringMovement(velocityTracker);
                    JinyIconReleased();
                    ret = true;
            }
            lastX = touchX;
            lastY = touchY;

            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void JinyIconReleased() {
        isTouchReleased = true;
        JinyViewIcon.setScaleX(1.0f);
        JinyViewIcon.setScaleY(1.0f);
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                hideExitButton();
            }
        };
        handler.postDelayed(r, 500);

        if (Math.abs(jinyParams.x - exitButtonParamsX) < 50
                && Math.abs(jinyParams.y - (exitButtonParamsY + closeIconRadius - jinyIconRadius)) < 50) {
            hideExitButton();
            jinyIcon.hide();
        }
    }

    public void setJinyIconSpringMovement(VelocityTracker velocityTracker) {
        velocityTracker.computeCurrentVelocity(500);
        jinyIconSpringX.setSpringConfig(COASTING);
        jinyIconSpringY.setSpringConfig(COASTING);
        float xVelocity = velocityTracker.getXVelocity();
        float yVelocity = velocityTracker.getYVelocity();
        jinyIconSpringX.setVelocity(xVelocity);
        jinyIconSpringY.setVelocity(yVelocity);
        velocityTracker.clear();
    }

    public void addToVelocityTracker(VelocityTracker velocityTracker, MotionEvent event) {
        event.offsetLocation((float) jinyIconSpringX.getCurrentValue(), (float) jinyIconSpringY.getCurrentValue());
        velocityTracker.addMovement(event);
    }

    public void moveJinyIconWithAnimation(float offsetX, float offsetY) {
        jinyIconSpringX.setCurrentValue(jinyIconSpringX.getCurrentValue() - offsetX).setAtRest();
        jinyIconSpringY.setCurrentValue(jinyIconSpringY.getCurrentValue() - offsetY).setAtRest();
    }


    private float attractionThreshold = 200;


    LinearLayout exitButton;
    LinearLayout exitButtonLayout;
    private float JinyIconTouchedScale = 0.9f;

    public void JinyIconMoving() {
        JinyViewIcon.setScaleX(JinyIconTouchedScale);
        JinyViewIcon.setScaleY(JinyIconTouchedScale);
        showExitButton();
    }

    public void chatheadTouched() {
        chatHeadAboutToClose = false;
    }

    public void showExitButton() {
        if (exitButtonLayout != null) {
            exitButtonLayout.setVisibility(View.VISIBLE);
        } else {
            createExitButton();
        }
    }

    WindowManager.LayoutParams exitButtonLayoutParams;
    int exitButtonParamsX, exitButtonParamsY;
    private int closeIconRadius;
    private static boolean chatHeadAboutToClose = false;

    private void createExitButton() {
        try {
            exitButtonLayoutParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    PixelFormat.TRANSLUCENT);
            exitButtonLayoutParams.gravity = Gravity.TOP | Gravity.START;
            closeIconRadius = AppUtils.dpToPx(context, 30);
            exitButtonLayoutParams.x = 0;
            exitButtonLayoutParams.y = homeScreenSize.y - AppUtils.dpToPx(context, 100);
            exitButtonLayoutParams.dimAmount = 0.6f;
            exitButtonLayoutParams.width = homeScreenSize.x;
            exitButtonLayoutParams.height = AppUtils.dpToPx(context, 100);

            exitButtonParamsX = homeScreenSize.x / 2 - closeIconRadius;
            exitButtonParamsY = homeScreenSize.y - AppUtils.dpToPx(context, 50) - closeIconRadius;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            exitButtonLayout = (LinearLayout) inflater.inflate(R.layout.close_icon_layout, null, false);

            exitButton = (LinearLayout) exitButtonLayout.findViewById(R.id.close_icon);

            windowManager.addView(exitButtonLayout, exitButtonLayoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideExitButton() {
        if (exitButtonLayout != null) {
            exitButtonLayout.setVisibility(View.GONE);
        }
    }


}

