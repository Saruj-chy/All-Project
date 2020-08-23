package com.example.allproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allproject.Activity.PostProductListActivity;
import com.example.allproject.Class.Product;
import com.example.allproject.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RVNestedAdapter extends RecyclerView.Adapter<RVNestedAdapter.PostViewHolder> {


    private Context mCtx;
    private List<String> productList;
    private FirebaseFirestore profileFireSTore ;



    public RVNestedAdapter(Context mCtx, List<String> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public RVNestedAdapter.PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.nested_post_image, null);
        return new RVNestedAdapter.PostViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RVNestedAdapter.PostViewHolder holder, final int position) {
        final String products = productList.get(position);

        ((RVNestedAdapter.PostViewHolder) holder).bind(products);


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        private AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mCtx);
        private AlertDialog dialog;

        public PostViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.nested_imageView);

        }

        public void bind(String products){

            Picasso.get().load(products)
                .placeholder(R.drawable.profile_image)
                .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    View dialogView = inflater.inflate(R.layout.image_popup, null);
//                    ImageView imageView_pop = dialogView.findViewById(R.id.image_pop) ;
//                    imageView_pop.setImageResource(R.drawable.profile_image);
//                    dialogBuilder.setView(view);
//                    dialog = dialogBuilder.create();
//                    dialog.show();

                    Toast.makeText(mCtx, "position: "+getPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}