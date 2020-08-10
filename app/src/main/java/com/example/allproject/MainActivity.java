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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.allproject.Activity.CollapsibleActivity;
import com.example.allproject.Activity.EmployListActivity;
import com.example.allproject.Activity.FoodListJsonActivity;
import com.example.allproject.Activity.FragmentActivity;
import com.example.allproject.Activity.RecyclerViewActivity;
import com.example.allproject.Activity.SqLiteDatabaseActivity;
import com.example.allproject.Adapter.JSONAdapter;
import com.example.allproject.Adapter.ProductsAdapter;
import com.example.allproject.Class.Product;
import com.example.allproject.Constant.JsonArray;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    TextView appName;
    ActionBarDrawerToggle drawerToggle;

    private RecyclerView recyclerView ;
    List<Product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Project");

        recyclerView = findViewById(R.id.recyclerView) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        loadProducts();







        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.header);

        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();



    }

    private void loadProducts() {
        JSONObject jsonResponse;
        try {
            jsonResponse = new JSONObject(JsonArray.food_JSON);
            JSONArray movies = jsonResponse.getJSONArray("employ");
            for(int i=0;i<movies.length();i++){
                JSONObject movie = movies.getJSONObject(i);
                productList.add(new Product(
                        movie.getInt("id"),
                        movie.getString("food_name"),
                        movie.getDouble("price"),
                        movie.getString("resturant_name"),
                        movie.getString("rating"),
                        movie.getString("image")
                ));
            }
            JSONAdapter adapter = new JSONAdapter(getApplicationContext(), productList);
            recyclerView.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
                startActivity(new Intent(this, EmployListActivity.class));
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
            case R.id.item_e:
                startActivity(new Intent(this, SqLiteDatabaseActivity.class));
                break;
            case R.id.item_f:
                startActivity(new Intent(this, FoodListJsonActivity.class));
                break;
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