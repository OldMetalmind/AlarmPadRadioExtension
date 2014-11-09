package pt.abrrs.alarmpadradio;

import android.content.Intent;

import com.mindmeapp.extensions.MindMeExtension;

public class RadioExtension extends MindMeExtension
{
    public static final String BROADCAST_ACTION_RADIO_STOP = "pt.abrrs.mindmeapp.alarm.ALARM_STOP";
    public static final String BROADCAST_ACTION_RADIO_START = "com.mindmeapp.alarm.ALARM_START";

    @Override
    protected void onUpdateData(int reason)
    {
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION_RADIO_STOP);
        sendBroadcast(intent);

        publishUpdate(null);
    }
}
