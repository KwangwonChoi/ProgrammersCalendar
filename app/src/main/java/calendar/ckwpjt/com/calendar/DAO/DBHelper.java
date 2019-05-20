package calendar.ckwpjt.com.calendar.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import calendar.ckwpjt.com.calendar.Objects.DateInfo;
import calendar.ckwpjt.com.calendar.Objects.TaskInfo;

import static calendar.ckwpjt.com.calendar.Objects.DateInfo.WEEK_LENGTH;

public class DBHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_DATE + " TEXT NOT NULL, " +
                    FeedEntry.COLUMN_NAME_TITLE + " TEXT, " +
                    FeedEntry.COLUMN_NAME_CONTENTS + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PsCalendar_test.db";

    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "calendar";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENTS = "contents";
    }

    private String[] projection = {
            FeedEntry.COLUMN_NAME_DATE,
            FeedEntry.COLUMN_NAME_TITLE,
            FeedEntry.COLUMN_NAME_CONTENTS
    };

    public DBHelper(Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public DateInfo getDailyDateInfo(LocalDate curDate){

        SQLiteDatabase db = getReadableDatabase();

        String selection = FeedEntry.COLUMN_NAME_DATE + " = ?";
        String[] selectionArgs = {curDate.format(DateInfo.getFormatter())};

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List<TaskInfo> tasks = new ArrayList<TaskInfo>();
        while(cursor.moveToNext()) {

            String str1 = cursor.getString(1);
            String str2 = cursor.getString(2);

            tasks.add(new TaskInfo(str1,str2));
        }
        cursor.close();

        DateInfo retValue = new DateInfo(curDate,tasks);

        return retValue;
    }

    public List<DateInfo> getWeeklyDateInfo(LocalDate curDate){

        List<DateInfo> retValue = new ArrayList<DateInfo>();

        SQLiteDatabase db = getReadableDatabase();

        String selection = FeedEntry.COLUMN_NAME_DATE + " = ?";

        LocalDate pointingDay = curDate;
        LocalDate firstDay = pointingDay.minusDays(pointingDay.getDayOfWeek().getValue());

        for(int i = 0 ; i < WEEK_LENGTH; i++){

            String[] selectionArgs = {firstDay.format(DateInfo.getFormatter())};

            Cursor cursor = db.query(
                    FeedEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            List<TaskInfo> tasks = new ArrayList<TaskInfo>();
            while(cursor.moveToNext()) {

                String str1 = cursor.getString(1);
                String str2 = cursor.getString(2);

                tasks.add(new TaskInfo(str1,str2));
            }
            cursor.close();

            retValue.add(new DateInfo(firstDay,tasks));

            firstDay = firstDay.plusDays(1);
        }

        return retValue;
    }

    public List<DateInfo> getMonthlyDateInfo(LocalDate curDate){

        List<DateInfo> retValue = new ArrayList<DateInfo>();

        SQLiteDatabase db = getReadableDatabase();

        String selection = FeedEntry.COLUMN_NAME_DATE + " = ?";

        LocalDate firstDay = curDate;

        int firstDayOfMonth = (firstDay.getDayOfWeek().getValue()) % 7 + 1;
        int maximumDate = DateInfo.maximumDate(firstDay);

        for(int i = 1 ; i < firstDayOfMonth + maximumDate; i++){

            if(i < firstDayOfMonth){
                retValue.add(new DateInfo());
                continue;
            }

            String[] selectionArgs = {firstDay.format(DateInfo.getFormatter())};

            Cursor cursor = db.query(
                    FeedEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            List<TaskInfo> tasks = new ArrayList<TaskInfo>();
            while(cursor.moveToNext()) {

                String str1 = cursor.getString(1);
                String str2 = cursor.getString(2);

                tasks.add(new TaskInfo(str1,str2));
            }
            cursor.close();

            retValue.add(new DateInfo(firstDay,tasks));

            firstDay = firstDay.plusDays(1);
        }

        return retValue;
    }

    public void deleteTask(LocalDate date, TaskInfo task){

        SQLiteDatabase db = getReadableDatabase();

        // Define 'where' part of query.
        String selection = FeedEntry.COLUMN_NAME_DATE + "=? AND " +
                FeedEntry.COLUMN_NAME_TITLE + "=? AND " +
                FeedEntry.COLUMN_NAME_CONTENTS + "=?";

        // Specify arguments in placeholder order.
        String[] selectionArgs = { date.format(DateInfo.getFormatter()), task.getTitle(), task.getContents() };
        // Issue SQL statement.
        int deletedRows = db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);

    }

    public void uploadTask(LocalDate date, TaskInfo task){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBHelper.FeedEntry.COLUMN_NAME_DATE, date.format(DateInfo.getFormatter()));
        values.put(DBHelper.FeedEntry.COLUMN_NAME_TITLE, task.getTitle());
        values.put(DBHelper.FeedEntry.COLUMN_NAME_CONTENTS, task.getContents());

        long newRodId = db.insert(DBHelper.FeedEntry.TABLE_NAME, null, values);

    }

}
