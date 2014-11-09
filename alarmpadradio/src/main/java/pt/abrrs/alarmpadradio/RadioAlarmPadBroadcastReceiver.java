package pt.abrrs.alarmpadradio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.IOException;

public class RadioAlarmPadBroadcastReceiver extends BroadcastReceiver
{
    private Context context;
    private static MediaPlayer _player;


    @Override
    public void onReceive(Context context, final Intent intent)
    {
//        Fabric.with(context, new Crashlytics());

        this.context = context;

        if(intent.getAction().equals(RadioExtension.BROADCAST_ACTION_RADIO_START))
        {
            initializeMediaPlayer();
            if (_player != null)
            {
                _player.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
                {
                    @Override
                    public void onPrepared(MediaPlayer player)
                    {
                        if (!_player.isPlaying())
                        {
                            player.start();
                        }
                    }
                });

                if (Utils.hasInternetConnection(this.context))
                {
                    _player.prepareAsync();
                } else
                {
                    Toast.makeText(this.context, R.string.toast_no_connection, Toast.LENGTH_LONG).show();
                }
            }
        }
        else if (intent.getAction().equals(RadioExtension.BROADCAST_ACTION_RADIO_STOP))
        {
            stopRadio();
        }
    }


    private void initializeMediaPlayer()
    {
        _player = new MediaPlayer();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        try
        {
            _player.setDataSource(preferences.getString(context.getResources().getString(R.string.PREFERENCE_KEY_RADIO), RadioAlarmPadConstants.PREFERENCE_RADIO_DEFAULT_URL));
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalStateException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void stopRadio()
    {
        if(_player != null)
        {
            _player.stop();
            _player.release();
            initializeMediaPlayer();
        }
    }
}
