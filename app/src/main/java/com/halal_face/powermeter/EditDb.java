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

public class EditDb extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    MasterDbHelper mMasterDbHelper;
    PowerDbHelper pPowerDbHelper;
    PrDbHelper mPrDbHelper;
    Button btnUpdate, btnDelete;
    EditText editText;
    String item;
    int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_db);

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
        editText = findViewById(R.id.edit);
        mMasterDbHelper = new MasterDbHelper(this, "Exercise_Database");
        Intent receiveIntent = getIntent();
        item = receiveIntent.getStringExtra("item");
        itemID = receiveIntent.getIntExtra("id", -1);

        editText.setHint(item);
        final Intent backToEditIntent = new Intent(EditDb.this, Edit.class);
        btnUpdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String newItem = editText.getText().toString().replaceAll(" ", "_");
                if(newItem!=null && !newItem.isEmpty()){
                    toastM("Changing " + item +" to " + newItem);
                    if(mMasterDbHelper.updateItem(newItem, itemID, item)) {
                        pPowerDbHelper = new PowerDbHelper(EditDb.this, item);
                        pPowerDbHelper.updateDbName(newItem.replaceAll(" ", "_"));
                        //mPrDbHelper = new PrDbHelper(EditDb.this);
                        //mPrDbHelper.updateExerName(item, newItem.replaceAll(" ", "_"));
                        EditDb.this.deleteDatabase(item);
                    }
                    startActivity(backToEditIntent);
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mMasterDbHelper.deleteItem(itemID, item);
                editText.setText("");
                EditDb.this.deleteDatabase(item);
                startActivity(backToEditIntent);
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
        intent = new Intent(EditDb.this, MainActivity.class);
        switch (menuItem.getItemId()) {
            case R.id.home:
                //System.out.println("MENU ITEM CLICKED " +"home" );
                break;
            case R.id.update_add:
                //System.out.println("MENU ITEM CLICKED " +"update_add");
                intent = new Intent(EditDb.this, Add.class);
                break;

            case R.id.view_data:
                //System.out.println("MENU ITEM CLICKED " +"view_data");
                intent = new Intent(EditDb.this, ViewData.class);
                break;

            case R.id.edit:
                //System.out.println("MENU ITEM CLICKED " +"edit");
                intent = new Intent(EditDb.this, Edit.class);
                break;

            case R.id.pr:
                //System.out.println("MENU ITEM CLICKED " +"pr");
                intent = new Intent(EditDb.this, Pr.class);
                break;
            case R.id.atributions:
                //System.out.println("MENU ITEM CLICKED " +"Attributions");
                intent = new Intent(EditDb.this, Atributions.class);
                break;
        }
        startActivity(intent);
        return true;
    }

}
