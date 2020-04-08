package PASSTIME1;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class TextParser
{
    static String getFormattedString(long value, ChronoUnit unit)
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

    static String getDaysBetween(long totalDaysBetween)
    {
        return "\n - mija: " + totalDaysBetween + " " + TextParser.getFormattedString(totalDaysBetween, ChronoUnit.DAYS) + String.format(", tygodni %.2f", totalDaysBetween/7.0);
    }

    static String getHoursBetween(long hoursBetween, long minutesBetween)
    {
        return "\n - godzin: " + hoursBetween + ", minut: " + minutesBetween;
    }

    static String getCalendarDaysBetween(Period p)
    {
        String toReturn = "\n - kalendarzowo: ";
        for (TemporalUnit t : p.getUnits())
        {
            int value = (int) p.get(t);

            if (value > 0)
                toReturn += value + " " + getFormattedString(value, (ChronoUnit) t) + ", ";
        }

        return toReturn.substring(0, toReturn.length() - 2);
    }

    private static String getMinutes(long value)
    {
        if (value == 0 || value > 4)
            return "minut";
        else if (value == 1)
            return "minuta";
        else
            return "minuty";
    }

    private static String getHours(long value)
    {
        if (value == 0 || value > 4)
            return "godzin";
        else if (value == 1)
            return "godzina";
        else
            return "godziny";
    }

    private static String getDays(long value)
    {
        if (value == 0 || value > 1)
            return "dni";
        else
            return "dzień";
    }

    private static String getMonths(long value)
    {
        if (value == 0 || value > 4)
            return "miesięcy";
        else if (value == 1)
            return "miesiąc";
        else
            return "miesiące";
    }

    private static String getYears(long value)
    {
        if (value == 0 || value > 4)
            return "lat";
        else if (value == 1)
            return "rok";
        else
            return "lata";
    }
}
