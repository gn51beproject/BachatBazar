package com.example.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Prevalent.Prevalent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class loginActivity extends AppCompatActivity {
//test
    private EditText phn,pw;
    private Button login;
    private CheckBox check;
    private TextView admin;
    private DatabaseReference ref;
    private ProgressDialog loading;
    private String dbName="Member";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phn=(EditText)findViewById(R.id.phone);
        pw=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login1);
        check=(CheckBox)findViewById(R.id.check);
        admin=(TextView)findViewById(R.id.admin_panel_link);
        loading=new ProgressDialog(this);


        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(admin.getText().equals("I am a Seller?")) {
                    dbName = "Admin";
                    admin.setText("Not A seller?");
                    login.setText("Seller Login");
                }
                else {
                    dbName = "Member";
                    admin.setText("I am a Seller?");
                    login.setText("Login");

                }
            }
        });
        Paper.init(this);
        FirebaseApp.initializeApp(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ph=phn.getText().toString();
                String ps=pw.getText().toString();

                Toast.makeText(getBaseContext(),"RRRRR",Toast.LENGTH_SHORT).show();
                loading.setTitle("Login Activity");
                loading.setMessage("Checking Credentials...");
                loading.setCanceledOnTouchOutside(false);
                loading.show();
                loginAct(ph,ps);
            }
        });
    }


    private void loginAct(String ph, String ps) {

        if(TextUtils.isEmpty(ph)){
            Toast.makeText(getBaseContext(),"Enter Phone Number",Toast.LENGTH_SHORT).show();
            loading.dismiss();
        }
        else if(TextUtils.isEmpty(ps)){
            Toast.makeText(getBaseContext(),"Enter Password",Toast.LENGTH_SHORT).show();
            loading.dismiss();
        }
        else {

            validateLogin(ph,ps);


        }
    }

    private void validateLogin(final String ph,final String ps) {
        Toast.makeText(getBaseContext(),"WWWWW",Toast.LENGTH_SHORT).show();
        if(check.isChecked()){
            Paper.book().write(Prevalent.userPhoneKey,ph);
            Paper.book().write(Prevalent.userPasswordKey,ps);
        }
        ref= FirebaseDatabase.getInstance().getReference().child("Member");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                    if (dataSnapshot.child(dbName).child(ph).exists()) {

                        Users data = dataSnapshot.child(dbName).child(ph).getValue(Users.class);

                        if (data.getPassword().equals(ps)) {
                            Toast.makeText(getBaseContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                            if (dbName.equals("Member")) {
                                //Paper.book().write(Prevalent.currentonlineUsers,data);

                                Prevalent.currentonlineUsers=data;
                                Intent intent1 = new Intent(loginActivity.this, HomeActivity.class);
                                startActivity(intent1);
                            } else {
                                Prevalent.currentonlineUsers=data;
                                Intent intent1 = new Intent(loginActivity.this, AdminCatagoryActivity.class);
                                startActivity(intent1);

                            }
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Please Register First", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
