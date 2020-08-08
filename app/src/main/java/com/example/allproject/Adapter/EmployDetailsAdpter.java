package com.example.allproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.allproject.Activity.FoodDetailsActivity;
import com.example.allproject.Class.Employ;
import com.example.allproject.Class.Product;
import com.example.allproject.R;

import java.util.List;

public class EmployDetailsAdpter extends RecyclerView.Adapter<EmployDetailsAdpter.EmployViewHolder> {


    private Context mCtx;
    private List<Employ> productList;

    public EmployDetailsAdpter(Context mCtx, List<Employ> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public EmployDetailsAdpter.EmployViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.employ_list, null);
        return new EmployDetailsAdpter.EmployViewHolder(view);
    }


    @Override
    public void onBindViewHolder(EmployViewHolder holder, int position) {
        final Employ employ = productList.get(position);



        holder.textViewName.setText(employ.getName());
        holder.textViewPosition.setText(employ.getPosition());


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mCtx, FoodDetailsActivity.class) ;
//                intent.putExtra("foodName", product.getFood_name()) ;
//                intent.putExtra("price", String.valueOf(product.getPrice())) ;
//                intent.putExtra("resturantName", product.getResturant_name()) ;
//                intent.putExtra("rating", product.getRating()) ;
//                intent.putExtra("image", product.getImage()) ;
//                mCtx.startActivity(intent);
//                Toast.makeText(mCtx, "yes", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class EmployViewHolder extends RecyclerView.ViewHolder {

       TextView textViewName, textViewPosition ;

        public EmployViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textviewName);
            textViewPosition = itemView.findViewById(R.id.textviewPosition);

        }
    }
}