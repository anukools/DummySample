package com.sample.shopapp.Jiny;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Anukool Srivastav on 19/4/16.
 */
public class AppUtils {

    private static Point sScreenSize = null;

    public static Iterable<View> getChildViews(final ViewGroup viewgroup) {
        return new Iterable<View>() {
            int i = -1;

            @NonNull
            @Override
            public Iterator<View> iterator() {
                return new Iterator<View>() {
                    int i = -1;

                    @Override
                    public boolean hasNext() {
                        return i < viewgroup.getChildCount();
                    }

                    @Override
                    public View next() {
                        Log.e("Iterated View : ", viewgroup.getChildAt(++i).getResources() + " ");
                        return viewgroup.getChildAt(++i);
                    }

                    @Override
                    public void remove() {

                    }
                };
            }

        };
    }

    public static View findViewAtPosition(View parent, int x, int y) {
        if (parent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)parent;
            for (int i=0; i<viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                View viewAtPosition = findViewAtPosition(child, x, y);
                if (viewAtPosition != null) {
                    return viewAtPosition;
                }
            }
            return null;
        } else {
            Rect rect = new Rect();
            parent.getGlobalVisibleRect(rect);
            if (rect.contains(x, y)) {
                return parent;
            } else {
                return null;
            }
        }
    }

    public static void showAllChildrenViews(View v) {
        ViewGroup viewgroup = (ViewGroup) v;
        for (int i = 0; i < viewgroup.getChildCount(); i++) {
            View v1 = viewgroup.getChildAt(i);
            if (v1 instanceof ViewGroup) showAllChildrenViews(v1);
            Log.d("CHILD VIEWS", v1.toString());
        }
    }

    static int getScreenWidth(Context context) {
        fetchScreenSize(context);
        return sScreenSize.x;
    }

    static int getScreenHeight(Context context) {
        fetchScreenSize(context);
        return sScreenSize.y;
    }

    static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }

    static float dist(double posX, double posY, double pos2X, double pos2Y) {
        return (float) Math.sqrt(Math.pow(pos2X - posX, 2) + Math.pow(pos2Y - posY, 2));
    }

    private static void fetchScreenSize(Context context) {
        int softButtonsHeight = 0;
        if (sScreenSize != null) return;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Configuration configuration = context.getResources().getConfiguration();

        boolean isPortrait = true;
        int rotation = display.getRotation();

        sScreenSize = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            DisplayMetrics absoluteMetrics = new DisplayMetrics();
            display.getRealMetrics(absoluteMetrics);
            softButtonsHeight = absoluteMetrics.heightPixels - metrics.heightPixels;
            sScreenSize.x = metrics.widthPixels;
            sScreenSize.y = metrics.heightPixels;
        } else {
            display.getSize(sScreenSize);
        }

        if (sScreenSize.x > sScreenSize.y) isPortrait = false;
        if (!isPortrait) {
            int tempX = sScreenSize.x;
            int tempY = sScreenSize.y;
            sScreenSize.x = tempY + softButtonsHeight;
            sScreenSize.y = tempX - softButtonsHeight;
        }

    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    static SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-mm-dd");
    static SimpleDateFormat writeFormat = new SimpleDateFormat("MMM dd, yyyy");

    public static String formateDate(String dateFormat) {
        Date date = null;
        try {
            date = readFormat.parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return writeFormat.format(date);
    }

}
