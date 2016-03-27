package com.friends.meetup;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.friends.meetup.activities.LoginActivity;
import com.friends.meetup.entities.MeetUpUser;
import com.friends.meetup.kinvey.KinveyClient;
import com.kinvey.android.AsyncUser;
import com.kinvey.android.Client;

public class MainActivity extends AppCompatActivity {

    private boolean isUserInfoSet;
    private Client kinveyClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isUserInfoSet = false;
        // get kinveyClient
        this.kinveyClient = ((KinveyClient) getApplication()).getKinveyClient();

        if (!isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    // set username and full name in nav header;
    private void setUserInfo() {
        if (isUserInfoSet)
            return;

        AsyncUser user = kinveyClient.user();
        String firstName = (String) user.get(MeetUpUser.KEY_FIRST_NAME);
        String lastName = (String) user.get(MeetUpUser.KEY_LAST_NAME);

        if (firstName != null && lastName !=null) {
            String userFullName = firstName.toString() + " " + lastName.toString();
            //TextView fullNameTV = (TextView) headerView.findViewById(R.id.tv_nav_header_full_name);
            //fullNameTV.setText(userFullName);
            isUserInfoSet = true;
        }

        if (user.getUsername() != null) {
            String username = "@" + user.getUsername();
            //TextView usernameTV = (TextView) headerView.findViewById(R.id.tv_nav_header_username);
            //usernameTV.setText(username);
        }
    }

    private boolean isLoggedIn() {
        AccountManager am = AccountManager.get(getApplicationContext());
        Account[] accounts = am.getAccountsByType(KinveyClient.ACCOUNT_TYPE);

        return accounts.length > 0 && kinveyClient.user().isUserLoggedIn();
    }

    public void logOut(MenuItem item) {
        /*
         * TODO: Log out is not an essentaial feature. Can be removed later
         * Keeping this here as a debugging and testing purposes
         * Need a better implementation to push this feature to production
         */
        kinveyClient.user().logout().execute();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
