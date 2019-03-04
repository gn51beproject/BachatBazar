package com.example.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private EditText phone, name, password;
    private ProgressDialog loading;
    DatabaseReference ref;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phone = (EditText) findViewById(R.id.reg_phone);
        password = (EditText) findViewById(R.id.reg_password);
        name = (EditText) findViewById(R.id.reg_name);
        register = (Button) findViewById(R.id.register);
        loading = new ProgressDialog(this);
        member=new Member();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String p=phone.getText().toString();
                String nm=name.getText().toString();
                String pw=password.getText().toString();

                if(TextUtils.isEmpty(p)){
                    Toast.makeText(getBaseContext(),"Enter Phone Number",Toast.LENGTH_SHORT).show();
                }
                else if(p.length()!=10){
                    Toast.makeText(getBaseContext(),"Enter 10 Digit Phone Number",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(nm)){
                    Toast.makeText(getBaseContext(),"Enter Phone Number",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pw)){
                    Toast.makeText(getBaseContext(),"Enter Phone Number",Toast.LENGTH_SHORT).show();
                }
                else {

                    loading.setTitle("Create Account");
                    loading.setMessage("Checking Credentials");
                    loading.setCanceledOnTouchOutside(false);
                    loading.show();
                    registerActivity(p,pw,nm);
                }

               // Toast.makeText(this,"Successful",Toast.LENGTH_SHORT);
            }
        });

        Toast.makeText(this,"Successful",Toast.LENGTH_SHORT).show();

    }

    private void registerActivity(final String p,final String pw,final String nm) {

        FirebaseApp.initializeApp(this);
      //  final long phn=Long.parseLong(p);
        ref=FirebaseDatabase.getInstance().getReference().child("Member");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Member").child(p).exists())){

                    HashMap<String,Object> userdata=new HashMap<>();
                    userdata.put("phone",p);
                    userdata.put("name",nm);
                    userdata.put("password",pw);

                    ref.child("Member").child(p).updateChildren(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                loading.dismiss();
                                Toast.makeText(getBaseContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(RegisterActivity.this,loginActivity.class);
                                startActivity(intent);

                            }
                            else {

                                Toast.makeText(getBaseContext(),"Registration Failed",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(getBaseContext(),"This"+p+"already Exists,Try Another Number",Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


}

