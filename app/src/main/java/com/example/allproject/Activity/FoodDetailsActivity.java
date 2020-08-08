package com.example.allproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.allproject.R;

public class FoodDetailsActivity extends AppCompatActivity {

    private TextView textViewFoodName, textViewPrice, textViewResturantName, textViewRating  ;
    private ImageView imageView ;

    private String foodName, price, resturantName, rating, image ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        textViewFoodName = findViewById(R.id.textViewFoodName);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewResturantName = findViewById(R.id.textViewResturantName);
        textViewRating = findViewById(R.id.textViewRating);
        imageView = findViewById(R.id.imageView);

        foodName = getIntent().getStringExtra("foodName").toString().trim();
        price = getIntent().getStringExtra("price").toString().trim();
        resturantName = getIntent().getStringExtra("resturantName").toString().trim();
        rating = getIntent().getStringExtra("rating").toString().trim();
        image = getIntent().getStringExtra("image").toString().trim();


        textViewFoodName.setText(foodName);
        textViewPrice.setText(price);
        textViewResturantName.setText(resturantName);
        textViewRating.setText(rating);

        Glide.with(getApplicationContext())
                .load(image)
                .into(imageView);


    }
}