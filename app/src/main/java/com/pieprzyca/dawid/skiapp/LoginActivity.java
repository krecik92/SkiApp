package com.pieprzyca.dawid.skiapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        Button mEmailSignInButton = (Button) findViewById(R.id.login_button);
        final TextView registerNewUserButton = (TextView) findViewById(R.id.register_button);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null)
            Log.d("FIREBASE", firebaseAuth.getCurrentUser().getEmail());
        pref = getSharedPreferences("log-in", Context.MODE_PRIVATE);
        editor = pref.edit();

        assert mEmailSignInButton != null;
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        assert registerNewUserButton != null;
        registerNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }
    /**
     * Attempts to sign in the account specified by the login form.
     * Show message if we put failed login and password.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();
        if (validateFields(email, password)) return;
        signIn(email, password);
        showProgress(true);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    checkUserNameAndPassword(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Database connection error!", Toast.LENGTH_LONG).show();
                }
            }
        };
        RequestLogin requestLogin = new com.pieprzyca.dawid.skiapp.RequestLogin(email, password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(requestLogin);
    }

    private boolean validateFields(String email, String password) {
        if (email.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Email or Password is empty!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void checkUserNameAndPassword(String response) throws JSONException {
        JSONObject jsonResponse = new JSONObject(response);
        boolean isCorrect = jsonResponse.getBoolean("success");
        Log.d("VERIFY: ", String.valueOf(isCorrect));
        if (isCorrect) {
            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
            int user_id = jsonResponse.getInt("user_id");
            editor.putString("user_id", String.valueOf(user_id));
            editor.apply();
            startActivity(homeIntent);
            showProgress(false);
        }else{
            Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
            showProgress(false);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
    private void signIn(String email, String password){
        Log.d("FIREBASE: ", "signIn: " + email);
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("FIREBASE: ", "signIn: " + "success");
                        }else {
                            Log.d("FIREBASE: ", "signIn: " + "failure");
                        }
                    }
                });
    }
}
