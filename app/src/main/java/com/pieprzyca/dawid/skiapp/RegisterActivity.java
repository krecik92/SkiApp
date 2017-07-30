package com.pieprzyca.dawid.skiapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUserName = (EditText) findViewById(R.id.etUserName);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button buttonRegister = (Button) findViewById(R.id.send_register_button);
        final Button buttonCancel = (Button) findViewById(R.id.cancel_register_button);

        /**
         * Request for register informations in userInformation table for database. If it will be fault, we will see alertDialog.
         */
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInformation userInformation = new UserInformation(etName, etUserName, etEmail, etPassword).invoke();
                String name = userInformation.getName();
                String userName = userInformation.getUserName();
                String email = userInformation.getEmail();
                String password = userInformation.getPassword();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            tryRegisterUser(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RequestRegister requestRegister = new RequestRegister(name, userName, email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(requestRegister);
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
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

    private class UserInformation {
        private EditText etName;
        private EditText etUserName;
        private EditText etEmail;
        private EditText etPassword;
        private String name;
        private String userName;
        private String email;
        private String password;

        public UserInformation(EditText etName, EditText etUserName, EditText etEmail, EditText etPassword) {
            this.etName = etName;
            this.etUserName = etUserName;
            this.etEmail = etEmail;
            this.etPassword = etPassword;
        }

        public String getName() {
            return name;
        }

        public String getUserName() {
            return userName;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public UserInformation invoke() {
            name = etName.getText().toString();
            userName = etUserName.getText().toString();
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
            return this;
        }
    }
}
