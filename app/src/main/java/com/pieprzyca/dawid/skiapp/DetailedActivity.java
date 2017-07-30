package com.pieprzyca.dawid.skiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pieprzyca.dawid.skiapp.fragments.Reviews;
import com.pieprzyca.dawid.skiapp.fragments.SkiRouteMap;
import com.pieprzyca.dawid.skiapp.fragments.ResortLocation;
import com.pieprzyca.dawid.skiapp.fragments.SkiResortInformation;
import com.pieprzyca.dawid.skiapp.fragments.Rating;
import com.pieprzyca.dawid.skiapp.fragments.ResortWeather;

import org.jetbrains.annotations.NotNull;

public class DetailedActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("resortName"));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_logout:
                Intent intent = new Intent(DetailedActivity.this, LoginActivity.class);
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
    @NotNull
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
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SkiResortInformation(), "Informacje");
        adapter.addFrag(new ResortWeather(), "Pogoda");
        adapter.addFrag(new Rating(), "Ocena");
        adapter.addFrag(new ResortLocation(), "Lokalizacja");
        adapter.addFrag(new SkiRouteMap(), "Mapa tras");
        adapter.addFrag(new Reviews(), "Opinie");
        viewPager.setAdapter(adapter);
    }
}
