package com.tamersarioglu.officeorginizer;


import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tamersarioglu.officeorginizer.Model.Data;

import java.text.DateFormat;
import java.util.Date;


public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uID = mUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("OfficeOrginizer").child(uID);

        toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Office Organizer");


        findViewByIds();
        setOnClicklisteners();
    }

    private void setOnClicklisteners() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this, "FAB WORKS!!!", Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(HomeActivity.this);

                LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);

                View view = inflater.inflate(R.layout.custominputfilled, null, false);

                myAlertDialog.setView(view);

                final AlertDialog dialog = myAlertDialog.create();

                final EditText title = view.findViewById(R.id.editText_Title);
                final EditText note = view.findViewById(R.id.editText_Notee);

                Button kaydetButton = view.findViewById(R.id.button_save_Note);

                kaydetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mTitle = title.getText().toString().trim();
                        String mNote = note.getText().toString().trim();

                        if (mTitle.length() <= 0 || mNote.length() <= 0) {
                            Toast.makeText(HomeActivity.this, "Title ve Note Alanlarını Doldurunuz", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String id = mDatabase.push().getKey();
                        String date = DateFormat.getDateInstance().format(new Date());
                        Data data = new Data(mTitle, mNote, date, id);
                        mDatabase.child(id).setValue(data);

                        Toast.makeText(getApplicationContext(), "Task Girildi", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();


                    }
                });

                dialog.show();
            }
        });
    }

    private void findViewByIds() {
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }
}
