package com.friends.meetup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.friends.meetup.R;
import com.friends.meetup.entities.MeetUpUser;
import com.friends.meetup.kinvey.KinveyClient;
import com.kinvey.android.AsyncUser;
import com.kinvey.android.Client;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyCancellableCallback;

public class SignUpActivity extends AppCompatActivity {
    // UI references
    private EditText mUsernameET;
    private EditText mFirstNameET;
    private EditText mLastNameET;
    private EditText mPhoneET;
    private EditText mPasswordET;
    private EditText mConfirmPasswordET;

    private Client kinveyClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // init UI references
        mUsernameET = (EditText) findViewById(R.id.et_reg_username);
        mFirstNameET= (EditText) findViewById(R.id.et_first_name);
        mLastNameET = (EditText) findViewById(R.id.et_last_name);
        mPhoneET = (EditText) findViewById(R.id.et_reg_phone);
        mPasswordET = (EditText) findViewById(R.id.et_reg_password);
        mConfirmPasswordET = (EditText) findViewById(R.id.et_reg_confirm_password);

        // init kinvey client
        kinveyClient = ((KinveyClient)getApplication()).getKinveyClient();
    }
    public void registerAccount(View view) {
        if (!validateFields())
            return;

        // logout active user
        if (kinveyClient.user().isUserLoggedIn())
            kinveyClient.user().logout().execute();

        // create a user with unique field
        kinveyClient.user().create(mUsernameET.getText().toString().trim(),
                mPasswordET.getText().toString(), new KinveyCancellableCallback<User>() {
                    @Override
                    public boolean isCancelled() {
                        return false;
                    }

                    @Override
                    public void onCancelled() {

                    }

                    @Override
                    public void onSuccess(User user) {
                        AsyncUser asyncUser = SignUpActivity.this.kinveyClient.user();
                        asyncUser.put(MeetUpUser.KEY_FIRST_NAME, mFirstNameET.getText().toString().trim());
                        asyncUser.put(MeetUpUser.KEY_LAST_NAME, mLastNameET.getText().toString().trim());
                        asyncUser.put(MeetUpUser.KEY_PHONE, mPhoneET.getText().toString().trim());

                        // update non unique fields for created user
                        asyncUser.update(new KinveyCancellableCallback<User>() {
                            @Override
                            public boolean isCancelled() {
                                return false;
                            }

                            @Override
                            public void onCancelled() {

                            }

                            @Override
                            public void onSuccess(User user) {
                                String info = "Registration successful: Please login to continue";
                                Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                SignUpActivity.this.startActivity(intent);
                                SignUpActivity.this.finish();
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                showErrorRegistrationFailed(throwable.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        showErrorRegistrationFailed(throwable.getMessage());
                    }
                });
    }

    private void showErrorRegistrationFailed(String err) {
        String text = "User registration failed: " + err;
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    private boolean validateFields() {
        if (mUsernameET.getText().toString().trim().isEmpty()) {
            showErrorCannotBeEmpty(mUsernameET.getHint().toString());
            return false;
        }
        if (mFirstNameET.getText().toString().trim().isEmpty()) {
            showErrorCannotBeEmpty(mFirstNameET.getHint().toString());
            return false;
        }
        if (mLastNameET.getText().toString().trim().isEmpty()) {
            showErrorCannotBeEmpty(mLastNameET.getHint().toString());
            return false;
        }
        if (mPhoneET.getText().toString().trim().isEmpty()) {
            showErrorCannotBeEmpty(mPhoneET.getHint().toString());
            return false;
        }
        if (mPasswordET.getText().toString().isEmpty()) {
            showErrorCannotBeEmpty(mPasswordET.getHint().toString());
            return false;
        }
        if (!mConfirmPasswordET.getText().toString().equals(mPasswordET.getText().toString())){
            showValidationError("Passwords do not match!");
            return false;
        }

        return true;
    }

    private void showErrorCannotBeEmpty(String s) {
        showValidationError(s + " cannot be empty!");
    }

    private void showValidationError(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
