/**
 *
 *  @author Pazur Michał S19023
 *
 */

package PASSTIME1;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Time
{
    private static Locale pl = new Locale("pl");
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy (EEEE)", pl);
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy (EEEE) 'godz.' HH:mm", pl);
    private static ZoneId timeZone = ZoneId.of("Europe/Warsaw");

    static String passed(String from, String to)
    {
        String toReturn = "";
        try
        {
            LocalDate dateFrom = LocalDate.parse(from);
            LocalDate dateTo = LocalDate.parse(to);
            toReturn = "Od " + dateFrom.format(dateFormatter);
            toReturn += " do " + dateTo.format(dateFormatter);

            toReturn += TextParser.getDaysBetween((int)(ChronoUnit.DAYS.between(dateFrom, dateTo)));
            toReturn += TextParser.getCalendarDaysBetween(Period.between(dateFrom, dateTo));
        }
        catch(DateTimeParseException e)
        {
            try
            {
                LocalDateTime dateFrom = LocalDateTime.parse(from);
                LocalDateTime dateTo = LocalDateTime.parse(to);
                ZonedDateTime zonedDateFrom = ZonedDateTime.of(dateFrom, timeZone);
                ZonedDateTime zonedDateTo = ZonedDateTime.of(dateTo, timeZone);
                LocalDate localDateFrom = dateFrom.toLocalDate();
                LocalDate localDateTo = dateTo.toLocalDate();

                toReturn = "Od " + zonedDateFrom.format(dateTimeFormatter);
                toReturn += " do " + zonedDateTo.format(dateTimeFormatter);

                toReturn += TextParser.getDaysBetween(ChronoUnit.DAYS.between(localDateFrom, localDateTo));
                toReturn += TextParser.getHoursBetween(ChronoUnit.HOURS.between(zonedDateFrom, zonedDateTo), ChronoUnit.MINUTES.between(zonedDateFrom, zonedDateTo));
                toReturn += TextParser.getCalendarDaysBetween(Period.between(localDateFrom, localDateTo));
            }
            catch(DateTimeParseException ex)
            {
                return "*** " + ex.toString();
            }
        }

        return toReturn;
    }
}
