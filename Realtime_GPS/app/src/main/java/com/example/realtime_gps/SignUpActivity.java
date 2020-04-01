package com.example.realtime_gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText passWord;
    EditText retypePassWord;
    EditText yourPhone;
    Button signUp;
    TextView orLogIn;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;



    String nameString     ;
    String emailString    ;
    String passWordString ;
    String retypeString   ;
    String phoneString    ;

    private void findID(){
        name           = findViewById(R.id.EditText_SignUp_Name);
        email          = findViewById(R.id.EditText_SignUp_Email);
        passWord       = findViewById(R.id.EditText_SignUp_Password);
        retypePassWord = findViewById(R.id.EditText_SignUp_RetypePassword);
        yourPhone      = findViewById(R.id.EditText_SignUp_Phone);
        signUp         = findViewById(R.id.Button_SignUp_SignUp);
        orLogIn        = findViewById(R.id.TextView_SignUp__LoLgin);
        firebaseAuth   = FirebaseAuth.getInstance();
    }

    private void register(){
        firebaseAuth.createUserWithEmailAndPassword(emailString, passWordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", nameString);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("phone",phoneString);

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }

                        else {
                            Toast.makeText(SignUpActivity.this, "You can't register woth this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                ;
    }



    // Xử lý sự kiện khi có người dùng bấm vào signUp
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nameString     = name.getText().toString();
            emailString    = email.getText().toString();
            passWordString = passWord.getText().toString();
            retypeString   = retypePassWord.getText().toString();
            phoneString    = yourPhone.getText().toString();

            // Kiểm tra xem người dùng đã nhập hợp lệ hay chưa và thông báo cho người dùng

            if(nameString == ""||emailString == ""||passWordString == ""||retypeString==""
            ||phoneString == ""){
                Toast.makeText(SignUpActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
            }
            else if (passWordString.length() < 6){
                Toast.makeText(SignUpActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            }
            else if(!passWordString.equals(retypeString)){
                Toast.makeText(SignUpActivity.this, "Password is not retype", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(SignUpActivity.this, "Creating account", Toast.LENGTH_SHORT).show();
                register();
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findID();

        signUp.setOnClickListener(onClickListener);

    }
}
