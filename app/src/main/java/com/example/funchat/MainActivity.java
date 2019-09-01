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

public class MainActivity extends AppCompatActivity {

    EditText et_username, et_pass;
    Button btn_signup;
    String username = "", passowrd = "";
    private FirebaseAuth mAuth;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = findViewById(R.id.et_username_signup);
        et_pass = findViewById(R.id.et_pass_signup);
        btn_signup =findViewById(R.id.btn_signup);
        login = findViewById(R.id.login_signup);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = et_username.getText().toString();
                passowrd = et_pass.getText().toString();

                if (username.isEmpty() || passowrd.isEmpty()){
                    showToast("Please fill credentials carefully");
                }else{
                    mAuth.createUserWithEmailAndPassword(username,passowrd)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        showToast("User registered : "+mAuth.getCurrentUser());
                                        startActivity(new Intent(MainActivity.this,ChatActivity.class));
                                        finish();
                                    }else{
                                        showToast("User registration failed!"+task.getException());
                                    }
                                }
                            });
                }

            }
        });

    }

    private void showToast(String msg){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}
