package com.example.ecommerce;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.Model.Cart;
import com.example.ecommerce.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ConfirmFinalOrdersActivity extends AppCompatActivity {

    EditText name,address,city,phone;
    Button confirm;
    String price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_orders);
        name=(EditText)findViewById(R.id.final_order_name);
        address=(EditText)findViewById(R.id.final_order_home_address);
        phone=(EditText)findViewById(R.id.final_order_phone);
        city=(EditText)findViewById(R.id.final_order_city);

        confirm=(Button)findViewById(R.id.final_order_register);

        price= getIntent().getStringExtra("price");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check();
            }
        });
    }

    private void check() {

        if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(getBaseContext(),"Please Enter Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone.getText().toString())){
            Toast.makeText(getBaseContext(),"Please Enter Phone",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(address.getText().toString())){
            Toast.makeText(getBaseContext(),"Please Enter Home Address",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(city.getText().toString())){
            Toast.makeText(getBaseContext(),"Please Enter City",Toast.LENGTH_SHORT).show();
        }
        else {

            confirmOrder();
        }
    }

    private void confirmOrder() {


        String saveCurrentDate,saveCurrentTime;

        Calendar calForDate=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());



        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Orders");

        final HashMap<String,Object> orderData=new HashMap<>();
        orderData.put("name",name.getText().toString());
        orderData.put("phone",phone.getText().toString());
        orderData.put("address",address.getText().toString());
        orderData.put("city",city.getText().toString());
        orderData.put("date",saveCurrentDate);
        orderData.put("time",saveCurrentTime);
        orderData.put("price",price);
        orderData.put("state","shipped");



  //      DatabaseReference ref1;
  //      ref1=FirebaseDatabase.getInstance().getReference().child("cart").child("Admin View");
//        FirebaseRecyclerOptions<Cart> options1=new FirebaseRecyclerOptions.Builder<Cart>().setQuery(ref1,Cart.class).build();





        ref.child(Prevalent.currentonlineUsers.getPhone()).updateChildren(orderData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    String saveCurrentDate,saveCurrentTime,productRandomKey;
                    Calendar calendar=Calendar.getInstance();

                    SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
                    saveCurrentDate=currentDate.format(calendar.getTime());

                    SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
                    saveCurrentTime=currentTime.format(calendar.getTime());

                    productRandomKey=saveCurrentDate + saveCurrentTime;

                    DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("cart").child("User View")
                            .child(Prevalent.currentonlineUsers.getPhone()).child("Products");
                    DatabaseReference ref2=FirebaseDatabase.getInstance().getReference()
                            .child("Buyer Products").child(Prevalent.currentonlineUsers.getPhone()).child("Products");

                    moveGameRoom(ref1,ref2);

                    DatabaseReference ref3=FirebaseDatabase.getInstance().getReference().child("cart")
                            .child("User View").child(Prevalent.currentonlineUsers.getPhone());

                    ref3.addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Get map of users in datasnapshot
                                   // collectPhoneNumbers((Map<String,Object>) dataSnapshot.getValue());
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    //handle databaseError
                                }
                            });


                    FirebaseDatabase.getInstance().getReference().child("cart")
                    .child("User View").child(Prevalent.currentonlineUsers.getPhone()).removeValue();
                    Toast.makeText(getBaseContext(),"Order Confirmed Successfullly...",Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(ConfirmFinalOrdersActivity.this,HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

    }
    private void collectPhoneNumbers(Map<String,Object> users) {

        String name,quantity,price,totalPrice;

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
           // phoneNumbers.add((Long) singleUser.get("name"));
            name=singleUser.get("name").toString();
            quantity=singleUser.get("quantity").toString();
            price=singleUser.get("price").toString();
            int total=Integer.parseInt(quantity)*Integer.parseInt(price);

        }


    }
    private void moveGameRoom(final DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.push().setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {
                            System.out.println("Copy failed");
                        } else {
                            System.out.println("Success");

                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
