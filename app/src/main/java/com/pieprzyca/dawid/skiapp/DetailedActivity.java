package com.pieprzyca.dawid.skiapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.pieprzyca.dawid.skiapp.data.DatabaseConfig;
import com.pieprzyca.dawid.skiapp.fragments.Cameras;
import com.pieprzyca.dawid.skiapp.fragments.Rating;
import com.pieprzyca.dawid.skiapp.fragments.ResortLocation;
import com.pieprzyca.dawid.skiapp.fragments.ResortWeather;
import com.pieprzyca.dawid.skiapp.fragments.Reviews;
import com.pieprzyca.dawid.skiapp.fragments.SkiResortInformation;
import com.pieprzyca.dawid.skiapp.fragments.SkiRouteMap;

public class DetailedActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences userPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle(getIntent().getStringExtra("resortName"));
        setSupportActionBar(toolbar);

        userPreferences = getSharedPreferences("log-in", Context.MODE_PRIVATE);
        Log.d("DetailedActivity USERID:", userPreferences.getString("user_id", ""));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(DetailedActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detailed, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d("SKIRESORTID : ", getIntent().getStringExtra("skiResortId"));
        DatabaseOperations.pullFavouriteResortInfo(getApplicationContext(), DatabaseConfig.IS_FAVOURITE_URL, getIntent().getStringExtra("skiResortId"), userPreferences.getString("user_id", ""), menu.findItem(R.id.favourite));
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
                Intent intent = new Intent(DetailedActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.favourite:
                if (item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_star_border_white_24dp).getConstantState())) {
                    DatabaseOperations.addFavouriteResort(getApplicationContext(), DatabaseConfig.ADD_FAVOURITE_URL, getIntent().getStringExtra("skiResortId"), userPreferences.getString("user_id", ""), item);
                } else {
                    DatabaseOperations.deleteFavouriteResort(getApplicationContext(), DatabaseConfig.REMOVE_FAVOURITE_URL, getIntent().getStringExtra("skiResortId"), userPreferences.getString("user_id", ""), item);
                }
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
            Intent home = new Intent(DetailedActivity.this, HomeActivity.class);
            startActivity(home);
        } else if (id == R.id.nav_search) {
            Intent search = new Intent(DetailedActivity.this, SearchActivity.class);
            startActivity(search);
        } else if (id == R.id.nav_messages) {

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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SkiResortInformation(), "Informacje");
        adapter.addFrag(new ResortWeather(), "Pogoda");
        adapter.addFrag(new Rating(), "Ocena");
        adapter.addFrag(new ResortLocation(), "Lokalizacja");
        adapter.addFrag(new SkiRouteMap(), "Mapa tras");
        adapter.addFrag(new Reviews(), "Opinie");
        adapter.addFrag(new Cameras(), "Kamery");
        viewPager.setAdapter(adapter);
    }
}


