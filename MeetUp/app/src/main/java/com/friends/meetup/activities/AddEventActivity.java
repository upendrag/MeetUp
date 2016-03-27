package com.friends.meetup.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.friends.meetup.R;
import com.friends.meetup.adapters.TimeSlotAdapter;
import com.friends.meetup.models.Model;

import java.util.ArrayList;
import java.util.List;


public class AddEventActivity extends ActionBarActivity implements View.OnClickListener {

    private ListView freeSlotList;
    private TimeSlotAdapter adapter;
    private Button add;
    List<Model> model = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        freeSlotList = (ListView) findViewById(R.id.list_free_slots);

        adapter = new TimeSlotAdapter(AddEventActivity.this, model);
        freeSlotList.setAdapter(adapter);
        add = (Button) findViewById(R.id.addBtn);
        add.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.addBtn:

                model.add(new Model());
                model.add(new Model());
                model.add(new Model());
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
