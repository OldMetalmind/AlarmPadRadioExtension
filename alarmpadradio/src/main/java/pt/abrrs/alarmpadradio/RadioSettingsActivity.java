package pt.abrrs.alarmpadradio;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import pt.abrrs.alarmpadradio.dirble.interfaces.Radios;
import pt.abrrs.alarmpadradio.dirble.services.DirbleService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

import static pt.abrrs.alarmpadradio.Utils.getUserCountry;

public class RadioSettingsActivity extends PreferenceActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        String locale = getUserCountry(this);
        OkHttpClient okHttpClient = new OkHttpClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://api.dirble.com/v1")
                .setClient(new OkClient(okHttpClient))
                .build();

        DirbleService service = restAdapter.create(DirbleService.class);

        service.listRadiosByCountry(locale, new Callback<List<Radios>>()
        {
            @Override
            public void success(List<Radios> radioses, Response response)
            {
                if(radioses != null && response.getStatus() == 200)
                {
                    List<String> entries = new ArrayList<String>();
                    List<String> entriesValues = new ArrayList<String>();
                    for (Radios radio : radioses)
                    {
                        entries.add(radio.name);
                        entriesValues.add(radio.streamurl);
                    }

                    ListPreference radioListPreference = (ListPreference) findPreference("preferencekeyradio");

                    radioListPreference.setEntries(entries.toArray(new CharSequence[entries.size()]));
                    radioListPreference.setEntryValues(entriesValues.toArray(new CharSequence[entriesValues.size()]));
                }
            }

            @Override
            public void failure(RetrofitError error)
            {
            }
        });


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        setupSimplePreferencesScreen();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        return false;
    }

    private void setupSimplePreferencesScreen()
    {
        addPreferencesFromResource(R.xml.preferences);

    }
}

