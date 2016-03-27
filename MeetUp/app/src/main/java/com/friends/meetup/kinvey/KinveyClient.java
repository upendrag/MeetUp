package com.friends.meetup.kinvey;

import android.support.multidex.MultiDexApplication;

import com.kinvey.android.Client;

/**
 * Created by upendra on 3/27/16.
 */
public class KinveyClient extends MultiDexApplication {
    public static final String AUTHTOKEN_TYPE = "com.friends.meetup.auth_token";
    public final static String ACCOUNT_TYPE = "com.friends.meetup.account";

    private Client kinveyClient;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        this.kinveyClient = new Client.Builder(getApplicationContext()).build();
    }

    public Client getKinveyClient() {
        return this.kinveyClient;
    }
}
