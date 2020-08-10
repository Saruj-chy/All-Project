package com.example.allproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.allproject.Activity.FoodDetailsActivity;
import com.example.allproject.Activity.FragmentActivity;
import com.example.allproject.Activity.GoogleMapActivity;
import com.example.allproject.Class.Product;
import com.example.allproject.R;

import java.util.List;

public class JSONAdapter extends RecyclerView.Adapter<JSONAdapter.ProductViewHolder>  {


    private Context mCtx;
    private List<Product> productList;

    public JSONAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public JSONAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, null);
        return new JSONAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JSONAdapter.ProductViewHolder holder, final int position) {
        final Product product = productList.get(position);

        Glide.with(mCtx)
                .load(product.getImage())
                .into(holder.imageView);

        holder.textViewFoodName.setText(product.getFood_name());
        holder.textViewPrice.setText(String.valueOf(product.getPrice()));
        holder.textViewResturantName.setText(product.getResturant_name());
        holder.textViewRating.setText(product.getRating());

        Log.d("TAG", "name: "+ product.getImage() ) ;

        holder.textViewResturantName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx, GoogleMapActivity.class) ;
                intent.putExtra("resturant_name", product.getResturant_name()) ;
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent);
//                Toast.makeText(mCtx, "yes", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewFoodName, textViewPrice, textViewResturantName, textViewRating;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewFoodName = itemView.findViewById(R.id.textViewFoodName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewResturantName = itemView.findViewById(R.id.textViewResturantName);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}