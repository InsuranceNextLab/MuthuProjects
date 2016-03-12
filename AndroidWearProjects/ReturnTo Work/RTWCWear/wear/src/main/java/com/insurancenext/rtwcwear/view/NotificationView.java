package com.insurancenext.rtwcwear.view;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.cognizant.workactivate.R;

public abstract class NotificationView {
    private static final int NOTIFICATION_ID = 3000;
    private Context context;
    private NotificationCompat.Builder notificationBuilder;
    private int stepCount = -1;

    public void initialize(Context context, Class trackername) {

        this.context = context;

        Intent intent = new Intent(context, trackername);
        PendingIntent pendingIntent
                = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action openMain
                = new NotificationCompat.Action(R.drawable.empty, null, pendingIntent);

        Bitmap bmp = BitmapFactory.decodeResource(this.context.getResources(),
                R.drawable.notification);

        NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender()
                .setHintHideIcon(true)
                .setBackground(bmp)
//                .setDisplayIntent(pendingIntent)
//                .setCustomSizePreset(NotificationCompat.WearableExtender.SIZE_LARGE)
                .setContentAction(0)
                .addAction(openMain);

        this.notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .extend(extender)
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setOngoing(true);

    }

    public Context getContext() {
        return this.context;

    }

    public void clear() {
        this.stepCount = 0;
    }

    private String makeDetailedText() {
        String str = "";

        if (this.stepCount > 0) {
            str += this.stepCount + this.context.getString(R.string.steps);
        }
        str = this.makeLongText(str);

        return str;
    }


    public abstract String makeShortText(long count);

    public abstract String makeLongText(String str);

    public void show(long elapsed) {
        Log.e("Notification", elapsed + "");
        this.notificationBuilder
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(this.makeShortText(elapsed))
                .setContentText(this.makeDetailedText());

        NotificationManagerCompat.from(this.context)
                .notify(NOTIFICATION_ID, this.notificationBuilder.build());

    }

    public void dismiss() {
        NotificationManagerCompat.from(this.context).cancel(NOTIFICATION_ID);
    }

}
