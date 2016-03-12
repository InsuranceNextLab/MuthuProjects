package com.insurancenext.rtwcwear.view;

/**
 * Created by ${MUTHU} on 5/6/2015.
 */
public class BicepsView extends NotificationView {

    private int bicepscount = -1;

    public void clear() {
        super.clear();
        this.bicepscount = -1;
    }

    @Override
    public String makeShortText(long elapsed) {
        String str = "";
        if (elapsed > 0) {
            str = "Biceps Count " + elapsed;
        }
        return str;
    }

    @Override
    public String makeLongText(String str) {
        return str;
    }
}
