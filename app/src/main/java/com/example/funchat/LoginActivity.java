package com.example.funchat;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText et_username, et_pass;
    Button btn_login;
    String username = "", passowrd = "";
    private FirebaseAuth mAuth;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.et_username_login);
        et_pass = findViewById(R.id.et_pass_login);
        btn_login =findViewById(R.id.btn_login);
        signup = findViewById(R.id.signup_login);

        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = et_username.getText().toString();
                passowrd = et_pass.getText().toString();

                if (username.isEmpty() || passowrd.isEmpty()){
                    showToast("Please fill credentials carefully");
                }else{
                    mAuth.signInWithEmailAndPassword(username,passowrd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        showToast("User Login successfully : "+mAuth.getCurrentUser());
                                    }else{
                                        showToast("User Login failed!"+task.getException());
                                    }
                                }
                            });
                }
            }
        });

    }

    private void showToast(String msg){
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}
