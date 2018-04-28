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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddPower extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    PowerDbHelper pPowerDbHelper;
    ListView listView;
    Button addPower;
    EditText editWeight;
    EditText editSet;
    String TABLE_NAME;
    int weight;
    int sets;
    int power;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_power);

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

                listView = findViewById(R.id.listView);
                addPower = findViewById(R.id.add);
                editWeight = findViewById(R.id.plate);
                editWeight.setHint("Total Weight");
                editSet = findViewById(R.id.set);
                editSet.setHint("Total Sets");

                Intent receiveIntent= getIntent();
                TABLE_NAME = receiveIntent.getStringExtra("TABLE_NAME");
                System.out.println("TABLE NAME: " + TABLE_NAME);
                pPowerDbHelper = new PowerDbHelper(AddPower.this, TABLE_NAME);


                populateListView();


                addPower.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!editSet.getText().toString().matches("") && editSet.getText().toString()!=null && !editWeight.getText().toString().matches("") && editWeight.getText().toString()!=null){
                            weight = Integer.parseInt(editWeight.getText().toString());
                            sets = Integer.parseInt(editSet.getText().toString());
                            power = weight * sets;
                            if(power>-1 && weight>-1 && sets>-1) {
                                toastM("Adding " + power + " to " + TABLE_NAME);
                                pPowerDbHelper.addData(power);
                                finish();
                                startActivity(getIntent());
                            }
                        }else{
                            toastM("Please Enter Valid Weight and Set Values");
                        }
                    }
                });
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

    public void populateListView(){
        //get iterator for data
        Cursor data = pPowerDbHelper.getData();
        //add the data from to the arraylsit
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
        }
        //used to populate the listview
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
    }

    public boolean nav(MenuItem menuItem){
        // set item as selected to persist highlight
        menuItem.setChecked(true);
        // close drawer when item is tapped
        mDrawerLayout.closeDrawers();
        Intent intent;
        intent = new Intent(AddPower.this, MainActivity.class);
        switch (menuItem.getItemId()) {
            case R.id.home:
                //System.out.println("MENU ITEM CLICKED " +"home" );
                break;
            case R.id.update_add:
                //System.out.println("MENU ITEM CLICKED " +"update_add");
                intent = new Intent(AddPower.this, Add.class);
                break;

            case R.id.view_data:
                //System.out.println("MENU ITEM CLICKED " +"view_data");
                intent = new Intent(AddPower.this, ViewData.class);
                break;

            case R.id.edit:
                //System.out.println("MENU ITEM CLICKED " +"edit");
                intent = new Intent(AddPower.this, Edit.class);
                break;

            case R.id.pr:
                //System.out.println("MENU ITEM CLICKED " +"pr");
                intent = new Intent(AddPower.this, Pr.class);
                break;
            case R.id.atributions:
                //System.out.println("MENU ITEM CLICKED " +"Attributions");
                intent = new Intent(AddPower.this, Atributions.class);
                break;
        }
        startActivity(intent);
        return true;
    }
    private void toastM(String m){
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }

}
