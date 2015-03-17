package com.smi.irc;
import java.io.IOException;

import org.json.JSONException;

import net.aksingh.owmjapis.AbstractWeather;
import net.aksingh.owmjapis.AbstractWeather.Weather;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import net.aksingh.owmjapis.OpenWeatherMap.Language;
import net.aksingh.owmjapis.OpenWeatherMap.Units;



public class Weatherer {
	//76f0f809ee20adaf19355bd2095d4dc3
	public static String getWeather(String city){
        OpenWeatherMap owm = new OpenWeatherMap(Units.METRIC,Language.FINNISH,"76f0f809ee20adaf19355bd2095d4dc3");
        owm.setLang(Language.FINNISH);
        Weather aw = null;
        AbstractWeather uw;
        CurrentWeather cw = null;
		try {
			
			cw = owm.currentWeatherByCityName(city+",FI");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if (!cw.isValid()) {
            return "Kaupunkia ei löytynyt";
        } else {
        	
        	return ""+cw.getMainInstance().getTemperature()+"°C "+cw.objectDesc.optString("description");
           // System.out.println();
        }
		//return "";
	}
}
