package com.friends.meetup.kinvey;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by upendra on 3/27/16.
 */
public class MeetUpAuthenticationService extends Service {
    private static final String TAG = "AccountAuthenticatorService";
    private static MeetUpAccountAuthenticator sAccountAuthenticator = null;

    private MeetUpAccountAuthenticator getAuthenticator() {
        if (sAccountAuthenticator == null)
            sAccountAuthenticator = new MeetUpAccountAuthenticator(this);

        return sAccountAuthenticator;
    }

    @Override
    public IBinder onBind(Intent intent) {
        IBinder binder = null;
        if (intent.getAction().equals(AccountManager.ACTION_AUTHENTICATOR_INTENT)){
            binder = new MeetUpAccountAuthenticator(this).getIBinder();
        }
        return binder;
    }
}
