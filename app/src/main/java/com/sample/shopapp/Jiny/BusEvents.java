package com.sample.shopapp.Jiny;

/**
 * Created by Anukool Srivastav on 4/29/2017.
 */

public class BusEvents {

    public static class HideEvent {
        boolean hideJinyIcon = true;
        boolean hidePointer = true;

        public boolean isHideJinyIcon() {
            return hideJinyIcon;
        }

        public void setHideJinyIcon(boolean hideJinyIcon) {
            this.hideJinyIcon = hideJinyIcon;
        }

        public boolean isHidePointer() {
            return hidePointer;
        }

        public void setHidePointer(boolean hidePointer) {
            this.hidePointer = hidePointer;
        }
    }

    public static class RemoveEvent {

    }

    public static class ShowPaymentEvent {

    }

    public static class HidePaymentEvent {

    }

    public static class AnimateEvent {
        int x;
        int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    public static class ShowUIEvent {
        float x;
        float y;
        int soundResId;
        int gravity;
        boolean showJinyIcon = true;
        boolean showPointer = true;

        public int getSoundResId() {
            return soundResId;
        }

        public void setSoundResId(int soundResId) {
            this.soundResId = soundResId;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public int getGravity() {
            return gravity;
        }

        public void setGravity(int gravity) {
            this.gravity = gravity;
        }

        public boolean isShowJinyIcon() {
            return showJinyIcon;
        }

        public void setShowJinyIcon(boolean showJinyIcon) {
            this.showJinyIcon = showJinyIcon;
        }

        public boolean isShowPointer() {
            return showPointer;
        }

        public void setShowPointer(boolean showPointer) {
            this.showPointer = showPointer;
        }
    }
}
