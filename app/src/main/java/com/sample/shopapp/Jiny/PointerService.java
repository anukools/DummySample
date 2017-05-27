package com.sample.shopapp.Jiny;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;
import com.sample.shopapp.R;

/**
 * Created by Anukool Srivastav on 4/29/2017.
 */

public class PointerService extends Service {
    public static Bus bus = new Bus(ThreadEnforcer.ANY);
    private String TAG = this.getClass().toString();
    private PointerIcon pointerIcon;
    private JinyIcon jinyIcon;
    private Context context;

    private SoundPlayer soundPlayer;

    SettingsContentObserver mSettingsContentObserver;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getBaseContext(), R.style.AppTheme);
            this.context = contextThemeWrapper;

            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            pointerIcon = new PointerIcon(windowManager, context, inflater);
            jinyIcon = new JinyIcon(windowManager, context, inflater);

            // init sound player
            soundPlayer = new SoundPlayer();

            PointerService.bus.register(this);


            mSettingsContentObserver = new SettingsContentObserver(new Handler());
            context.getContentResolver().registerContentObserver(
                    android.provider.Settings.System.CONTENT_URI, true,
                    mSettingsContentObserver);

            Log.e("Pointer : ", "startUIService registered");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (startId == Service.START_STICKY) {
            return super.onStartCommand(intent, flags, startId);
        } else {
            return Service.START_NOT_STICKY;
        }
    }


    @Subscribe
    public void showPointerUIEvent(final BusEvents.ShowUIEvent event) {
        pointerIcon.show(event.getX(), event.getY(), event.getGravity());
        jinyIcon.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                soundPlayer.play(getApplicationContext(), event.getSoundResId());
            }
        }, 500);
    }

    @Subscribe
    public void hidePointerUIEvent(BusEvents.HideEvent event) {
        if (event.isHidePointer()) {
            pointerIcon.hide();
        }

        if (event.isHideJinyIcon()) {
            jinyIcon.hide();
        }
        soundPlayer.stop();
    }

    @Subscribe
    public void animatePointerUIEvent(BusEvents.AnimateEvent event) {
        pointerIcon.animateView(event.getX(), event.getY());

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Subscribe
    public void removePointerUIEvent(BusEvents.RemoveEvent event) {
        pointerIcon.removePointer();
        jinyIcon.removeJiny();
    }

    @Subscribe
    public void showPaymentBranchEvent(BusEvents.ShowPaymentEvent event){
        pointerIcon.showPaymentView();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                soundPlayer.play(getApplicationContext(), R.raw.payment_method);
            }
        }, 500);
    }

    @Subscribe
    public void hidePaymentBranchEvent(BusEvents.HidePaymentEvent event){
        pointerIcon.hidePaymentView();

        soundPlayer.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // remove views from the window
        if (pointerIcon != null) {
            pointerIcon.removePointer();
        }
        // remove views from the window
        if (jinyIcon != null) {
            jinyIcon.removeJiny();
        }

        PointerService.bus.unregister(this);

        if (mSettingsContentObserver != null)
            this.getApplicationContext().getContentResolver().unregisterContentObserver(mSettingsContentObserver);
    }

    public class SettingsContentObserver extends ContentObserver {
        private AudioManager audioManager;

        public SettingsContentObserver(Handler handler) {
            super(handler);
            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        }

        @Override
        public boolean deliverSelfNotifications() {
            return super.deliverSelfNotifications();
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            int musicVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int ringVoume = audioManager.getStreamVolume(AudioManager.MODE_RINGTONE);

            Log.d(TAG, "Volume now " + musicVolume + " --- " + ringVoume);

            if (ringVoume == 0) {

            } else if (ringVoume == 7) {

            } else if (ringVoume >= 1 && ringVoume <= 2) {

            } else if (ringVoume >= 3 && ringVoume <= 6) {

            }
        }
    }
}
