package com.example.allproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allproject.Class.Members;
import com.example.allproject.MainActivity;
import com.example.allproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userFirstName, userLastName, userName, userEmail, userPassword, userConfirmPassword;
    private Button createAccountButton;
    private TextView alreadyHaveAccountLink;

    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;
    String currentId;

    List<Members> members;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference("Members");

        initialFields();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAccount();
//                addMemberAdded() ;
            }
        });
    }


    private void initialFields() {
        userFirstName = findViewById(R.id.register_first_name);
        userLastName = findViewById(R.id.register_last_name);
        userName = findViewById(R.id.register_UserName);
        userEmail = findViewById(R.id.register_email);
        userPassword = findViewById(R.id.register_password);
        userConfirmPassword = findViewById(R.id.register_confirm_password);
        createAccountButton = findViewById(R.id.register_button);
        alreadyHaveAccountLink = findViewById(R.id.already_account_link);
        loadingBar = new ProgressDialog(this);

        members = new ArrayList<>();
    }

    private void CreateNewAccount() {
        final String firstName = userFirstName.getText().toString();
        final String lastName = userLastName.getText().toString();
        final String username = userName.getText().toString();
        final String email = userEmail.getText().toString();
        final String password = userPassword.getText().toString();
        String confirm_password = userConfirmPassword.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(getApplicationContext(), "Please enter your UserName..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Please enter your email..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password) || TextUtils.isEmpty(confirm_password)){
            Toast.makeText(getApplicationContext(), "Please enter your password..", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirm_password)){
            Toast.makeText(getApplicationContext(), "Your password is not match...", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Creating New Account");
            loadingBar.setMessage("Plaese wait, while we are creating new account for you...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();



            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                currentId = mAuth.getCurrentUser().getUid() ;
                                Members member = new Members(firstName, lastName, username, email, password) ;
                                rootRef.child(currentId).setValue(member)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Clear();
                                                SendUserToMainActivity();
                                                Toast.makeText(RegistrationActivity.this, "Account created Successfully", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                        });

                            }
                            else{
                                String message = task.getException().toString();
                                Toast.makeText(RegistrationActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }

    }
    private void Clear() {
        userFirstName.setText("");
        userLastName.setText("");
        userName.setText("");
        userEmail.setText("");
        userPassword.setText("");
        userConfirmPassword.setText("");
    }

    private void SendUserToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}