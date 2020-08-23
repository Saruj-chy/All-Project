package com.example.allproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allproject.Activity.PostProductListActivity;
import com.example.allproject.Class.PostProduct;
import com.example.allproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostProductListAdapter extends RecyclerView.Adapter<PostProductListAdapter.PostViewHolder> {


    private Context mCtx;
    private List<PostProduct> productList;
    private CollectionReference productRef ;
    private String currentUserId;

    public PostProductListAdapter(Context mCtx, List<PostProduct> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public PostProductListAdapter.PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.post_product_list, null);
        currentUserId = FirebaseAuth.getInstance().getUid();
        return new PostProductListAdapter.PostViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final PostProductListAdapter.PostViewHolder holder, final int position) {
        final PostProduct products = productList.get(position);

        ((PostViewHolder) holder).bind(products);

        if(currentUserId.equals(products.getCurrentId())){
            holder.deleteButton.setVisibility(View.VISIBLE);
        }
        else{
            holder.deleteButton.setVisibility(View.GONE);
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                productRef.document(products.getDocumentId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mCtx, "Deleted successfully...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mCtx, PostProductListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mCtx.startActivity(intent);
                                Log.d("TAG", " currentUserId : " + currentUserId);
                            }
                        }, 100);
                    }
                });

            }
        });

//        profileFireSTore = FirebaseFirestore.getInstance();

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



//        List<String> images = new ArrayList<>() ;
//        List<String> data = products.getImageList() ;
//        Log.d("TAG", "ImageList: "+data) ;
//        Log.d("TAG", "foodName: "+products.getFood_name()) ;
//
//
//        Picasso.get().load(products.getImage())
//                .placeholder(R.drawable.profile_image)
//                .into(holder.imageView);
//
//        holder.textViewFoodName.setText(products.getFood_name());
//        holder.textViewResturantName.setText(products.getResturant_name());
//        holder.textViewPrice.setText(String.valueOf(products.getTotalPrice()));
//        holder.textViewRating.setText(products.getRating());




    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView textViewFoodName, textViewPrice, textViewResturantName, textViewRating;
        ImageButton deleteButton ;

        private RecyclerView nestedRV ;
        private  RVNestedAdapter nestedRVA ;
        List<String> images = new ArrayList<>() ;

        public PostViewHolder(View itemView) {
            super(itemView);

            textViewFoodName = itemView.findViewById(R.id.textViewFoodName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewResturantName = itemView.findViewById(R.id.textViewResturantName);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            deleteButton = itemView.findViewById(R.id.deleteBtn);

            nestedRV = itemView.findViewById(R.id.postProductRV);
            nestedRVA = new RVNestedAdapter(mCtx, images);
            GridLayoutManager manager = new GridLayoutManager(mCtx, 1, GridLayoutManager.HORIZONTAL, false);
            nestedRV.setLayoutManager(manager);  // set horizontal LM
            nestedRV.setAdapter(nestedRVA);

        }

        public void bind(final PostProduct products){
            textViewFoodName.setText(products.getFood_name());
            textViewResturantName.setText(products.getResturant_name());
            textViewPrice.setText("Price: "+ String.valueOf(products.getTotalPrice()));
            textViewRating.setText("Rating: "+Double.valueOf(products.getRating())*2 +"/10");

            productRef = FirebaseFirestore.getInstance().collection("ProductPost");


            List<String> imageList = products.getImageList();
            for(int i=0; i<imageList.size(); i++){
                this.images.add(this.images.size(), imageList.get(i));
            }
            Log.d("TAG", "ImageList: "+images) ;
            nestedRVA.notifyDataSetChanged();
        }

    }
}