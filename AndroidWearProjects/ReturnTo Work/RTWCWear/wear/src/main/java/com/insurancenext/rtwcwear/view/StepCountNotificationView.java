package com.insurancenext.rtwcwear.view;

/**
 * Created by ${MUTHU} on 4/10/2015.
 */
public class StepCountNotificationView extends NotificationView {

    private int stepcount = -1;

    public void clear() {
        super.clear();
        this.stepcount = -1;
    }

    @Override
    public String makeShortText(long elapsed) {
        String str = "";
        if (elapsed > 0) {

            str = "Your Step Count " + elapsed;
        }
        return str;
    }

    @Override
    public String makeLongText(String str) {
        return str;
    }
}
