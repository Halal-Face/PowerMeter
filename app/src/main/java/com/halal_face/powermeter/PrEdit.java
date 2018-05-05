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

public class PrEdit extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    PrDbHelper mPrDbHelper;
    Button btnUpdate;
    Button btnDelete;
    EditText editText;
    String name;
    int newPr = -1;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pr_edit);

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

        btnUpdate = findViewById(R.id.update);
        btnDelete = findViewById(R.id.delete);
        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.edit);
        mPrDbHelper = new PrDbHelper(this);
        populateListView();
        Intent receiveIntent = getIntent();
        name = receiveIntent.getStringExtra("exerName");

        editText.setHint(name);
        final Intent backToPr = new Intent(PrEdit.this, Pr.class);

        btnUpdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                newPr = Integer.parseInt(editText.getText().toString());
                if(newPr>0){
                    if(mPrDbHelper.addData(name,newPr)) {
                        toastM("Adding new PR " + newPr +" to " + name);
                    }
                    startActivity(backToPr);
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrDbHelper.deleteItem(name);
                editText.setText("");
                startActivity(backToPr);
            }
        });
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
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean nav(MenuItem menuItem){
        // set item as selected to persist highlight
        menuItem.setChecked(true);
        // close drawer when item is tapped
        mDrawerLayout.closeDrawers();
        Intent intent;
        intent = new Intent(PrEdit.this, MainActivity.class);
        switch (menuItem.getItemId()) {
            case R.id.home:
                //System.out.println("MENU ITEM CLICKED " +"home" );
                break;
            case R.id.update_add:
                //System.out.println("MENU ITEM CLICKED " +"update_add");
                intent = new Intent(PrEdit.this, Add.class);
                break;

            case R.id.view_data:
                //System.out.println("MENU ITEM CLICKED " +"view_data");
                intent = new Intent(PrEdit.this, ViewData.class);
                break;

            case R.id.edit:
                //System.out.println("MENU ITEM CLICKED " +"edit");
                intent = new Intent(PrEdit.this, Edit.class);
                break;

            case R.id.pr:
                //System.out.println("MENU ITEM CLICKED " +"pr");
                intent = new Intent(PrEdit.this, Pr.class);
                break;
            case R.id.atributions:
                //System.out.println("MENU ITEM CLICKED " +"Attributions");
                intent = new Intent(PrEdit.this, Atributions.class);
                break;
        }
        startActivity(intent);
        return true;
    }
    public void populateListView(){
        //get iterator for data
        Cursor data = mPrDbHelper.getData();
        //add the data from to the arraylsit
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1)+"    "+data.getInt(2));
        }
        //used to populate the listview
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
    }

}
