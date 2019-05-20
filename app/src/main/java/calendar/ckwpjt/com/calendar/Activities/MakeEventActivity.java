package calendar.ckwpjt.com.calendar.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import calendar.ckwpjt.com.calendar.DAO.DBHelper;
import calendar.ckwpjt.com.calendar.Objects.DateInfo;
import calendar.ckwpjt.com.calendar.Objects.TaskInfo;
import calendar.ckwpjt.com.calendar.R;

public class MakeEventActivity extends AppCompatActivity {

    private LocalDate mDatePointer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_event);

        Intent intent = getIntent();

        mDatePointer = (LocalDate) intent.getSerializableExtra("Date");

        final TextView dateText = findViewById(R.id.make_event_input_date);
        final EditText titleText = findViewById(R.id.make_event_input_title);
        final EditText contentsText  = findViewById(R.id.make_event_input_contents);

        Button enrollButton = findViewById(R.id.make_event_enroll_btn);
        Button cancelButton = findViewById(R.id.make_event_exit_btn);

        dateText.setText(mDatePointer.format(DateInfo.getFormatter()));

        dateText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(MakeEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        String msg = String.format("%d 년 %d 월 %d 일", year, month+1, date);

                        String monthString = month + 1 < 10 ? "0" + Integer.toString(month + 1) : Integer.toString(month+1);
                        String dayString = date < 10 ? "0" + Integer.toString(date) : Integer.toString(date);

                        dateText.setText(Integer.toString(year) +"-"+ monthString + "-" + dayString);
                    }
                }, mDatePointer.getYear(), mDatePointer.getMonth().getValue() - 1, mDatePointer.getDayOfMonth());

                dialog.show();
            }

        });


        enrollButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                TextView dateText = findViewById(R.id.make_event_input_date);

                String dateString = dateText.getText().toString();
                String titleString = titleText.getText().toString();
                String contentsString = contentsText.getText().toString();

                DBHelper dbHelper = new DBHelper(getApplicationContext());

                LocalDate date = LocalDate.parse(dateString, DateInfo.getFormatter());
                TaskInfo task = new TaskInfo(titleString,contentsString);

                dbHelper.uploadTask(date,task);

                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
