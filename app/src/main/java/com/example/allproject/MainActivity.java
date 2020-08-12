package com.example.allproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allproject.Activity.ActiveEmployActivity;
import com.example.allproject.Activity.EmployListActivity;
import com.example.allproject.Activity.FoodListJsonActivity;
import com.example.allproject.Activity.FragmentActivity;
import com.example.allproject.Activity.LoginActivity;
import com.example.allproject.Activity.RegistrationActivity;
import com.example.allproject.Adapter.JSONAdapter;
import com.example.allproject.Class.Product;
import com.example.allproject.Constant.JsonArray;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    TextView appName;
    ActionBarDrawerToggle drawerToggle;

    private RecyclerView recyclerView ;
    List<Product> productList;

    private FirebaseAuth mAuth;
    private String currentUserId;
    private CollectionReference userRef ;


    //=========   sharedprefarence
    static String SHARED_PREFS = "codeTheme";
    String state = "";
    String getState ;
    SharedPreferences sharedPreferences;

    //=====  for location
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    double latitude=0, longitude=0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Project");

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance() ;
        currentUserId = mAuth.getCurrentUser().getUid() ;
        userRef = FirebaseFirestore.getInstance().collection("Location");


        recyclerView = findViewById(R.id.recyclerView) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        loadProducts();






        //====    location user
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        GetLocation("online");


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
    protected void onStart() {
        super.onStart();
        GetLocation("online");
//        Toast.makeText(this, "onStatrt", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        GetLocation("online");
//        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GetLocation("online");
//        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
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
//            case R.id.item_c:
//                startActivity(new Intent(this, CollapsibleActivity.class));
//                break;
//            case R.id.item_d:
//                startActivity(new Intent(this, RecyclerViewActivity.class));
//                break;
//            case R.id.item_e:
//                startActivity(new Intent(this, SqLiteDatabaseActivity.class));
//                break;
            case R.id.item_f:
                startActivity(new Intent(this, FoodListJsonActivity.class));
                break;
            case R.id.item_g:  //log out
                startActivity(new Intent(this, ActiveEmployActivity.class));

                break;
            case R.id.item_h:  //log out
                sendUserToLoginActivity();
                mAuth.signOut();
                break;

        }


        return false;
    }



    //===========    for location
    private void GetLocation(final String onlineState) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location  != null){
                    currentLocation = location;
                    setDatabaseStorage(currentLocation.getLatitude(), currentLocation.getLongitude(),onlineState);
                }
            }
        });
    }

    private void setDatabaseStorage(double latitude, double longitude, String onlineState) {


        HashMap<String, Object> memberLocation = new HashMap<>();
        memberLocation.put("latitude", latitude );
        memberLocation.put("longitude",longitude);
        memberLocation.put("onlineState", onlineState );


        userRef.document(currentUserId).set(memberLocation);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    GetLocation("online");
                    Log.d("TAG", "yes granted");
                }
                else{
                    Log.d("TAG", "No granted");
                    setDatabaseStorage(latitude, longitude, "online");

                }
                break;
        }
    }

    private void sendUserToLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
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

   public static void sharedSaved(SharedPreferences sharedPreferences, String state, String memberState){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(state, memberState);
        editor.apply();
    }
}