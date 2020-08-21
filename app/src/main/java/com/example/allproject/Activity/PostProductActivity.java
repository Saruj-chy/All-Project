package com.example.allproject.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.allproject.Adapter.RVNestedAdapter;
import com.example.allproject.Class.PostProduct;
import com.example.allproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PostProductActivity extends AppCompatActivity {

    private ImageView postImage;
    private RecyclerView postRecyclerView ;
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


    private Uri imageuri;
    Bitmap bitmapSrc;

    byte[] thumbData ;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private ArrayList<byte[]> ThumbListByte = new ArrayList<byte[]>();
    private List<String> tagUrl = new ArrayList<String>();
    private List<String> thumbUrl = new ArrayList<String>();
    private String downloadUrl;
    private int upload_count=0 ;
    private ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_product);

        productPostRef = FirebaseFirestore.getInstance().collection("ProductPost");
        postStorageRef = FirebaseStorage.getInstance().getReference();
        currentUserId = FirebaseAuth.getInstance().getUid() ;
        progressDialog = new ProgressDialog(this);


        postImage = findViewById(R.id.imagePost) ;
        postRecyclerView = findViewById(R.id.post_recyclerView) ;
        foodEditText = findViewById(R.id.foodEditText) ;
        resturantEditText = findViewById(R.id.resturantEditText) ;
        priceEditText = findViewById(R.id.priceEditText) ;
        ratingBar = findViewById(R.id.ratingBar) ;
        submitBtn = findViewById(R.id.submitBtnPost) ;



        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, 1);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postProductInfi() ;
            }
        });




    }

    private void postProductInfi() {
        foodName = foodEditText.getText().toString();
        resturantName = resturantEditText.getText().toString();
        price = priceEditText.getText().toString();
        rating = ratingBar.getRating();



        progressDialog.setTitle("Post product Image");
        progressDialog.setMessage("Please wait, Your post is posting...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        for (upload_count = 0; upload_count < ImageList.size(); upload_count++) {
            final Uri IndivitualImage = ImageList.get(upload_count);
            final StorageReference imagename = postStorageRef.child("ProductPost").child(currentUserId).child(IndivitualImage.getLastPathSegment());

            //TODO:: thumbonil
            final StorageReference thumbnilImage = postStorageRef.child("ThumbonilImage").child(currentUserId).child(IndivitualImage.getLastPathSegment());
            thumbnilImage.putBytes(ThumbListByte.get(upload_count))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            thumbnilImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = String.valueOf(uri);
                                    thumbUrl.add(url);
                                }
                            }) ;
                        }
                    }) ;


            imagename.putFile(IndivitualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = String.valueOf(uri);
                            tagUrl.add(url);

                          if (tagUrl.size() == ImageList.size()) {
                                Log.d("TAG", "tags: " + tagUrl);
                                Log.d("TAG", "thumbUrl: " + thumbUrl);
                                PostProduct note = new PostProduct(currentUserId, foodName, resturantName, String.valueOf(rating), price, tagUrl, thumbUrl);
                                productPostRef.document().set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        finish();
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Post Inserted Successfully...", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }

                        }
                    });

                }
            });


        }

    }








    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();

                    int CurrentImageSelect = 0;


                    while (CurrentImageSelect < count) {
                        imageuri = data.getClipData().getItemAt(CurrentImageSelect).getUri();
                        Picasso.get().load(imageuri).resize(200, 200).
                                centerCrop().into(postImage);
                        ImageList.add(imageuri);

                        //TODO:: byte array
                        try{
                            bitmapSrc = MediaStore.Images.Media.getBitmap(PostProductActivity.this.getContentResolver(), imageuri) ;
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        Bitmap bitmapObj = Bitmap.createScaledBitmap(bitmapSrc, 120, 120, false );
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmapObj.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        thumbData = baos.toByteArray();
                        ThumbListByte.add(thumbData);


                        CurrentImageSelect = CurrentImageSelect + 1;
                    }

                    postImage.setVisibility(View.INVISIBLE);
                    postRecyclerView.setVisibility(View.VISIBLE);
                    List<String> selectImages = new ArrayList<>() ;
                    for(int i=0; i<ImageList.size(); i++){
                        selectImages.add(selectImages.size(), String.valueOf(ImageList.get(i)));
                    }

                    RVNestedAdapter productAdapter = new RVNestedAdapter(getApplicationContext(), selectImages);
                    postRecyclerView.setAdapter(productAdapter);
                    GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.HORIZONTAL, false);
                    postRecyclerView.setLayoutManager(manager);

//                    Toast.makeText(this, "You have selected "+ ImageList.size()+" images...", Toast.LENGTH_SHORT).show();
                }

            }

        }

        if(requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
        }
    }
}