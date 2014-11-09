package pt.abrrs.alarmpadradio;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class RadioSettingsActivity extends PreferenceActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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

