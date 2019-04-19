package com.tamersarioglu.officeorginizer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText editText_register_email, editText_register_password;
    TextView textView_already_have_account;
    Button button_register;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        findViewById();
        register();
        returnToLogin();

    }

    private void findViewById() {
        editText_register_email= findViewById(R.id.textView_Register_Email);
        editText_register_password = findViewById(R.id.textView_Register_Password);
        button_register = findViewById(R.id.button_Register);
        textView_already_have_account = findViewById(R.id.textView_Already_Have_Account);
    }

    private void register() {
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = editText_register_email.getText().toString();
                String mPassword = editText_register_password.getText().toString();

                if (mEmail.length()==0 || mPassword.length() == 0){
                    Toast.makeText(RegisterActivity.this, "Email ve Password Alanlarını Doldurunuz", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                            Log.d("Register", task.getResult().getUser().getUid());
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            editText_register_email.setText("");
                            editText_register_password.setText("");
                        } else {
                            Toast.makeText(RegisterActivity.this, "Kayıt Başarısız", Toast.LENGTH_SHORT).show();
                            Log.d("Register", task.getException().getMessage());
                            editText_register_email.setText("");
                            editText_register_password.setText("");
                        }
                    }
                });
            }
        });
    }

    private void returnToLogin() {
        textView_already_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }
}
