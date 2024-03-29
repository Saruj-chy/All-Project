package com.example.allproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allproject.MainActivity;
import com.example.allproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.allproject.MainActivity.sharedSaved;


public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private TextInputEditText userEmail, userPassword;
    private TextView needNewAccountLink;

    private FirebaseAuth mAuth;
    private String currentUserId;
    private FirebaseUser currentUSer ;
    private CollectionReference memberRef ;

    private ProgressDialog loadingBar;
    private String memberState ;

    //=========   sharedprefarence
    String SHARED_PREFS = "codeTheme";
    String state = "state";
    String getState ;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        memberRef = FirebaseFirestore.getInstance().collection("Members");
        currentUSer = mAuth.getCurrentUser() ;

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);


        initialFields();

        needNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToRegisterActivity();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowUserToLogn();
            }
        });
    }

    private void AllowUserToLogn() {
        String email = userEmail.getText().toString();
        final String password = userPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Please enter your email..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Please enter your password..", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please wait....");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                currentUserId = mAuth.getCurrentUser().getUid() ;

                                memberRef.document(currentUserId).get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.exists()) {
                                                    memberState = documentSnapshot.getString("memberState");
                                                    sharedSaved(sharedPreferences, state, memberState) ;
                                                    if(memberState.equals("admin")){
                                                        SendUserToMainActivity();
                                                    } else{
                                                        SendUserToFragmentActivity();
                                                    }
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                                                Log.d("TAG", e.toString());
                                            }
                                        });

                                Log.d("TAG","state: "+ memberState) ;

//                                Toast.makeText(LoginActivity.this, "CurrentUserId: "+currentUserId, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String message = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getState = sharedPreferences.getString(state, "");
        Log.d("TAGs", "memstate: "+memberState) ;
        Log.d("TAGs", "state: "+getState) ;
//        Toast.makeText(this, "state: "+getState, Toast.LENGTH_SHORT).show();
        if(currentUSer != null && getState.equals("admin")){
            SendUserToMainActivity();
        }
        else if(currentUSer != null){
            SendUserToFragmentActivity();
        }
    }

    private void initialFields() {
        loginButton = findViewById(R.id.login_button);

        userEmail = findViewById(R.id.login_email_input_text);
        userPassword = findViewById(R.id.login_password_input_text);
        needNewAccountLink = findViewById(R.id.need_new_account_link);

        loadingBar = new ProgressDialog(this);
    }
    private void SendUserToRegisterActivity() {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }

    private void SendUserToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    private void SendUserToFragmentActivity() {
        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }



}