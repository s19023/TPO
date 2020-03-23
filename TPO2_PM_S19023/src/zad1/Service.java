/**
 *
 *  @author Pazur Michał S19023
 *
 */

package zad1;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Currency;
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
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
        return getDataFromURL(urlString);
    }


    public Double getRateFor(String currency)
    {
        String urlString = "https://api.exchangeratesapi.io/latest?base=" + this.currency.getSymbol() + "&symbols=" + currency;
        String json = getDataFromURL(urlString);
        JSONObject jsonObject = new JSONObject(json);
        Double rate = jsonObject.getJSONObject("rates").getDouble(currency);
        System.out.println(rate);
        return rate;
    }

    public Double getNBPRate()
    {
        if (currency.getSymbol().equals("PLN"))
            return 1.0;

        return 0.0;
    }

    private String getDataFromURL(String urlString)
    {
        StringBuilder response = new StringBuilder();
        try
        {
            URL url = new URL(urlString);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8")))
            {
                String line;
                while((line = bufferedReader.readLine()) != null)
                {
                    response.append(line).append("\n");
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
        return response.toString();
    }
}
