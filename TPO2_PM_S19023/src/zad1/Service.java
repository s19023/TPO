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
    JLabel weather, currencyRate, rateForPLN;

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
        String json = getDataFromURL(urlString);
        JSONObject jsonObject = new JSONObject(json);
        String weatherDescription = "Current weather in " + city + ": " + jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
        double temperature = jsonObject.getJSONObject("main").getDouble("temp");
        weatherDescription = weatherDescription + ", it is currently " + temperature + "°C";
        weather = new JLabel(weatherDescription);
        return json;
    }


    public Double getRateFor(String forCurrency)
    {
        String baseCurrency = currency.getCurrencyCode();
        String urlString = "https://api.exchangeratesapi.io/latest?base=" + baseCurrency + "&symbols=" + forCurrency;
        String json = getDataFromURL(urlString);
        double rate = 0.0;
        String rateString;
        if(json != null)
        {
            JSONObject jsonObject = new JSONObject(json);
            rate = jsonObject.getJSONObject("rates").getDouble(forCurrency);
            rateString = "1 " + baseCurrency + " = " + rate + " " + forCurrency;
        }
        else
        {
            rateString = "1 " + baseCurrency + " = ?.?? " + forCurrency;
        }
        currencyRate = new JLabel(rateString);
        return rate;
    }

    public Double getNBPRate()
    {
        double rate = 1.0;
        String rateString, baseCurrency = currency.getCurrencyCode();
        if (baseCurrency.equals("PLN"))
        {
            rateString = "1 PLN = 1 PLN";
            rateForPLN = new JLabel(rateString);
            return rate;
        }

        String urlString = "http://api.nbp.pl/api/exchangerates/rates/A/" + baseCurrency;
        String json = getDataFromURL(urlString);

        if (json == null)
        {
            urlString = "http://api.nbp.pl/api/exchangerates/rates/B/" + baseCurrency;
            json = getDataFromURL(urlString);
        }

        JSONObject jsonObject = new JSONObject(json);
        rate = jsonObject.getJSONArray("rates").getJSONObject(0).getDouble("mid");
        rateString = "1 " + baseCurrency + " = " + rate + " PLN";
        rateForPLN = new JLabel(rateString);
        return rate;
    }

    void showGUI()
    {
        SwingUtilities.invokeLater(() ->
        {
            JFrame jFrame = new JFrame();
            JPanel jPanel = new JPanel();
            jFrame.setSize(800, 600);
            jFrame.setVisible(true);
            jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jPanel.add(weather);
            jPanel.add(currencyRate);
            jPanel.add(rateForPLN);
            jPanel.add(new WebBrowserPane("http://en.wikipedia.org/wiki/" + city));
            jFrame.add(jPanel);
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
