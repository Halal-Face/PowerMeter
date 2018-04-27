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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class Add extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    Button add;
    EditText edit;
    DataBaseHelperM mDataBaseHelperM;
    Random rand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

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
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        Intent intent;
                        intent = new Intent(Add.this, MainActivity.class);
                        switch (menuItem.getItemId()) {
                            case R.id.home:
                                System.out.println("MENU ITEM CLICKED " +"home" );
                                intent = new Intent(Add.this, MainActivity.class);
                                break;
                            case R.id.update_add:
                                System.out.println("MENU ITEM CLICKED " +"update_add");
                                intent = new Intent(Add.this, UpdateAdd.class);
                                break;

                            case R.id.view_data:
                                System.out.println("MENU ITEM CLICKED " +"view_data");
                                intent = new Intent(Add.this, ViewData.class);
                                break;

                            case R.id.edit:
                                System.out.println("MENU ITEM CLICKED " +"edit");
                                intent = new Intent(Add.this, Edit.class);
                                break;

                            case R.id.pr:
                                System.out.println("MENU ITEM CLICKED " +"pr");
                                intent = new Intent(Add.this, Pr.class);
                                break;
                        }
                        startActivity(intent);
                        return true;
                    }
                });

        add = findViewById(R.id.add);
        edit = findViewById(R.id.edit);
        //get a reference to the database
        mDataBaseHelperM = new DataBaseHelperM(this, "Exercise_Database");
        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String exercise = edit.getText().toString();
                if(exercise!=null && !exercise.isEmpty()){
                    AddData(exercise.replaceAll(" ", "_"));
                }else{
                    toastM("Please enter an exercise");
                }

            }
        });



    }

    //insert into the database
    public void AddData(String newEntry){
        boolean insert = mDataBaseHelperM.addData(newEntry);
        if(insert){
            toastM("Success");

        }else{
            toastM("Exercise already exists");
        }
    }
    private void toastM(String m){
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
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

}