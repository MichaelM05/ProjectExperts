package com.mjb.projectexperts;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mjb.projectexperts.Domain.Route;
import com.mjb.projectexperts.Domain.Site;
import com.mjb.projectexperts.Domain.User;

import java.util.ArrayList;

import layout.LoginFragment;
import layout.MainFragment;
import layout.ModifyRouteFragment;
import layout.RegistryFragment;
import layout.RoutesFoundFragment;
import layout.SearchDestinationFragment;
import layout.WelcomeFragment;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Location lastLocation;
    public String[] parameters;
    public ArrayList<Route> routeList;
    public ArrayList<Route> preRouteList;
    public User user;
    public ArrayList<Site> sites;
    public ArrayList<Site> sitesCreate = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MainFragment mainFragment = new MainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, mainFragment, "mainFragment");
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_main) {
            MainFragment mainFragment = new MainFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, mainFragment, "mainFragment");
            ft.addToBackStack("mainFragment");
            ft.commit();
        }
        else if (id == R.id.nav_login) {
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, loginFragment, "loginFragment");
            ft.addToBackStack("loginFragment");
            ft.commit();
        } else if (id == R.id.nav_register) {
            RegistryFragment registryFragment = new RegistryFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, registryFragment, "registryFragment");
            ft.addToBackStack("registryFragment");
            ft.commit();
        } else if (id == R.id.nav_search_route) {
            SearchDestinationFragment searchDestinationFragment = new SearchDestinationFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, searchDestinationFragment, "searchDestinationFragment");
            ft.addToBackStack("searchDestinationFragment");
            ft.commit();
        } else if (id == R.id.nav_pre_route) {

            RoutesFoundFragment routesFoundFragment = new RoutesFoundFragment();
            routesFoundFragment.routeList = preRouteList;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, routesFoundFragment, "routesFoundFragment");
            ft.addToBackStack("routesFoundFragment");
            ft.commit();
        } else if(id == R.id.nav_welcome_client){
            WelcomeFragment welcomeFragment = new WelcomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, welcomeFragment, "welcomeFragment");
            ft.addToBackStack("welcomeFragment");
            ft.commit();
        } else if(id == R.id.nav_routes_client){
            ModifyRouteFragment modifyRouteFragment = new ModifyRouteFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, modifyRouteFragment, "modifyRouteFragment");
            ft.addToBackStack("welcomeFragment");
            ft.commit();
        }else if(id == R.id.nav_signout){
            MainFragment mainFragment = new MainFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, mainFragment, "mainFragment");
            ft.addToBackStack("mainFragment");
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_register).setVisible(true);
            nav_Menu.findItem(R.id.nav_routes_client).setVisible(false);
            nav_Menu.findItem(R.id.nav_signout).setVisible(false);
            nav_Menu.findItem(R.id.nav_welcome_client).setVisible(false);
            ft.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

 
}
