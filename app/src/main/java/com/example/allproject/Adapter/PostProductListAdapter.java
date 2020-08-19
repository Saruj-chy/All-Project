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

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allproject.Activity.GoogleMapActivity;
import com.example.allproject.Class.Members;
import com.example.allproject.Class.Product;
import com.example.allproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostProductListAdapter extends RecyclerView.Adapter<PostProductListAdapter.PostViewHolder> {


    private Context mCtx;
    private List<Product> productList;
    private FirebaseFirestore profileFireSTore ;

    public PostProductListAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public PostProductListAdapter.PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, null);
        return new PostProductListAdapter.PostViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final PostProductListAdapter.PostViewHolder holder, final int position) {
        final Product products = productList.get(position);

        profileFireSTore = FirebaseFirestore.getInstance();

//        profileFireSTore.collection("ProductPost").document(products.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot.exists()){
//                    final double latitude = documentSnapshot.getDouble("latitude");
//                    final double longitude = documentSnapshot.getDouble("longitude");
//                    String onlineStatus = documentSnapshot.getString("onlineState");
//                    Log.d("online", "onine: "+members.getCurrentId()+" "+onlineStatus);
//                    if(onlineStatus.equals("online")){
//                        holder.onlineStatusImage.setVisibility(View.VISIBLE);
//                    }
//                    else{
//                        holder.onlineStatusImage.setVisibility(View.INVISIBLE);
//
//                    }
//                    holder.itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(mCtx, GoogleMapActivity.class) ;
//                            intent.putExtra("latitude",String.valueOf(latitude)) ;
//                            intent.putExtra("longitude", String.valueOf(longitude)) ;
//                            intent.putExtra("location", "userLocation") ;
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            mCtx.startActivity(intent);
//                            Toast.makeText(mCtx, "yes", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//
//                }
//            }
//        });






        Picasso.get().load(products.getImage())
                .placeholder(R.drawable.profile_image)
                .into(holder.imageView);

        holder.textViewFoodName.setText(products.getFood_name());
        holder.textViewResturantName.setText(products.getResturant_name());
        holder.textViewPrice.setText(String.valueOf(products.getPrice()));
        holder.textViewRating.setText(products.getRating());




    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView textViewFoodName, textViewPrice, textViewResturantName, textViewRating;
        ImageView imageView;

        public PostViewHolder(View itemView) {
            super(itemView);

            textViewFoodName = itemView.findViewById(R.id.textViewFoodName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewResturantName = itemView.findViewById(R.id.textViewResturantName);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}