package com.example.allproject.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allproject.Activity.ActiveEmployActivity;
import com.example.allproject.Activity.EmployListActivity;
import com.example.allproject.Activity.GoogleMapActivity;
import com.example.allproject.Activity.WebViewActivity;
import com.example.allproject.Class.Employ;
import com.example.allproject.Class.Members;
import com.example.allproject.R;
import com.example.allproject.interfaces.IActiveEmployeeCallBack;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActiveEmployAdapter extends RecyclerView.Adapter<ActiveEmployAdapter.EmployViewHolder> {


    private Context mCtx;
    private List<Members> productList;
    private FirebaseFirestore profileFireSTore ;
    private AlertDialog.Builder clickBuilder;
    private  IActiveEmployeeCallBack callBack ;
    private int pageNumber = 0 ;

    public ActiveEmployAdapter(Context mCtx, List<Members> productList) {
        this.mCtx = mCtx;
        this.productList = productList;

    }



    @Override
    public ActiveEmployAdapter.EmployViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.active_list, null);
        return new ActiveEmployAdapter.EmployViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ActiveEmployAdapter.EmployViewHolder holder, final int position) {
        final Members members = productList.get(position%productList.size());

        clickBuilder =new AlertDialog.Builder(mCtx);
        profileFireSTore = FirebaseFirestore.getInstance();
        Log.d("id", "id: "+members.getCurrentId()) ;




        profileFireSTore.collection("Location").document(members.getCurrentId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    final double latitude = documentSnapshot.getDouble("latitude");
                    final double longitude = documentSnapshot.getDouble("longitude");
                    String onlineStatus = documentSnapshot.getString("onlineState");
                    Log.d("online", "onine: "+members.getCurrentId()+" "+onlineStatus);
                    if(onlineStatus.equals("online")){
                        holder.onlineStatusImage.setVisibility(View.VISIBLE);
                    }
                    else{
                        holder.onlineStatusImage.setVisibility(View.INVISIBLE);

                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mCtx, GoogleMapActivity.class) ;
                            intent.putExtra("latitude",String.valueOf(latitude)) ;
                            intent.putExtra("longitude", String.valueOf(longitude)) ;
                            intent.putExtra("location", "userLocation") ;
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            mCtx.startActivity(intent);
                            callBack.onClickOne(position, members);
                            Toast.makeText(mCtx, "yes", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                callBack.onClickOne(position, members);
//            }
//        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                CharSequence options[] = new CharSequence[]
//                        {
//                                "user",
//                                "admin"
//                        };
//
//                clickBuilder.setTitle("Select Any Test?") ;
//
//                clickBuilder.setItems(options, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                clickBuilder.show() ;
                profileFireSTore.collection("Members").document(members.getCurrentId())
                        .update("memberState","admin")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mCtx, "Updated by admin", Toast.LENGTH_SHORT).show();
                            }
                        });


                Log.d("long", "id:"+position+" "+ members.getCurrentId());
                return true;
            }
        });




        Picasso.get().load(members.getMemberProfileImage())
                .placeholder(R.drawable.profile_image)
                .into(holder.userProfileImage);
        Log.d("Image", "image: "+ members.getMemberProfileImage()) ;

        holder.textViewUserName.setText(members.getMemberUserName());
        holder.textViewStatus.setText(members.getMemberState());


        Log.d("AdapterBinding", position +" adapter") ;

        if(position > getItemCount()-2 ){
            pageNumber = pageNumber + 1 ;
        }

    }

    @Override
    public int getItemCount() {
        return productList.size()*10;
    }

    class EmployViewHolder extends RecyclerView.ViewHolder {

        TextView textViewUserName, textViewStatus;
        ImageView onlineStatusImage ;
        CircleImageView userProfileImage ;

        public EmployViewHolder(View itemView) {
            super(itemView);

            textViewUserName = itemView.findViewById(R.id.user_name);
            textViewStatus = itemView.findViewById(R.id.user_status);
            onlineStatusImage = itemView.findViewById(R.id.user_online_status);
            userProfileImage = itemView.findViewById(R.id.users_profile_image);

        }
    }
}