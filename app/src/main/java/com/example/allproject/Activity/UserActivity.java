package com.example.allproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.allproject.Adapter.JSONAdapter;
import com.example.allproject.Class.Product;
import com.example.allproject.Constant.JsonArray;
import com.example.allproject.MainActivity;
import com.example.allproject.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    List<Product> productList;

    FirebaseAuth mAuth ;
    private CollectionReference userRef, memberRef ;
    String currentId;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    double latitude=0, longitude=0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_cycle);

        mAuth = FirebaseAuth.getInstance();
        currentId= mAuth.getUid() ;
        userRef = FirebaseFirestore.getInstance().collection("Location");
        memberRef = FirebaseFirestore.getInstance().collection("Members");

        recyclerView = findViewById(R.id.userRecyclerView) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        loadProducts();

        //====    location user
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        GetLocation("online");



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

//                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude()+" "+
//                            currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setDatabaseStorage(double latitude, double longitude, String onlineState) {


        HashMap<String, Object> memberLocation = new HashMap<>();
        memberLocation.put("latitude", latitude );
        memberLocation.put("longitude",longitude);
        memberLocation.put("onlineState", onlineState );


        userRef.document(currentId).set(memberLocation);

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.user_profile_menu){
            sendUserToProfileActivity();
        }
        if (item.getItemId() == R.id.user_logout_menu){

            mAuth.signOut();
            sendUserToLoginActivity();
        }
        return true;
    }





    @Override
    protected void onStart() {
        super.onStart();
        WhereToGo();
        GetLocation("online");
//        Toast.makeText(this, "App now onStart mode", Toast.LENGTH_SHORT).show();
    }

    private void WhereToGo() {
        memberRef.document(currentId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    String memberState = documentSnapshot.getString("memberState");
                    if(memberState.equals("admin")){
                        mAuth.signOut();
                    }
                }
            }
        });
    }

    //
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(this, "App now onResume mode", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Toast.makeText(this, "App now onPause mode", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Toast.makeText(this, "App now onRestart mode", Toast.LENGTH_SHORT).show();
//
//    }
//
    @Override
    protected void onStop() {
        super.onStop();
        GetLocation("offline");
//        Toast.makeText(this, "App now onStop mode", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GetLocation("offline");
//        Toast.makeText(this, "App now onDestroy mode", Toast.LENGTH_SHORT).show();

    }

    private void sendUserToProfileActivity() {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
    }


    private void sendUserToLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}