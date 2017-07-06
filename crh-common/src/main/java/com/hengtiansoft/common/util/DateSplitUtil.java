package com.hengtiansoft.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Class Name: DateSplitUtil Description: Split date
 * 
 * @author taochen
 *
 */
public class DateSplitUtil {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final long ONE_DAY = 24 * 60 * 60 * 1000;

    public static Calendar cal = Calendar.getInstance();

    /**
     * Description: Separated by time interval
     *
     * @param startDate
     * @param endDate
     * @return Date list
     * @throws Exception
     */
    public static List<Date> dateSplit(Date startDate, Date endDate) throws Exception {
        if (startDate.after(endDate)) {
            throw new Exception("The start time should be after the end time");
        }

        Long spi = endDate.getTime() - startDate.getTime();
        Long step = (spi / ONE_DAY);
        System.out.println("Days：" + step);

        List<Date> dateList = new ArrayList<Date>();
        for (int i = 0; i <= step.intValue(); i++) {
            dateList.add(new Date(startDate.getTime() + ONE_DAY * i));
        }
        return dateList;
    }

    /**
     * Description: Get the number of days in the month
     *
     * @param date
     * @return
     */
    public static Integer getDaysOfMonth(Date date) {
        cal.setTime(date);
        // Set the first day of the month
        cal.set(Calendar.DAY_OF_MONTH, 1);
        // Date Rollback day, that is the last day
        cal.roll(Calendar.DAY_OF_MONTH, -1);

        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Description: Month of taking the difference between two dates
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getDiffOfDate(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            return null;
        }
        cal.setTime(startDate);
        Integer startYear = cal.get(Calendar.YEAR);
        Integer startMonth = cal.get(Calendar.MONTH);

        cal.setTime(endDate);
        Integer endYear = cal.get(Calendar.YEAR);
        Integer endMonth = cal.get(Calendar.MONTH);

        Integer diff = 0;
        if (startYear.equals(endYear)) {
            // If in the same year, use subtraction directly
            diff = endMonth - startMonth;
        } else {
            // Use 12 minus the start month plus the end month
            diff = 12 - startMonth + endMonth;
        }
        return diff;
    }

}
