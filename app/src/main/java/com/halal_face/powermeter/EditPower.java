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

public class EditPower extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    PowerDbHelper pPowerDbHelper;
    Button btnUpdate, btnDelete;
    EditText editText;
    String TABLE_NAME;
    int itemID;
    Intent backToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_power);

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

        backToAdd = new Intent(EditPower.this, Add.class);
        Intent receiveIntent = getIntent();
        TABLE_NAME = receiveIntent.getStringExtra("TABLE_NAME");


        itemID = receiveIntent.getIntExtra("id", -1);

        pPowerDbHelper = new PowerDbHelper(this, TABLE_NAME);


        btnUpdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(editText!=null && editText.getText().toString()!=""){
                    System.out.println("NAME: " + TABLE_NAME);
                    int newItem = Integer.parseInt(editText.getText().toString());
                    pPowerDbHelper.updateItem(newItem, itemID);
                    startActivity(backToAdd);
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                pPowerDbHelper.deleteItem(itemID);
                editText.setText("");
                startActivity(backToAdd);
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
        intent = new Intent(EditPower.this, MainActivity.class);
        switch (menuItem.getItemId()) {
            case R.id.home:
                //System.out.println("MENU ITEM CLICKED " +"home" );
                break;
            case R.id.update_add:
                //System.out.println("MENU ITEM CLICKED " +"update_add");
                intent = new Intent(EditPower.this, Add.class);
                break;

            case R.id.view_data:
                //System.out.println("MENU ITEM CLICKED " +"view_data");
                intent = new Intent(EditPower.this, ViewData.class);
                break;

            case R.id.edit:
                //System.out.println("MENU ITEM CLICKED " +"edit");
                intent = new Intent(EditPower.this, Edit.class);
                break;

            case R.id.pr:
                //System.out.println("MENU ITEM CLICKED " +"pr");
                intent = new Intent(EditPower.this, Pr.class);
                break;
            case R.id.atributions:
                //System.out.println("MENU ITEM CLICKED " +"Attributions");
                intent = new Intent(EditPower.this, Atributions.class);
                break;
        }
        startActivity(intent);
        return true;
    }

}
