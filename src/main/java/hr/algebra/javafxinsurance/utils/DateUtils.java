package hr.algebra.javafxinsurance.utils;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static LocalDate getLocalDateFromDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date getDateFromLocalDate(LocalDate date){
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
