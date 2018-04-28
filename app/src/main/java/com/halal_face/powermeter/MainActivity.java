package com.halal_face.powermeter;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        return nav(menuItem);
                    }
                });

        description = findViewById(R.id.description);
        description.setText("PowerMeter is an app designed to track the relative power exerted over time when exercising. Currently only exercises that are quantifiable by weight and sets are supported.");

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.update_add:
                System.out.println("MENU ITEM CLICKED " );
                return true;
            case R.id.view_data:
                //do something
                return true;
            case R.id.edit:
                //do something
                return true;
            case R.id.pr:
                //do something
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean nav(MenuItem menuItem){
        // set item as selected to persist highlight
        menuItem.setChecked(true);
        // close drawer when item is tapped
        mDrawerLayout.closeDrawers();
        Intent intent;
        intent = new Intent(MainActivity.this, MainActivity.class);
        switch (menuItem.getItemId()) {
            case R.id.home:
                System.out.println("MENU ITEM CLICKED " +"home" );
                break;
            case R.id.update_add:
                System.out.println("MENU ITEM CLICKED " +"update_add");
                intent = new Intent(MainActivity.this, Add.class);
                break;

            case R.id.view_data:
                System.out.println("MENU ITEM CLICKED " +"view_data");
                intent = new Intent(MainActivity.this, ViewData.class);
                break;

            case R.id.edit:
                System.out.println("MENU ITEM CLICKED " +"edit");
                intent = new Intent(MainActivity.this, Edit.class);
                break;

            case R.id.pr:
                System.out.println("MENU ITEM CLICKED " +"pr");
                intent = new Intent(MainActivity.this, Pr.class);
                break;
            case R.id.atributions:
                System.out.println("MENU ITEM CLICKED " +"pr");
                intent = new Intent(MainActivity.this, Atributions.class);
                break;
        }
        startActivity(intent);
        return true;
    }

}
