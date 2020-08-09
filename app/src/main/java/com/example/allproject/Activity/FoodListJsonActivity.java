package com.example.allproject.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.allproject.Adapter.ProductsAdapter;
import com.example.allproject.Class.Product;
import com.example.allproject.Constant.JsonArray;
import com.example.allproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoodListJsonActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    List<Product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list_json);

        recyclerView = findViewById(R.id.foodRecyclerView) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        loadProducts();
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
            ProductsAdapter adapter = new ProductsAdapter(getApplicationContext(), productList);
            recyclerView.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}