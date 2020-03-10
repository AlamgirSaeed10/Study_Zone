package com.software.acuity.splick.utils;

import java.util.Calendar;

public class DateIntervalUtil {

    public enum IntervalType {Year, Month, Week}

    private DateIntervalUtil() {
    }

    public static Calendar[] getDateIntervals(IntervalType type, Calendar reference) {
        if (reference == null) {
            reference = Calendar.getInstance();
        }
        Calendar startDate = (Calendar) reference.clone();
        Calendar endDate = (Calendar) reference.clone();

        if (type == IntervalType.Month) {
            // first date of the month
            startDate.set(Calendar.DATE, 1);
            // previous month
            startDate.add(Calendar.MONTH, -1);

            // first date of the month
            endDate.set(Calendar.DATE, 1);
            // previous month, last date
            endDate.add(Calendar.DATE, -1);
        } else {
            // previous week by convention (monday ... sunday)
            // you will have to adjust this a bit if you want
            // sunday to be considered as the first day of the week.
            //   start date : decrement until first sunday then
            //   down to monday
            int dayOfWeek = startDate.get(Calendar.DAY_OF_WEEK);
            while (dayOfWeek != Calendar.SUNDAY) {
                startDate.add(Calendar.DATE, -1);
                dayOfWeek = startDate.get(Calendar.DAY_OF_WEEK);
            }
            while (dayOfWeek != Calendar.MONDAY) {
                startDate.add(Calendar.DATE, -1);
                dayOfWeek = startDate.get(Calendar.DAY_OF_WEEK);
            }

            // end date , decrement until the first sunday
            dayOfWeek = endDate.get(Calendar.DAY_OF_WEEK);
            while (dayOfWeek != Calendar.SUNDAY) {
                endDate.add(Calendar.DATE, -1);
                dayOfWeek = endDate.get(Calendar.DAY_OF_WEEK);
            }
        }
        return new Calendar[]{startDate, endDate};
    }
}
