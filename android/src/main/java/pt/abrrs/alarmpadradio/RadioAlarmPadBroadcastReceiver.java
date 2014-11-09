package pt.abrrs.alarmpadradio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.IOException;

public class RadioAlarmPadBroadcastReceiver extends BroadcastReceiver
{
    private Context _context;
    private static MediaPlayer _player;
    private SharedPreferences _preferences;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        _context = context;
        _preferences = PreferenceManager.getDefaultSharedPreferences(_context);

        if(intent.getAction().equals(RadioExtension.BROADCAST_ACTION_RADIO_START))
        {
            initializeMediaPlayer();
            if(_player != null)
            {
                _player.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
                {
                    @Override
                    public void onPrepared(MediaPlayer player)
                    {
                        if(!_player.isPlaying())
                        {
                            player.start();
                        }
                    }
                });
                if(hasInternetConnection())
                {
                    _player.prepareAsync();
                }
                else
                {
                    Toast.makeText(_context, R.string.toast_no_connection, Toast.LENGTH_LONG).show();
                }
            }
        }
        else if(intent.getAction().equals(RadioExtension.BROADCAST_ACTION_RADIO_STOP))
        {
            stopRadio();
        }
    }

    private boolean hasInternetConnection()
    {
        boolean preferenceWifiOnly = _preferences.getBoolean(_context.getResources().getString(R.string.PREFERENCE_KEY_WIFI), false);
        ConnectivityManager connectionManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(networkInfo.isConnected() && !preferenceWifiOnly)
        {
            return true;
        }

        networkInfo = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected())
        {
            return true;
        }

        return false;
    }

    private void initializeMediaPlayer()
    {
        _player = new MediaPlayer();

        try
        {
            _player.setDataSource(_preferences.getString(_context.getResources().getString(R.string.PREFERENCE_KEY_RADIO), RadioAlarmPadConstants.PREFERENCE_RADIO_DEFAULT_URL));
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
        if(_player != null && _player.isPlaying())
        {
            _player.stop();
            _player.release();
            initializeMediaPlayer();
        }
    }
}
