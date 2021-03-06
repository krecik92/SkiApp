package com.pieprzyca.dawid.skiapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.pieprzyca.dawid.skiapp.arrayAdapters.ResortInfoAdapter;
import com.pieprzyca.dawid.skiapp.data.DatabaseConfig;
import com.pieprzyca.dawid.skiapp.data.ResortData;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView listView;
    ResortInfoAdapter adapter;
    List<ResortData> resortDataList = new ArrayList<>();
    private SharedPreferences userPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userPreferences = getSharedPreferences("log-in", Context.MODE_PRIVATE);
        Log.d("HomeActivity USER_ID:", userPreferences.getString("user_id", ""));

        listView = (ListView)findViewById(R.id.listView);
        adapter = new ResortInfoAdapter(this, android.R.layout.simple_list_item_1, resortDataList);
        new DownloadSkiResortNameList().execute(DatabaseConfig.FAVOURITE_SKIRESORTS_URL);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detail = new Intent(HomeActivity.this, DetailedActivity.class);
                putInfoToNextIntent(position, detail);
                startActivity(detail);
                adapter.clear();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void putInfoToNextIntent(int position, Intent detail) {
        detail.putExtra("skiResortId", adapter.getItem(position).getSkiResortId().toString());
        detail.putExtra("resortName", adapter.getItem(position).getResortName());
        detail.putExtra("resortAddress", adapter.getItem(position).getResortAddress());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_logout:
                Log.d("FIREBASE : ", "Try signout");
                FirebaseAuth.getInstance().signOut();
                Log.d("FIREBASE : ", "After signout");
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case  R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    /**
     *
     @ parameter item MenuItem
     *
     */
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_skiresort) {

        } else if (id == R.id.nav_search) {
            Intent search = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(search);
            adapter.clear();
        } else if (id == R.id.nav_messages){
            Intent messangerIntent = new Intent(HomeActivity.this, MessangerActivity.class);
            startActivity(messangerIntent);
            adapter.clear();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.clear();
        Log.d("HomeActivity", "SignOut");
        FirebaseAuth.getInstance().signOut();
    }

    private class DownloadSkiResortNameList extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            DatabaseOperations.fetchResortsFromDatabase(getApplicationContext(), params[0], userPreferences.getString("user_id", "0"), adapter);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean value) {
        }
    }
}