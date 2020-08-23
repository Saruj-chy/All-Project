package com.example.allproject.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allproject.MainActivity;
import com.example.allproject.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    //=========   sharedprefarence
    String SHARED_PREFS = "codeTheme";
    String state = "";
    String getState ;
    SharedPreferences sharedPreferences;

    private RelativeLayout relativeLayout1, relativeLayout2;
    private TextView fullNameTV, userNameTV, emailTV, passwordTV;
    private EditText firstNameET, lastNameET, userNameET, emailET, passwordET;
    private Button updateProfileButton, updateButton;
    private CircleImageView profileImage1, profileImage2;

    private String firstName, lastName, userName, email, password, profileImg ;

    private FirebaseAuth mAuth ;
    private CollectionReference profileRef, LocationRef ;
    private String currentUserId ;
    private StorageReference userProfileimageRef1;
    private String currentDate, currentTime ;

    private Uri imageuri1;
    private String downloadUrl1;

    private ProgressDialog progressDialog ;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        getState = sharedPreferences.getString(state, "");
        Log.d("State", getState) ;
        progressDialog = new ProgressDialog(this);

        InitialFields1() ;
        InitialFields2();

        mAuth = FirebaseAuth.getInstance() ;
        currentUserId = mAuth.getCurrentUser().getUid() ;
        profileRef = FirebaseFirestore.getInstance().collection("Members");
        LocationRef = FirebaseFirestore.getInstance().collection("Location");
        userProfileimageRef1 = FirebaseStorage.getInstance().getReference().child("Profile Images");

        RetrieveUserInfo() ;

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout1.setVisibility(View.INVISIBLE);
                relativeLayout2.setVisibility(View.VISIBLE);

                RetrieveUserInfo2() ;
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveUserData() ;
            }
        });

        profileImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryintent = new Intent();
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,1);
            }
        });

        UpdateTimeState("online") ;

    }

    private void UpdateTimeState(String onlineState) {

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        currentDate = currentDateFormat.format(calForDate.getTime());
        Log.d("TAG"," currentDate: "+ currentDate ) ;

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
        currentTime = currentTimeFormat.format(calForTime.getTime());
        Log.d("TAG"," currentTime: "+ currentTime ) ;

        HashMap<String, Object> timeUpdate = new HashMap<>();
        timeUpdate.put("onlineState", onlineState );
        timeUpdate.put("time", currentTime );
        timeUpdate.put("date", currentDate );

        LocationRef.document(currentUserId).update(timeUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }) ;
//        LocationRef.document(currentUserId).update("time", currentTimeFormat) ;
//        LocationRef.document(currentUserId).update("date", currentDateFormat) ;


    }

    private void SaveUserData() {
        final String setFirstName= firstNameET.getText().toString();
        final String setLastName= lastNameET.getText().toString();
        final String setUserName= userNameET.getText().toString();
        final String setEmail= emailET.getText().toString();
        final String setPassword= passwordET.getText().toString();

        if(count==1 &&  imageuri1==null ) {

            profileRef.document(currentUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    saveInfoOnlyWithoutImage();
                }
            });
        }  else if(setUserName.equals("") || setEmail.equals("") ||setPassword.equals("")){
            Toast.makeText(ProfileActivity.this,"Please fillup all fields",Toast.LENGTH_LONG).show();
        }
        else if(count==1 && imageuri1 != null){
            progressDialog.setTitle("Set Profile Image");
            progressDialog.setMessage("Please wait, Your Profile image is updating...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            final StorageReference filePath = userProfileimageRef1.child(currentUserId);
            Log.d("TAG", "imageURI: "+imageuri1 ) ;
            final UploadTask uploadTask = filePath.putFile(imageuri1);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    downloadUrl1=filePath.getDownloadUrl().toString();
                    Log.d("TAG", "image: "+downloadUrl1) ;
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        downloadUrl1 = task.getResult().toString();
                        HashMap<String,Object> profileMap = new HashMap<>();
                        profileMap.put("currentId",currentUserId);
                        profileMap.put("memberFirstName",setFirstName);
                        profileMap.put("memberLastName",setLastName);
                        profileMap.put("memberUserName",setUserName);
                        profileMap.put("memberPassword",setPassword);
                        profileMap.put("memberEmail",setEmail);
                        profileMap.put("memberProfileImage",downloadUrl1);
                        profileMap.put("memberState","user");

                        Log.d("TAG", "imagepost: "+downloadUrl1) ;

                        profileRef.document(currentUserId).set(profileMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                relativeLayout1.setVisibility(View.VISIBLE);
                                relativeLayout2.setVisibility(View.INVISIBLE);
                                finish();
                                progressDialog.dismiss();
                                Toast.makeText(ProfileActivity.this,"Profile updated with image",Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }
            });
        }
    }

    private void saveInfoOnlyWithoutImage() {
        final String setFirstName= firstNameET.getText().toString();
        final String setLastName= lastNameET.getText().toString();
        final String setUserName= userNameET.getText().toString();
        final String setEmail= emailET.getText().toString();
        final String setPassword= passwordET.getText().toString();

        if(setUserName.equals("") || setEmail.equals("") ||setPassword.equals("")){
            Toast.makeText(ProfileActivity.this,"Please fillup all fields",Toast.LENGTH_LONG).show();
        }
        else if(count==1){
            progressDialog.setTitle("Set Profile Without Image: ");
            progressDialog.setMessage("Please wait, Your Profile without image is updating...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            HashMap<String,Object> profileMap = new HashMap<>();
            profileMap.put("currentId",currentUserId);
            profileMap.put("memberFirstName",setFirstName);
            profileMap.put("memberLastName",setLastName);
            profileMap.put("memberUserName",setUserName);
            profileMap.put("memberPassword",setPassword);
            profileMap.put("memberEmail",setEmail);
            profileMap.put("memberState","user");

            profileRef.document(currentUserId).set(profileMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    relativeLayout1.setVisibility(View.VISIBLE);
                    relativeLayout2.setVisibility(View.INVISIBLE);
                    finish();
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this,"Your profile updated",Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        count = 1;
        UpdateTimeState("online") ;

        Log.d("TAG","Count1: "+count) ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateTimeState("online") ;
    }

    @Override
    protected void onPause() {
        super.onPause();
        UpdateTimeState("online") ;
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressDialog.dismiss();
        count = 2;
//        UpdateTimeState("offline");
        Log.d("TAG","Count2: "+count) ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 &&resultCode==RESULT_OK&&data!=null){
            imageuri1=data.getData();
//            Toast.makeText(this, "yes: "+imageuri1, Toast.LENGTH_LONG).show();

            Picasso.get().load(imageuri1).resize(50, 50).
                    centerCrop().into(profileImage1);
            Log.d("TAG","Profileimage: "+imageuri1) ;

        }

        if(requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
        }
    }

    private void RetrieveUserInfo() {

        profileRef.document(currentUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    firstName = documentSnapshot.getString("memberFirstName");
                    lastName = documentSnapshot.getString("memberLastName");
                    userName = documentSnapshot.getString("memberUserName");
                    email = documentSnapshot.getString("memberEmail");
                    password = documentSnapshot.getString("memberPassword");


                    fullNameTV.setText(firstName+" "+lastName);
                    userNameTV.setText(userName);
                    emailTV.setText(email);
                    passwordTV.setText(password);

                    Log.d("TAG","FullName: "+firstName+" "+lastName);
                    Log.d("TAG","UserName: "+userName) ;

                    if(documentSnapshot.contains("memberProfileImage")){
                        profileImg = documentSnapshot.getString("memberProfileImage");
                        Picasso.get().load(profileImg).placeholder(R.drawable.profile_image).into(profileImage1);
                        Log.d("TAG","profileImg1: "+profileImg ) ;

                        Toast.makeText(ProfileActivity.this, "Yes exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }) ;
    }
    private void RetrieveUserInfo2() {

        profileRef.document(currentUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    firstName = documentSnapshot.getString("memberFirstName");
                    lastName = documentSnapshot.getString("memberLastName");
                    userName = documentSnapshot.getString("memberUserName");
                    email = documentSnapshot.getString("memberEmail");
                    password = documentSnapshot.getString("memberPassword");

                    firstNameET.setText(firstName);
                    lastNameET.setText(lastName);
                    userNameET.setText(userName);
                    emailET.setText(email);
                    passwordET.setText(password);

                    Log.d("TAG","FullName: "+firstName+" "+lastName);
                    Log.d("TAG","UserName: "+userName) ;

                    if(documentSnapshot.contains("memberProfileImage")){
                        profileImg = documentSnapshot.getString("memberProfileImage");
                        Picasso.get().load(profileImg).placeholder(R.drawable.profile_image).into(profileImage2);
                        Log.d("TAG","profileImg: "+profileImg ) ;

                        Toast.makeText(ProfileActivity.this, "Yes exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }) ;

    }

    private void InitialFields1() {
        relativeLayout1 = findViewById(R.id.relativeLayout1) ;

        fullNameTV = findViewById(R.id.fullNameTextView);
        userNameTV = findViewById(R.id.userNameTextView);
        emailTV = findViewById(R.id.emailTextView);
        passwordTV = findViewById(R.id.passwordTextView);
        updateProfileButton = findViewById(R.id.updateButton);
        profileImage1 = findViewById(R.id.visit_profile_image);
    }

    private void InitialFields2() {
        relativeLayout2 = findViewById(R.id.relativeLayout2) ;

        firstNameET = findViewById(R.id.firstNameEditText2);
        lastNameET = findViewById(R.id.lastNameEditText2);
        userNameET = findViewById(R.id.userNameEditText2);
        emailET = findViewById(R.id.emailEditText2);
        passwordET = findViewById(R.id.passwordEditText2);
        updateButton = findViewById(R.id.updateButton2);
        profileImage2 = findViewById(R.id.visit_profile_image2);
    }

//    private void sendUserToMainActivity() {
//        Intent intent = new Intent(ProfileActivity.this, UserActivity.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
//        startActivity(intent);
//        finish();
//    }
}