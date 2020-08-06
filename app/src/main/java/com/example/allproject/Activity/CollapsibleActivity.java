package com.example.allproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.allproject.R;
import com.google.android.material.textfield.TextInputEditText;

public class CollapsibleActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView,  textInputTextView ;
    private Button editBtn , clickBtn ;

    private TextInputEditText emailText, passwordText ;


    private  String text,  email, password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsible);

        editText = findViewById(R.id.editText) ;
        textView = findViewById(R.id.textView) ;
        editBtn = findViewById(R.id.editBtn) ;
        emailText = findViewById(R.id.email) ;
        passwordText = findViewById(R.id.password) ;
        clickBtn = findViewById(R.id.click) ;
        textInputTextView = findViewById(R.id.textInputTextView) ;

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = editText.getText().toString().trim() ;
                textView.setText(text);
            }
        });


        clickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailText.getText().toString().trim() ;
                password = passwordText.getText().toString().trim() ;

                textInputTextView.setVisibility(View.VISIBLE);
                textInputTextView.setText("Email: "+ email + "\n" + " Password: "+password);

            }
        });
    }
}