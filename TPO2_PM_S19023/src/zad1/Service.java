/**
 *
 *  @author Pazur Michał S19023
 *
 */

package zad1;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

public class Service {

    String apiKey = "f4a7b39051ac63abb8dfa9de06ff2233";
    String countryName, city;
    Currency currency;

    public Service(String countryName)
    {
        this.countryName = countryName;
        Locale en = new Locale("en", "gb");
        for(String iso : Locale.getISOCountries())
        {
            Locale l = new Locale("", iso);
            if (l.getDisplayCountry(en).equals(countryName))
            {
                currency = Currency.getInstance(l);
                break;
            }
        }
    }

    public String getWeather(String city)
    {
        this.city = city;
        String response = "";
        try
        {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8")))
            {
                String line;
                while((line = bufferedReader.readLine()) != null)
                {
                    response += line + "\n";
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }

        System.out.println(response);
        return response;
    }


    public Double getRateFor(String currency)
    {
        return 0.0;
    }

    public Double getNBPRate()
    {
        return 0.0;
    }
}
