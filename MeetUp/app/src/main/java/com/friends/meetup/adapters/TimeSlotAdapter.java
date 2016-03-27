package com.friends.meetup.adapters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.friends.meetup.R;
import com.friends.meetup.models.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by shruthi on 27/3/16.
 */
public class TimeSlotAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    Context ctx;
    List<Model> model;

    public TimeSlotAdapter(Context ctx, List<Model> model) {
        this.ctx = ctx;
        this.model = model;
        inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        return model.get(i);
    }

    @Override
    public long getItemId(int i) {
        return model.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        String myFormat = "MM/dd/yy"; //In which you need put here
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.time_slot_leg, null);
            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.date = (EditText) convertView.findViewById(R.id.date);
            holder.fromTime = (EditText) convertView.findViewById(R.id.etxt_time_from);
            holder.toTime = (EditText) convertView.findViewById(R.id.etxt_time_to);
            holder.add = (Button) convertView.findViewById(R.id.addmebtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Model m = model.get(position);
        if (m.getDate() != null) {
            holder.date.setText(m.getDate());
            holder.fromTime.setText(m.getFrom());
            holder.toTime.setText(m.getTo());
        }
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                holder.date.setText(sdf.format(myCalendar.getTime()));
            }

        };
        holder.date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ctx, dateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        holder.fromTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        holder.fromTime.setText(selectedHour + ":" + selectedMinute);
                    }
                };
                mTimePicker = new TimePickerDialog(ctx, listener, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        holder.toTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        holder.toTime.setText(selectedHour + ":" + selectedMinute);
                    }
                };
                mTimePicker = new TimePickerDialog(ctx, listener, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.add(new Model(holder.date.getText().toString(), holder.fromTime.getText().toString(), holder.toTime.getText().toString()));
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public class ViewHolder {
        EditText date;
        EditText fromTime;
        EditText toTime;
        Button add;
    }
}
