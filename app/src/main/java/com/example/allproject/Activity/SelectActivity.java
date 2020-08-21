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
import android.widget.TextView;
import android.widget.Toast;

import com.example.allproject.Class.Nested;
import com.example.allproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectActivity extends AppCompatActivity {

    Button selectBtn, uploadBtn ;
    TextView textView ;

    private static final int PICK_IMG = 1;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    Uri imageuri;

    private ProgressDialog progressDialog ;
    private int upload_count = 0;


    private CollectionReference productPostRef ;

    HashMap<String, String> hashMap ;
    List<String> tagUrl = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);


        selectBtn = findViewById(R.id.button) ;
        uploadBtn = findViewById(R.id.button2) ;
        textView = findViewById(R.id.textView) ;
        productPostRef = FirebaseFirestore.getInstance().collection("TestImage");


        progressDialog =  new ProgressDialog(this) ;
        progressDialog.setMessage("Image uploading...");

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICK_IMG);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                final StorageReference ImageFolder =  FirebaseStorage.getInstance().getReference().child("ImageFolder");

                for (upload_count=0; upload_count < ImageList.size(); upload_count++) {
                    Log.d("TAG", "count size "+upload_count + "    "+ (ImageList.size()-1) ) ;

                    Uri IndivitualImage  = ImageList.get(upload_count);
                    final StorageReference imagename = ImageFolder.child("image").child(IndivitualImage.getLastPathSegment());

                    Log.d("TAG", "IndivitualImage "+IndivitualImage ) ;
                    Log.d("TAG", "imageName "+imagename ) ;

                    imagename.putFile(IndivitualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Log.d("TAG", "uri: "+uri ) ;
                                    String url = String.valueOf(uri);
                                    Log.d("TAG", "url: "+url ) ;
//                                    StoreLink(url);
                                    tagUrl.add(url) ;
                                    Log.d("TAG", "tags also: "+tagUrl ) ;
                                    Log.d("TAG", "count "+ tagUrl.size() ) ;

                                    if(tagUrl.size() == ImageList.size()){
                                        Log.d("TAG", "tags: "+tagUrl ) ;
                                        Nested note = new Nested(tagUrl) ;
                                        productPostRef.document().set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                    });


                }



//                HashMap<String,Object> productMap = new HashMap<>();
//                productMap.put("currentId","currentUserId");
//                productMap.put("food_name","foodName");
//                productMap.put("tags", tagUrl );



            }
        });




    }
    private void StoreLink(String url) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Userone");

//        hashMap = new HashMap<>();
//        hashMap.put("imglink", url);
        tagUrl.add(url) ;
        Log.d("TAG", "tagUrl: "+tagUrl ) ;
        Log.d("TAG", "hashMap: "+hashMap ) ;
//        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                Log.d("TAG", "Uploaded: "+task ) ;
//                progressDialog.dismiss();
//                textView.setText("Image Uploaded Successfully");
//                uploadBtn.setVisibility(View.GONE);
//                ImageList.clear();
//            }
//        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMG) {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();

                    int CurrentImageSelect = 0;

                    while (CurrentImageSelect < count) {
                        imageuri = data.getClipData().getItemAt(CurrentImageSelect).getUri();
                        ImageList.add(imageuri);
                        CurrentImageSelect = CurrentImageSelect + 1;
                    }
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("You Have Selected "+ ImageList.size() +" Pictures" );
                    selectBtn.setVisibility(View.GONE);
                }

            }

        }

    }




}