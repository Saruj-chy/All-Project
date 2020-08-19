package com.example.allproject.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.allproject.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Random;

public class PostProductActivity extends AppCompatActivity {

    private ImageView postImage;
    private EditText foodEditText, resturantEditText, priceEditText ;
    private Button submitBtn;
    private RatingBar ratingBar ;

    private String image;
    private String foodName;
    private String resturantName;
    private String price;
    private double rating;

    private CollectionReference productPostRef ;
    private String currentUserId ;
    private StorageReference postStorageRef ;


    private Uri imageuri1;
    private String downloadUrl;
    private ProgressDialog progressDialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_product);

        productPostRef = FirebaseFirestore.getInstance().collection("ProductPost");
        postStorageRef = FirebaseStorage.getInstance().getReference().child("ProductPost");
        currentUserId = FirebaseAuth.getInstance().getUid() ;
        progressDialog = new ProgressDialog(this);


        postImage = findViewById(R.id.imagePost) ;
        foodEditText = findViewById(R.id.foodEditText) ;
        resturantEditText = findViewById(R.id.resturantEditText) ;
        priceEditText = findViewById(R.id.priceEditText) ;
        ratingBar = findViewById(R.id.ratingBar) ;
        submitBtn = findViewById(R.id.submitBtnPost) ;

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryintent = new Intent();
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,1);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postProductInfi() ;

                Log.d("TAG", foodName+" "+resturantName+" "+price+ " "+ rating ) ;
                Toast.makeText(PostProductActivity.this, "rating: "+rating, Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void postProductInfi() {
        foodName = foodEditText.getText().toString() ;
        resturantName = resturantEditText.getText().toString() ;
        price = priceEditText.getText().toString() ;
        rating = ratingBar.getRating();

        progressDialog.setTitle("Post product Image");
        progressDialog.setMessage("Please wait, Your post is posting...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        final StorageReference filePath = postStorageRef.child(currentUserId);
        Log.d("TAG", "imageURI: "+imageuri1 ) ;
        final UploadTask uploadTask = filePath.putFile(imageuri1);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }
                downloadUrl=filePath.getDownloadUrl().toString();
                Log.d("TAG", "image: "+downloadUrl) ;
                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    downloadUrl = task.getResult().toString();
                    HashMap<String,Object> productMap = new HashMap<>();
                    productMap.put("currentId",currentUserId);
                    productMap.put("food_name",foodName);
                    productMap.put("totalPrice", price);
                    productMap.put("resturant_name",resturantName);
                    productMap.put("rating",String.valueOf(rating));
                    productMap.put("image",downloadUrl);

                    Log.d("TAG", "imagepost: "+downloadUrl) ;
                    Random number = new Random() ;
                    productPostRef.document(currentUserId).collection("post").document().set(productMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            finish();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Post Inserted Successfully...",Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 &&resultCode==RESULT_OK&&data!=null){
            imageuri1=data.getData();
//            Toast.makeText(this, "yes: "+imageuri1, Toast.LENGTH_LONG).show();

            Picasso.get().load(imageuri1).resize(200, 200).
                    centerCrop().into(postImage);
            Log.d("TAG","Profileimage: "+imageuri1) ;

        }

        if(requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
        }
    }
}