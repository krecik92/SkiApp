package com.pieprzyca.dawid.skiapp;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.pieprzyca.dawid.skiapp.arrayAdapters.ResortInfoAdapter;
import com.pieprzyca.dawid.skiapp.data.DatabaseConfig;
import com.pieprzyca.dawid.skiapp.data.ResortData;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ResortInfoAdapter adapter;
    List<ResortData> resortDataList;
    ListView listView;
    private SharedPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Intent searchIntent = getIntent();
        if(Intent.ACTION_SEARCH.equals(searchIntent.getAction())){
            String query = searchIntent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        listView = (ListView) findViewById(R.id.listView);
        resortDataList = new ArrayList<>();
        adapter = new ResortInfoAdapter(this, android.R.layout.simple_list_item_1, resortDataList);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detail = new Intent(SearchActivity.this, DetailedActivity.class);
                putInfoToNextIntent(position, detail);
                startActivity(detail);
                adapter.clear();
            }
        });
        userPreferences = getSharedPreferences("log-in", Context.MODE_PRIVATE);
    }

    private void putInfoToNextIntent(int position, Intent detail) {
        detail.putExtra("skiResortId", adapter.getItem(position).getSkiResortId().toString());
        detail.putExtra("resortName", adapter.getItem(position).getResortName());
        detail.putExtra("resortAddress", adapter.getItem(position).getResortAddress());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView((searchItem));
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(getBaseContext(), SearchActivity.class);
        new DownloadSkiResortNameList().execute(DatabaseConfig.ALL_SKIRESORTS_REQUEST_URL);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName));
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        adapter.getFilter().filter(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText){
                        //adapter.getFilter().filter(newText);
                        return false;
                    }
                }
        );
        return super.onCreateOptionsMenu(menu);
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
                Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_skiresort) {
            Intent home = new Intent(SearchActivity.this, HomeActivity.class);
            startActivity(home);
        } else if (id == R.id.nav_search) {

        } else if (id == R.id.nav_messages) {
            Intent messanger = new Intent(SearchActivity.this, MessangerActivity.class);
            startActivity(messanger);
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
        FirebaseAuth.getInstance().signOut();
    }

    private class DownloadSkiResortNameList extends AsyncTask<String, Integer, Long> {
        @Override
        protected Long doInBackground(String... params) {
            DatabaseOperations.fetchResortsFromDatabase(getApplicationContext(), params[0], userPreferences.getString("user_id", "0"), adapter);
                return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {}
    }
}
