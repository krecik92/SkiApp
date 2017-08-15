package com.pieprzyca.dawid.skiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etSurname = (EditText) findViewById(R.id.etSurName);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button buttonRegister = (Button) findViewById(R.id.send_register_button);
        final Button buttonCancel = (Button) findViewById(R.id.cancel_register_button);


        /**
         * Request for store user data in "userInformation" table. If it will be fault, we will see alertDialog.
         */
        assert buttonRegister != null;
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInformation userInformation = new UserInformation(etName, etSurname, etEmail, etPassword).invoke();
                final String name = userInformation.getName();
                final String surname = userInformation.getSurname();
                final String email = userInformation.getEmail();
                final String password = userInformation.getPassword();

                if (validateEmptyFields(name, surname, email, password)) return;
                if (validatePassword(password)) return;

                createAccount(email, password, name, surname);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            tryRegisterUser(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error during store user!", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                RequestRegister requestRegister = new RequestRegister(name, surname, email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(requestRegister);
            }
        });
        assert buttonCancel != null;
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validatePassword(String password) {
        if (password.length() < 8) {
            Toast.makeText(getApplicationContext(), "Password is too short!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean validateEmptyFields(String name, String surname, String email, String password) {
        if (name.equals("") || surname.equals("") || email.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "You have empty field!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void tryRegisterUser(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        boolean success = jsonObject.getBoolean("success");

        if(success){
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            RegisterActivity.this.startActivity(intent);

        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Register failed")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
        }
    }

    private void createAccount(final String email, final String password, final String name, final String surname) {
        Log.d("FIREBASE", "createAccount");
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User is created!", Toast.LENGTH_LONG).show();
                            Log.d("FIREBASE", "Authentication success!");
                            setUserName(name, surname);
                        }
                        else{
                            Log.w("FIREBASE", "createUserWithEmail : failure ", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void setUserName(String name, String surname) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(name + " " + surname)
                .build();

        assert user != null;
        user.updateProfile(profileChangeRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("FIREBASE: ", "User profile updated.");
                        } else {
                            Log.d("FIREBASE: ", "Unable to profile updated.");
                        }
                    }
                });
    }

    private class UserInformation {
        private EditText etName;
        private EditText etSurname;
        private EditText etEmail;
        private EditText etPassword;
        private String name;
        private String surname;
        private String email;
        private String password;

        private UserInformation(EditText etName, EditText etSurname, EditText etEmail, EditText etPassword) {
            this.etName = etName;
            this.etSurname = etSurname;
            this.etEmail = etEmail;
            this.etPassword = etPassword;
        }

        public String getName() {
            return name;
        }

        private String getSurname() {
            return surname;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        private UserInformation invoke() {
            name = etName.getText().toString();
            surname = etSurname.getText().toString();
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
            return this;
        }
    }
}