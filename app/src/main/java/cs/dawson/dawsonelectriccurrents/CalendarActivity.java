package cs.dawson.dawsonelectriccurrents;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarActivity extends MenuActivity {
    private TextView etTitle;
    private TextView datePickerTV;
    private TextView startTimePickerTV;
    private TextView endTimePickerTV;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener startTimeSetListener;
    private TimePickerDialog.OnTimeSetListener endTimeSetListener;
    private int year;
    private int month;
    private int day;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        etTitle = findViewById(R.id.etTitle);
        datePickerTV = findViewById(R.id.tvDatePicker);
        startTimePickerTV = findViewById(R.id.tvStartTimePicker);
        endTimePickerTV = findViewById(R.id.tvEndTimePicker);

        setDatePickerTVListener();
        setStartTimePickerTVListener();
        setEndTimePickerTVListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    public void setDatePickerTVListener(){
        datePickerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                cal.set(year, month, day);
                DatePickerDialog dialog = new DatePickerDialog(
                        CalendarActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int syear, int smonth, int sday) {
                smonth = smonth + 1;

                year = syear;
                month = smonth - 1;
                day = sday;
                String date = smonth + "/" + sday + "/" + syear;
                datePickerTV.setText(date);
            }
        };
    }

    public void setStartTimePickerTVListener(){
        startTimePickerTV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                startHour = cal.get(Calendar.HOUR);
                startMinute = cal.get(Calendar.MINUTE);
                cal.set(year, month, day, startHour, startMinute);
                TimePickerDialog dialog = new TimePickerDialog(
                        CalendarActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        startTimeSetListener,
                        startHour, startMinute,
                        false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        startTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                startHour = hours;
                startMinute = minutes;
                String startTime = hours + ":" + minutes;
                startTimePickerTV.setText(startTime);
            }
        };
    }

    public void setEndTimePickerTVListener(){
        endTimePickerTV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                endHour = cal.get(Calendar.HOUR);
                endMinute = cal.get(Calendar.MINUTE);
                cal.set(year, month, day, endHour, endMinute);
                TimePickerDialog dialog = new TimePickerDialog(
                        CalendarActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        endTimeSetListener,
                        endHour, endMinute,
                        false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        endTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                endHour = hours;
                endMinute = minutes;
                String endTime = hours + ":" + minutes;
                endTimePickerTV.setText(endTime);
            }
        };
    }

    public void addEvent(View v){
        long calID = 1;
        long startM;
        long endM;
        Calendar startTime = Calendar.getInstance();
        startTime.set(year, month, day, startHour, startMinute);
        startM = startTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, day, endHour, endMinute);
        endM = endTime.getTimeInMillis();

        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startM);
        values.put(CalendarContract.Events.DTEND, endM);
        values.put(CalendarContract.Events.TITLE, etTitle.getText().toString());
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Montreal");
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

        Toast.makeText(this, "The event was created.", Toast.LENGTH_SHORT).show();
    }
}
