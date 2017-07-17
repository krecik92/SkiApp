package com.pieprzyca.dawid.skiapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pieprzyca.dawid.skiapp.data.DatabaseConfig;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ListView listView;
    ResortInfoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.listView);
        adapter = new ResortInfoAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<ResortData>());
        new DownloadSkiResortNameList().execute(DatabaseConfig.LOGIN_REQUEST_URL);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detail = new Intent(Home.this, DetailedActivity.class);
                detail.putExtra("skiResortId", adapter.getItem(position).getSkiResortId().toString());
                detail.putExtra("resortName", adapter.getItem(position).getResortName());
                detail.putExtra("resortAddress", adapter.getItem(position).getResortAddress());
                startActivity(detail);
                adapter.clear();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            Intent intent = new Intent(Home.this, LoginActivity.class);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            Intent search = new Intent(Home.this, SearchActivity.class);
            startActivity(search);
            adapter.clear();
        } else if (id == R.id.nav_near) {

        } else if (id == R.id.nav_messages){

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private class DownloadSkiResortNameList extends AsyncTask<String, Integer, Long> {
        @Override
        protected Long doInBackground(String... params) {
                DatabaseOperations.fetchResortInfoFromDatabase(getApplicationContext() ,params[0], adapter);
                return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
        }
    }
}