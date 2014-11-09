package pt.abrrs.alarmpadradio.dirble.services;

import java.util.List;

import pt.abrrs.alarmpadradio.dirble.interfaces.Radios;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface DirbleService
{
    @GET("/country/apikey/<Dirble API KEY>/country/{country}")
    void listRadiosByCountry(@Path("country") String country, Callback<List<Radios>> callback
    );
}
