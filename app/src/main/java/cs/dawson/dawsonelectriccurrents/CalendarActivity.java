package cs.dawson.dawsonelectriccurrents;

import android.app.AlertDialog;
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

public class CalendarActivity extends MenuActivity
{
    private static final String EVENTCREATED = "The event was created.";
    private static final String TIMEZONE  = "America/Montreal";
	private EditText etTitle;
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
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m = m + 1;

                year = y;
                month = m - 1;
                day = d;
                String date = m + "/" + d + "/" + y;
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
                String startTime;
                startHour = hours;
                startMinute = minutes;

                if(minutes < 10){
                    startTime = hours + ":0" + minutes;
                }
                else {
                    startTime = hours + ":" + minutes;
                }
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
                String endTime;
                endHour = hours;
                endMinute = minutes;

                if(minutes < 10){
                    endTime = hours + ":0" + minutes;
                }
                else {
                    endTime = hours + ":" + minutes;
                }
                endTimePickerTV.setText(endTime);
            }
        };
    }

    public boolean validateTime(){
        String startTime;
        String endTime;
        int start;
        int end;

        startTime = startHour + "" + startMinute;
        endTime = endHour + "" + endMinute;

        start = Integer.parseInt(startTime);
        end = Integer.parseInt(endTime);

        if(start > end)
            return false;
        else
            return true;
    }

    public void addEvent(View v){
        long calID = 1;
        long start;
        long end;
        boolean validStartEndTime = validateTime();

        if(etTitle.getText().toString().equals("")){
            AlertDialog errorDialog = new AlertDialog.Builder(
                    CalendarActivity.this).create();
            errorDialog.setTitle("Error!");
            errorDialog.setMessage("You must enter a title!");
            errorDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            errorDialog.show();
        }
        else if(datePickerTV.getText().toString().equals("")){
            AlertDialog errorDialog = new AlertDialog.Builder(
                    CalendarActivity.this).create();
            errorDialog.setTitle("Error!");
            errorDialog.setMessage("You must enter a date!");
            errorDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            errorDialog.show();
        }
        else if(startTimePickerTV.getText().toString().equals("") || endTimePickerTV.getText().toString().equals("")){
            AlertDialog errorDialog = new AlertDialog.Builder(
                    CalendarActivity.this).create();
            errorDialog.setTitle("Error!");
            errorDialog.setMessage("You must enter a start and end time!");
            errorDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            errorDialog.show();
        }
        else if(!validStartEndTime) {
            AlertDialog errorDialog = new AlertDialog.Builder(
                    this).create();
            errorDialog.setTitle("Error!");
            errorDialog.setMessage("The end time cannot be earlier than the start time.");
            errorDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            errorDialog.show();
        }
        else{
            Calendar startTime = Calendar.getInstance();
			startTime.set(year, month, day, startHour, startMinute);
			start = startTime.getTimeInMillis();
			Calendar endTime = Calendar.getInstance();
			endTime.set(year, month, day, endHour, endMinute);
			end = endTime.getTimeInMillis();

			ContentResolver cr = getContentResolver();
			ContentValues values = new ContentValues();
			values.put(CalendarContract.Events.DTSTART, start);
			values.put(CalendarContract.Events.DTEND, end);
			values.put(CalendarContract.Events.EVENT_TIMEZONE, TIMEZONE);
			values.put(CalendarContract.Events.TITLE, etTitle.getText().toString());
			values.put(CalendarContract.Events.CALENDAR_ID, calID);
			Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

            Toast.makeText(this, EVENTCREATED, Toast.LENGTH_SHORT).show();
        }
	}
}
