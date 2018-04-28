package com.halal_face.powermeter;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Add extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    Button add;
    EditText edit;
    MasterDbHelper mMasterDbHelper;
    ListView listView;

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
        mMasterDbHelper = new MasterDbHelper(this, "Exercise_Database");
        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String exercise = edit.getText().toString();
                if(exercise!=null && !exercise.isEmpty()){
                    AddData(exercise.replaceAll(" ", "_"));
                    PowerDbHelper pPowerDbHelper = new PowerDbHelper(Add.this, exercise.replaceAll(" ", "_"));
                    finish();
                    startActivity(getIntent());
                }else{
                    toastM("Please enter an exercise");
                }

            }
        });

       listView = findViewById(R.id.listView);
        populateListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Cursor data = mMasterDbHelper.getItemID(item);
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID>-1){
                    Intent AddPowerIntent = new Intent(Add.this, AddPower.class);
                    AddPowerIntent.putExtra("TABLE_NAME", item);
                    startActivity(AddPowerIntent);

                }
            }
        });


    }

    public void populateListView(){
        //get iterator for data
        Cursor data = mMasterDbHelper.getData();
        //add the data from to the arraylsit
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
        }
        //used to populate the listview
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
    }

    //insert into the database
    public void AddData(String newEntry){
        boolean insert = mMasterDbHelper.addData(newEntry);
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