package com.insurancenext.rtwcwear.view;

/**
 * Created by ${MUTHU} on 5/14/2015.
 */
public class FrontRaiseView extends NotificationView {
    private int frontraisecount = -1;

    public void clear() {
        super.clear();
        this.frontraisecount = -1;
    }

    @Override
    public String makeShortText(long elapsed) {
        String str = "";
        if (elapsed > 0) {
            str = "Front Raise Count " + elapsed;
        }
        return str;
    }

    @Override
    public String makeLongText(String str) {
        return str;
    }
}
