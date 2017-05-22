package com.sample.shopapp.Jiny;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Anukool Srivastav on 5/6/2017.
 */

public class SoundPlayer {
    MediaPlayer mediaPlayer;

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void play(Context c, int rid) {
        stop();

        mediaPlayer = MediaPlayer.create(c, rid);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                stop();
            }
        });

        mediaPlayer.start();
    }
}
