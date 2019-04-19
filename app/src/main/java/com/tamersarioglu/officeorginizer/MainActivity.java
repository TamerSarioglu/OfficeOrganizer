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

public class MainActivity extends AppCompatActivity {

    EditText editText_email, editText_password;
    TextView textView_dont_have_account;
    Button button_Login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }

        findViewById();
        goToRegisterPage();
        logIn();


    }

    private void findViewById() {
        editText_email = findViewById(R.id.textView_Login_Email);
        editText_password = findViewById(R.id.textView_Login_Password);
        button_Login = findViewById(R.id.button_Login);
        textView_dont_have_account = findViewById(R.id.textView_Dont_Have_Account);
    }

    private void logIn() {

        button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = editText_email.getText().toString();
                String mPassword = editText_password.getText().toString();

                if (mEmail.length() == 0 || mPassword.length() == 0){
                    Toast.makeText(MainActivity.this, "Lütfen Email ve Password Alanlarını Doldurunuz", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login Başarılı", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            editText_email.setText("");
                            editText_password.setText("");
                            editText_email.isCursorVisible();
                        } else {
                            Toast.makeText(MainActivity.this, "Login Başarısız", Toast.LENGTH_SHORT).show();
                            Log.d("MainActivity", task.getException().getMessage());
                            editText_email.setText("");
                            editText_password.setText("");
                        }
                    }
                });

            }
        });
    }

    private void goToRegisterPage() {
        textView_dont_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }
}
