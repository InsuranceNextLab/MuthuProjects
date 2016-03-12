package com.insurancenext.rtwcwear.view;

/**
 * Created by ${MUTHU} on 5/14/2015.
 */
public class SideRaiseView extends NotificationView {
    private int sideraisecount = -1;

    public void clear() {
        super.clear();
        this.sideraisecount = -1;
    }

    @Override
    public String makeShortText(long elapsed) {
        String str = "";
        if (elapsed > 0) {
            str = "Side Raise Count " + elapsed;
        }
        return str;
    }

    @Override
    public String makeLongText(String str) {
        return str;
    }
}
