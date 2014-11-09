package pt.abrrs.alarmpadradio;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

public class Utils
{
    public static String getUserCountry(Context context)
    {
        try
        {
            final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = telephonyManager.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2)
            {
                return simCountry.toLowerCase();
            }
            else if (telephonyManager.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA)
            {
                String networkCountry = telephonyManager.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2)
                {
                    return networkCountry.toLowerCase();
                }
            }
        }
        catch (Exception e) {
            return "";
        }
        return "";
    }

    public static boolean hasInternetConnection(Context context)
    {
        SharedPreferences _preferences = PreferenceManager.getDefaultSharedPreferences(context);

        boolean preferenceWifiOnly = _preferences.getBoolean(context.getResources().getString(R.string.PREFERENCE_KEY_WIFI), false);
        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(networkInfo.isConnected() && !preferenceWifiOnly)
        {
            return true;
        }

        networkInfo = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo.isConnected();
    }
}
