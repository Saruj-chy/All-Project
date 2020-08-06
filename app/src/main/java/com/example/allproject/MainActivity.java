package com.example.allproject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.example.allproject.Activity.CollapsibleActivity;
import com.example.allproject.Activity.FragmentActivity;
import com.example.allproject.Activity.LifeCycleActivity;
import com.example.allproject.Activity.RecyclerViewActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    TextView appName;
    ActionBarDrawerToggle drawerToggle;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Project");




        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.header);

        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        String itemName = (String) item.getTitle();

        // tvInfo.setText(itemName);

        closeDrawer();

        switch (item.getItemId()){
            case R.id.item_a:
                startActivity(new Intent(this, LifeCycleActivity.class));
                break;
            case R.id.item_b:
                startActivity(new Intent(this, FragmentActivity.class));
                break;
            case R.id.item_c:
                startActivity(new Intent(this, CollapsibleActivity.class));
                break;
            case R.id.item_d:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
//            case R.id.item_e:
//                startActivity(new Intent(this, ChoiceActivity.class));
//                break;
//            case R.id.item_f:
//                startActivity(new Intent(this, UpploadPostActivity.class));
//                break;
//            case R.id.item_g:
//                startActivity(new Intent(this, DepartmentPostActivity.class));
//                break;

        }
        return false;
    }
    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    private void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            closeDrawer();
        }
        super.onBackPressed();
    }
}