/**
 *
 *  @author Pazur Michał S19023
 *
 */

package zad1;


import org.json.JSONObject;

import javax.swing.*;
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
    Locale locale;
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
                locale = l;
                break;
            }
        }
    }

    public String getWeather(String city)
    {
        this.city = city;
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + locale.getCountry() + "&appid=" + apiKey + "&units=metric";
        return getDataFromURL(urlString);
    }


    public Double getRateFor(String forCurrency)
    {
        String baseCurrency = currency.getCurrencyCode();
        String urlString = "https://api.exchangeratesapi.io/latest?base=" + baseCurrency + "&symbols=" + forCurrency;
        String json = getDataFromURL(urlString);
        if(json != null)
        {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getJSONObject("rates").getDouble(forCurrency);
        }
        else
        {
            return 0.0;
        }
    }

    public Double getNBPRate()
    {
        if (currency.getCurrencyCode().equals("PLN"))
            return 1.0;

        String urlString = "http://api.nbp.pl/api/exchangerates/rates/A/" + currency.getCurrencyCode();
        String json = getDataFromURL(urlString);

        if (json == null)
        {
            urlString = "http://api.nbp.pl/api/exchangerates/rates/B/" + currency.getCurrencyCode();
            json = getDataFromURL(urlString);
            if (json == null)
            {
                return getRateFor("PLN");
            }
        }

        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.getJSONArray("rates").getJSONObject(0).getDouble("mid");
    }

    void showGUI()
    {
        SwingUtilities.invokeLater(() ->
        {
            JFrame jFrame = new JFrame();
            jFrame.setSize(800, 600);
            jFrame.setVisible(true);
            jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jFrame.add(new WebBrowserPane("http://en.wikipedia.org/wiki/" + city));
        });
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
                return null;
            }
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        return response.toString();
    }
}
