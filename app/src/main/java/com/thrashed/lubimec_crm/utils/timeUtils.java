package com.thrashed.lubimec_crm.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class timeUtils {

    public static final long DAY_MILLIS = 86400000; // Время в сутках в миллисекундах




    static long firstday = new Date(0L).getTime(); // Время начала эпохи 1 января 1970
    static long now = new Date().getTime(); // Текущее время

    static Calendar c = Calendar.getInstance(); // Вызов календаря для дальнейшего определения временной зоны
    static int utcOffset = c.get(Calendar.ZONE_OFFSET); // Величина поправки по временной зоне

    static long DAY_NUM = (now - firstday + utcOffset) / DAY_MILLIS; // Номер дня с начала эпохи
    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

    public static int dayNumber() {
        return  (int) DAY_NUM;
    }

    public static int dayOfWeek() {
        return dayOfWeek();
    }
    public static int week() {
        return (int) (DAY_NUM / 7);
    }


    public static String getFormattedDate() {
        String pattern = "dd MMMM yyyy";
        return new SimpleDateFormat(pattern, new Locale("ru")).format(new Date(now));
    }
}
