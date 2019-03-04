package com.example.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Prevalent.Prevalent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button login,signup;
    DatabaseReference ref;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=(Button)findViewById(R.id.main_login_btn);
        signup=(Button)findViewById(R.id.main_join_now_btn);
        loading=new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,loginActivity.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent1);
            }
        });

        Paper.init(this);

        String phn,pw;
        phn=Paper.book().read(Prevalent.userPhoneKey);
        pw=Paper.book().read(Prevalent.userPasswordKey);

        if(phn !="" && pw != ""){

            if(!TextUtils.isEmpty(phn) && !TextUtils.isEmpty(pw)){

                validateLogin(phn,pw);

            }
        }




    }

    private void validateLogin(final String ph,final String ps) {

        FirebaseApp.initializeApp(this);
        ref= FirebaseDatabase.getInstance().getReference().child("Member");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("Member").child(ph).exists()){

                    Users data=dataSnapshot.child("Member").child(ph).getValue(Users.class);

                    if(data.getPassword().equals(ps)){
                        Toast.makeText(getBaseContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                        Intent intent1=new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(intent1);
                    }
                }
                else {
                    Toast.makeText(getBaseContext(),"Please Register First",Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    }

