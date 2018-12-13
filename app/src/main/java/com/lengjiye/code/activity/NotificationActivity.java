package com.lengjiye.code.activity;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.lengjiye.code.MainActivity;
import com.lengjiye.code.R;
import com.lengjiye.code.base.BaseActivity;

/**
 * 通知栏activity
 */
public class NotificationActivity extends BaseActivity {

    @Override
    public int getResourceId() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initViews() {
        super.initViews();


    }

    @Override
    protected void setListener() {
        super.setListener();
        setOnClickListener(findViewById(R.id.button), findViewById(R.id.button1));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.button:
                sentNotification();
                break;
            case R.id.button1:

                break;
        }
    }

    /**
     * 发送通知
     */
    private void sentNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "chat");
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Gavin")
                .setContentText("Today released Android 8.0 version of its name is Oreo")
                .setAutoCancel(true);

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
