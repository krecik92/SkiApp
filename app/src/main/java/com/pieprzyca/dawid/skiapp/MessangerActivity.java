package com.pieprzyca.dawid.skiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.pieprzyca.dawid.skiapp.data.MessageInstance;

/**
 * Created by Dawid on 30.07.2017.
 * Activity które odpowiada za wyświetalnie i obsługe Chat w aplikacji.
 */

public class MessangerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private FirebaseListAdapter<MessageInstance> firebaseListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messanger);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.listViewForMessanger);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this,
                    "Invalid sig-in user",
                    Toast.LENGTH_LONG)
                    .show();
        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast
            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_LONG)
                    .show();
        }

        final EditText inputMessage = (EditText) findViewById(R.id.input_message);
        assert inputMessage != null;
        inputMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    assert inputMessage.getText() != null;
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .push()
                            .setValue(new MessageInstance(inputMessage.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
                    inputMessage.setText("");
                    handled = true;
                }
                return handled;
            }
        });
        displayMessages();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_my_skiresort) {
            Intent home = new Intent(MessangerActivity.this, HomeActivity.class);
            startActivity(home);
            adapter.clear();
        } else if (id == R.id.nav_search) {
            Intent search = new Intent(MessangerActivity.this, SearchActivity.class);
            startActivity(search);
            adapter.clear();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                Log.d("FIREBASE : ", "Try signout");
                FirebaseAuth.getInstance().signOut();
                Log.d("FIREBASE : ", "After signout");
                Intent intent = new Intent(MessangerActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case  R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseDatabase.getInstance().getReference().setValue(null);
        FirebaseAuth.getInstance().signOut();
    }

    private void displayMessages(){
        ListView listOfMessages = (ListView) findViewById(R.id.listViewForMessanger);

        firebaseListAdapter = new FirebaseListAdapter<MessageInstance>(this, MessageInstance.class, R.layout.message_item, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, MessageInstance model, int position) {
                TextView messageTextView = (TextView) v.findViewById(R.id.message_text);
                TextView messageUserView = (TextView) v.findViewById(R.id.message_user);
                TextView messageTimeView = (TextView) v.findViewById(R.id.message_time);

                messageTextView.setText(model.getTextMessage());
                messageUserView.setText(model.getMessageUser());
                messageTimeView.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMesssageTime()));
            }

            @Override
            protected void onCancelled(DatabaseError databaseError) {
                super.onCancelled(databaseError);
            }
        };
        assert listOfMessages != null;
        listOfMessages.setAdapter(firebaseListAdapter);
    }
}
