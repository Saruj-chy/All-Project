package com.example.allproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.allproject.Activity.FoodDetailsActivity;
import com.example.allproject.Class.Product;
import com.example.allproject.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Product> productList;

    public ProductsAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product product = productList.get(position);

        Glide.with(mCtx)
                .load(product.getImage())
                .into(holder.imageView);

        holder.textViewFoodName.setText(product.getFood_name());
        holder.textViewPrice.setText(String.valueOf(product.getPrice()));
        holder.textViewResturantName.setText(product.getResturant_name());
        holder.textViewRating.setText(product.getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx, FoodDetailsActivity.class) ;
                intent.putExtra("foodName", product.getFood_name()) ;
                intent.putExtra("price", String.valueOf(product.getPrice())) ;
                intent.putExtra("resturantName", product.getResturant_name()) ;
                intent.putExtra("rating", product.getRating()) ;
                intent.putExtra("image", product.getImage()) ;
                mCtx.startActivity(intent);
                Toast.makeText(mCtx, "yes", Toast.LENGTH_SHORT).show();
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