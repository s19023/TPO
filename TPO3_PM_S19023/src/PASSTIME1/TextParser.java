package PASSTIME1;

import java.time.temporal.ChronoUnit;

public class TextParser
{
    static String getFormattedString(int value, ChronoUnit unit)
    {
        value = value > 19 || value < 9 ? value % 10 : value;
        switch(unit)
        {
            case MINUTES:
                return getMinutes(value);
            case HOURS:
                return getHours(value);
            case DAYS:
                return getDays(value);
            case MONTHS:
                return getMonths(value);
            case YEARS:
                return getYears(value);
            default:
                return "???";
        }
    }

    private static String getMinutes(int value)
    {
        if (value == 0 || value > 4)
            return "minut";
        else if (value == 1)
            return "minuta";
        else
            return "minuty";
    }

    private static String getHours(int value)
    {
        if (value == 0 || value > 4)
            return "godzin";
        else if (value == 1)
            return "godzina";
        else
            return "godziny";
    }

    private static String getDays(int value)
    {
        if (value == 0 || value > 1)
            return "dni";
        else
            return "dzień";
    }

    private static String getMonths(int value)
    {
        if (value == 0 || value > 4)
            return "miesięcy";
        else if (value == 1)
            return "miesiąc";
        else
            return "miesiące";
    }

    private static String getYears(int value)
    {
        if (value == 0 || value > 4)
            return "lat";
        else if (value == 1)
            return "rok";
        else
            return "lata";
    }
}
