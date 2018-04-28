package com.halal_face.powermeter;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

public class Atributions extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    TextView mpandroid;
    TextView icons8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atributions);

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

        mpandroid = findViewById(R.id.mpandroid);
        String linkText1 = "Graphs are created via the  <a href='https://github.com/PhilJay/MPAndroidChart'>MPAndroidChart</a> library.";
        mpandroid.setText(setHtml(linkText1));
        mpandroid.setMovementMethod(LinkMovementMethod.getInstance());

//        icons8 = findViewById(R.id.icons8);
//        String linkText2 = "App icon via  <a href='https://icons8.com/'>Icons8</a> library.";
//        icons8.setText(setHtml(linkText2));
//        icons8.setMovementMethod(LinkMovementMethod.getInstance());
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
        intent = new Intent(Atributions.this, MainActivity.class);
        switch (menuItem.getItemId()) {
            case R.id.home:
                System.out.println("MENU ITEM CLICKED " +"home" );
                break;
            case R.id.update_add:
                System.out.println("MENU ITEM CLICKED " +"update_add");
                intent = new Intent(Atributions.this, Add.class);
                break;

            case R.id.view_data:
                System.out.println("MENU ITEM CLICKED " +"view_data");
                intent = new Intent(Atributions.this, ViewData.class);
                break;

            case R.id.edit:
                System.out.println("MENU ITEM CLICKED " +"edit");
                intent = new Intent(Atributions.this, Edit.class);
                break;

            case R.id.pr:
                System.out.println("MENU ITEM CLICKED " +"pr");
                intent = new Intent(Atributions.this, Pr.class);
                break;
            case R.id.atributions:
                System.out.println("MENU ITEM CLICKED " +"pr");
                intent = new Intent(Atributions.this, Atributions.class);
                break;
        }
        startActivity(intent);
        return true;
    }

    @SuppressWarnings("deprecation")
    public Spanned setHtml(String html){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }

}
