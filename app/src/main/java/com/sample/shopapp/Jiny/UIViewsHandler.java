package com.sample.shopapp.Jiny;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.sample.shopapp.R;

/**
 * Created by Anukool Srivastav on 4/29/2017.
 */

public class UIViewsHandler {
    private static String TAG = "UIViewsHandler";
    private static int maxHeight = 0;

    public static void handleProductListPageView(Context context, View view) {
        // get home recycler view
        View recyclerView = view.findViewById(R.id.fragment_product_list_recycler_view);
        if (recyclerView != null) {
            Log.d(TAG, "RECYCLER VIEW FOUND");
            // post event bus to show pointer
            BusEvents.ShowUIEvent event = new BusEvents.ShowUIEvent();
            event.setX(50);
            event.setY(AppUtils.getScreenHeight(context) / 2 - 100);
            event.setGravity(Gravity.TOP | Gravity.END);
            event.setSoundResId(R.raw.search_from_list);
            PointerService.bus.post(event);

        }
    }

    public static void handleProductDetailPageView(Context context, View view) {
        // get home recycler view
        View cartButton = view.findViewById(R.id.add_to_cart);
        if (cartButton != null) {
            Rect rect = new Rect();
            cartButton.getGlobalVisibleRect(rect);

            Log.e("cartButton CoOrd : ", rect.exactCenterX() + " - " + rect.exactCenterY());

            BusEvents.ShowUIEvent event = new BusEvents.ShowUIEvent();
            event.setX(rect.exactCenterX());
            event.setY(rect.exactCenterY());
            event.setGravity(Gravity.TOP | Gravity.START);
            event.setSoundResId(R.raw.checkout);
            PointerService.bus.post(event);
        }
    }

    public static void sendViewAnimateEvent(Context context, View view) {
        BusEvents.AnimateEvent animateEvent = new BusEvents.AnimateEvent();
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);

        int heightDiff = (int) (maxHeight - rect.exactCenterY());

        animateEvent.setX((int) ((AppUtils.getScreenWidth(context) - rect.exactCenterX()) / 2));
        animateEvent.setY((int) ((AppUtils.getScreenHeight(context) - rect.exactCenterY()) / 2) - heightDiff);
        PointerService.bus.post(animateEvent);
    }

    public static void sendViewEvent(Context context, View view) {
//        if (view.getVisibility() == View.VISIBLE) {

        Log.e("Pointer : ", "sendViewEvent");
        // post event bus to show pointer
        BusEvents.ShowUIEvent event = new BusEvents.ShowUIEvent();

        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);

        maxHeight = (int) rect.exactCenterY();
//            event.setX((AppUtils.getScreenWidth(context) - view.getX()) / 2);
//            event.setY((AppUtils.getScreenHeight(context) - view.getY()) / 2);

        event.setX(AppUtils.getScreenWidth(context) / 2);
        event.setY(AppUtils.getScreenHeight(context) / 2);
        event.setSoundResId(R.raw.search);
        event.setGravity(Gravity.TOP | Gravity.END);
        PointerService.bus.post(event);
//        }
    }



    public static void handleSignInPageViews(Context context, View view) {
        View emailView =  view.findViewById(R.id.profile_email_txt);
        Rect rect = new Rect();
        emailView.getGlobalVisibleRect(rect);

        Log.e("Email View CoOrd : ", rect.exactCenterX() + " - " + rect.exactCenterY());

        BusEvents.ShowUIEvent event = new BusEvents.ShowUIEvent();
        event.setX(rect.exactCenterX());
        event.setY(rect.exactCenterY());
        event.setSoundResId(R.raw.search);
        event.setGravity(Gravity.TOP | Gravity.START);
        PointerService.bus.post(event);
    }

    public static void handleSignInPageViewClicks(Context context, View view) {
        if(view != null){
            Rect rect = new Rect();
            view.getGlobalVisibleRect(rect);

            Log.e("Email View CoOrd : ", rect.exactCenterX() + " - " + rect.exactCenterY());

            BusEvents.ShowUIEvent event = new BusEvents.ShowUIEvent();
            event.setX(rect.exactCenterX());
            event.setY(rect.exactCenterY());
            event.setSoundResId(R.raw.search);
            event.setGravity(Gravity.TOP | Gravity.START);
            PointerService.bus.post(event);
        }
    }

    public static void handlePageViewFocusChanges(Context context, View view) {
        if(view != null){
            Rect rect = new Rect();
            view.getGlobalVisibleRect(rect);

            Log.e("Email View CoOrd : ", rect.exactCenterX() + " - " + rect.exactCenterY());

            BusEvents.ShowUIEvent event = new BusEvents.ShowUIEvent();
            event.setX(rect.exactCenterX());
            event.setY(rect.exactCenterY());
            event.setSoundResId(R.raw.search);
            event.setGravity(Gravity.TOP | Gravity.START);
            PointerService.bus.post(event);
        }
    }

    public static void handleSignUpPageViews(Context context, View view) {
        View emailView =  view.findViewById(R.id.fragment_signup_email_txt);
        Rect rect = new Rect();
        emailView.getGlobalVisibleRect(rect);

        Log.e("Email View CoOrd : ", rect.exactCenterX() + " - " + rect.exactCenterY());

        BusEvents.ShowUIEvent event = new BusEvents.ShowUIEvent();
        event.setX(rect.exactCenterX());
        event.setY(rect.exactCenterY());
        event.setSoundResId(R.raw.search);
        event.setGravity(Gravity.TOP | Gravity.START);
        PointerService.bus.post(event);
    }

    public static void handleSignAddressPageViews(Context context, View view) {
        View addAddressButton =  view.findViewById(R.id.fragment_address_fab);
        if(addAddressButton.getVisibility() ==  View.VISIBLE){
            Rect rect = new Rect();
            addAddressButton.getGlobalVisibleRect(rect);

            Log.e("Email View CoOrd : ", rect.exactCenterX() + " - " + rect.exactCenterY());

            BusEvents.ShowUIEvent event = new BusEvents.ShowUIEvent();
            event.setX(rect.exactCenterX());
            event.setY(rect.exactCenterY());
            event.setSoundResId(R.raw.search);
            event.setGravity(Gravity.TOP | Gravity.START);
            PointerService.bus.post(event);
        }

    }
}
