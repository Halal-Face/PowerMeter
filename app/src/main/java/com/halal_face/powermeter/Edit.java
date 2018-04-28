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
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Edit extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    MasterDbHelper mMasterDbHelper;
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

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
                        intent = new Intent(Edit.this, MainActivity.class);
                        switch (menuItem.getItemId()) {
                            case R.id.home:
                                System.out.println("MENU ITEM CLICKED " +"home" );
                                intent = new Intent(Edit.this, MainActivity.class);
                                break;
                            case R.id.update_add:
                                System.out.println("MENU ITEM CLICKED " +"update_add");
                                intent = new Intent(Edit.this, Add.class);
                                break;

                            case R.id.view_data:
                                System.out.println("MENU ITEM CLICKED " +"view_data");
                                intent = new Intent(Edit.this, ViewData.class);
                                break;

                            case R.id.edit:
                                System.out.println("MENU ITEM CLICKED " +"edit");
                                intent = new Intent(Edit.this, Edit.class);
                                return true;

                            case R.id.pr:
                                System.out.println("MENU ITEM CLICKED " +"pr");
                                intent = new Intent(Edit.this, Pr.class);
                                break;
                        }
                        startActivity(intent);
                        return true;
                    }
                });
        mMasterDbHelper = new MasterDbHelper(this, "Exercise_Database");
        mListView = findViewById(R.id.listView);

        populateListView();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Cursor data = mMasterDbHelper.getItemID(item);
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID>-1){
                    Intent editDbIntent = new Intent(Edit.this, EditDb.class);
                    editDbIntent.putExtra("id", itemID);
                    editDbIntent.putExtra("item", item);
                    startActivity(editDbIntent);

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
        mListView.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
