package calendar.ckwpjt.com.calendar.Objects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateInfo {
    private LocalDate mDate;
    private List<TaskInfo> mTasks;
    private static final String mDateFormat = "yyyy-MM-dd";
    public static final int WEEK_LENGTH = 7;

    public DateInfo(){
        mDate = null;
        mTasks = new ArrayList<TaskInfo>();
    }

    public DateInfo(LocalDate d){
        mDate = d;
        mTasks = new ArrayList<TaskInfo>();
    }

    public DateInfo(LocalDate d, List<TaskInfo> t)
    {
        mDate = d;
        mTasks = t;
    }

    public LocalDate getDate() {
        return mDate;
    }

    public List<TaskInfo> getTasks(){
        return mTasks;
    }

    public static DateTimeFormatter getFormatter(){
        return DateTimeFormatter.ofPattern(mDateFormat);
    }

    public static String getYearMonthString(LocalDate date){
        return date.getYear() + "년 " + date.getMonthValue() + "월";
    }

    public static int maximumDate(LocalDate d){
        Calendar cal = Calendar.getInstance();
        cal.set(d.getYear(), d.getMonthValue() - 1 , d.getDayOfMonth());

        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
