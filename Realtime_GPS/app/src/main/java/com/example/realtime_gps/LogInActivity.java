package com.example.realtime_gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    Button logIn;
    TextView signUp;
    TextView forgot;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    EditText email;
    EditText pass;



    protected void onStart(){
        super.onStart();
        // Trước khi mở trang logIn kiểm tra xem người dùng đã đăng nhập hay chưa nếu đăng nhập rồi
        // Thì start MainActivity
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            Intent intent = new Intent(LogInActivity.this , MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void findID(){
        logIn = findViewById(R.id.Button_LogIn);
        signUp = findViewById(R.id.TextView_LogIn_orSignUp);
        forgot = findViewById(R.id.TextView_ForgotPassword);
        email = findViewById(R.id.EditText_LogIn_Email);
        pass = findViewById(R.id.EditText_LogIn_Passwword);
        auth = FirebaseAuth.getInstance();
    }

    private void logInListener(){
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void sigUpListener(){
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
//                finish();
            }
        });
    }

    private void forGotListener(){
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, ForgotPassword.class);
                startActivity(intent);
//                finish();
            }
        });
    }


    private View.OnClickListener logInonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //Copy của bạn ấn độ :)))

            String txt_email = email.getText().toString();
            String txt_password = pass.getText().toString();

            if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                Toast.makeText(LogInActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
            } else {

                auth.signInWithEmailAndPassword(txt_email, txt_password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LogInActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        findID();

        sigUpListener();

        logInListener();

        forGotListener();

        logIn.setOnClickListener(logInonClickListener);



    }


}
