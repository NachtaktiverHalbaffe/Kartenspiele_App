package com.example.nachtaktiverhalbaffe.kartenspiele;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import static utils.Einstellungen_Fragment.NIGHT_MODE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Night Mode
        if(mPrefs.getString(NIGHT_MODE,"AUTO").equals("NIGHT")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else if(mPrefs.getString(NIGHT_MODE,"AUTO").equals("DAY")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else if(mPrefs.getString(NIGHT_MODE,"AUTO").equals("AUTO")){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        //Startfragment
        FragmentManager fm= getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.Fragment,new Spiele_finden()).commit();
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment, new Einstellungen()).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm= getSupportFragmentManager();

        if (id == R.id.nav_golfen){
            fm.beginTransaction().replace(R.id.Fragment,new Golfen()).commit();
        } else if (id == R.id.nav_arschloch) {
            fm.beginTransaction().replace(R.id.Fragment,new Arschloch()).commit();
        } else if (id == R.id.nav_shithead) {
            fm.beginTransaction().replace(R.id.Fragment, new Shithead()).commit();
        } else if (id == R.id.nav_schlafmuetze){
            fm.beginTransaction().replace(R.id.Fragment,new Schlafmuetze()).commit();
        } else if (id == R.id.nav_schwimmen) {
            fm.beginTransaction().replace(R.id.Fragment, new Schwimmen()).commit();
        } else if(id == R.id.nav_skat){
            fm.beginTransaction().replace(R.id.Fragment,new Skat()).commit();
        } else if(id== R.id.nav_durak){
            fm.beginTransaction().replace(R.id.Fragment, new Durak()).commit();
        } else if (id == R.id.nav_einstellungen){
            fm.beginTransaction().replace(R.id.Fragment,new Einstellungen()).commit();
        } else if (id== R.id.nav_suchen){
            fm.beginTransaction().replace(R.id.Fragment,new Spiele_finden()).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
