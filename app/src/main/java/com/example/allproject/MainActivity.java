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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.allproject.Activity.ActiveEmployActivity;
import com.example.allproject.Activity.EmployListActivity;
import com.example.allproject.Activity.FoodListVolleyActivity;
import com.example.allproject.Activity.FragmentActivity;
import com.example.allproject.Activity.LoginActivity;
import com.example.allproject.Activity.PostProductActivity;
import com.example.allproject.Activity.PostProductListActivity;
import com.example.allproject.Activity.ProfileActivity;
import com.example.allproject.Adapter.JSONAdapter;
import com.example.allproject.Class.Product;
import com.example.allproject.Constant.JsonArray;
import com.example.allproject.interfaces.IntentCallBack;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IntentCallBack {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    String drawerImageUrl, drawerUserName, drawerStatus ;

    private RecyclerView recyclerView ;
    List<Product> productList;

    private FirebaseAuth mAuth;
    private String currentUserId;
    private FirebaseFirestore userFireStore ;


    //=========   sharedprefarence
    static String SHARED_PREFS = "codeTheme";
    String state = "state";
    String getState ;
    SharedPreferences sharedPreferences,  sharedPreferences1, sharedPreferences2, sharedPreferences3;

    //=====  for location
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    double latitude=0, longitude=0 ;

    private String currentDate, currentTime ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Project");

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        sharedPreferences1 = getSharedPreferences("userName", MODE_PRIVATE);
        sharedPreferences2 = getSharedPreferences("status", MODE_PRIVATE);
        sharedPreferences3 = getSharedPreferences("image", MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance() ;
        currentUserId = mAuth.getCurrentUser().getUid() ;
        userFireStore = FirebaseFirestore.getInstance();


        recyclerView = findViewById(R.id.recyclerView) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        loadProducts();

        DrawerProfileRef();

        drawerUserName = sharedPreferences1.getString("userName", "userName");
        drawerStatus = sharedPreferences2.getString("status", "user");
        drawerImageUrl = sharedPreferences3.getString("image", "image");

        //====    location user
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        GetLocation("online");


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.header);

        //=======   drawer_header
        View headerview = navigationView.getHeaderView(0);
        ImageView drawerImage = headerview.findViewById(R.id.drawer_image);
        TextView drawerUserTV = (TextView) headerview.findViewById(R.id.drawer_userName);
        TextView drawerStatusTV = (TextView) headerview.findViewById(R.id.drawer_status);
        Log.d("user","name1: "+drawerUserName+" "+ drawerStatus) ;
        drawerUserTV.setText(drawerUserName);
        drawerStatusTV.setText(drawerStatus);
        Picasso.get().load(drawerImageUrl)
                .placeholder(R.drawable.profile_image)
                .into(drawerImage);
        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        //======  drawer_menu
        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        RecentDate();


    }

    private void DrawerProfileRef() {
        userFireStore.collection("Members").document(currentUserId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            drawerUserName = documentSnapshot.getString("memberUserName") ;
                            drawerStatus = documentSnapshot.getString("memberState") ;

                            sharedSaved(sharedPreferences1,"userName", drawerUserName);
                            sharedSaved(sharedPreferences2,"status", drawerStatus);
                            sharedSaved(sharedPreferences3,"image", drawerStatus);

                            if(documentSnapshot.contains("memberProfileImage")){
                                drawerImageUrl = documentSnapshot.getString("memberProfileImage") ;
                                sharedSaved(sharedPreferences3,"image", drawerImageUrl);
                            }
                        }
                    }
                });
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
            e.printStackTrace();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        DrawerProfileRef();
        GetLocation("online");
        RecentDate();
//        Toast.makeText(this, "onStatrt", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
//        GetLocation("online");
//        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GetLocation("offline");
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
            case R.id.item_employ_details:
                startActivity(new Intent(this, EmployListActivity.class));
                break;
            case R.id.item_volley:
                startActivity(new Intent(this, FoodListVolleyActivity.class));
                break;
            case R.id.item_active_employ:  //log out
                startActivity(new Intent(this, ActiveEmployActivity.class));
                break;
            case R.id.item_b:
                startActivity(new Intent(this, FragmentActivity.class));
                break;
            case R.id.item_c:

//                startActivity(new Intent(this, SelectActivity.class));
//                startActivity(new Intent(this, TestActivity.class));
                break;
            case R.id.item_d:
                startActivity(new Intent(this, PostProductActivity.class));
                break;
            case R.id.item_e:
                startActivity(new Intent(this, PostProductListActivity.class));
                break;

            case R.id.item_log_out:  //log out
                onIntent(getApplicationContext(), LoginActivity.class);
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
        memberLocation.put("time", currentTime );
        memberLocation.put("date", currentDate );

        userFireStore.collection("Location").document(currentUserId).set(memberLocation);
    }
    private void RecentDate() {
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        currentDate = currentDateFormat.format(calForDate.getTime());
        Log.d("TAG"," currentDate: "+ currentDate ) ;

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
        currentTime = currentTimeFormat.format(calForTime.getTime());
        Log.d("TAG"," currentTime: "+ currentTime ) ;
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

    @Override
    public void onIntent(Context context, Object activity) {
        Intent intent = new Intent(context, (Class<?>) activity);
        startActivity(intent);
    }
}