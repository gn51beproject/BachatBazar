package com.example.ecommerce;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ecommerce.Model.Products;
import com.example.ecommerce.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetails extends AppCompatActivity {

    int quantity;
    TextView name,description,price;
    ImageView img;
    String state="Normal";
    ElegantNumberButton button;
    FloatingActionButton cart;
    private static String pid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        name=(TextView)findViewById(R.id.product_details_name);
        description=(TextView)findViewById(R.id.product_details_description);
        price=(TextView)findViewById(R.id.product_details_price);
        img=(ImageView)findViewById(R.id.product_details_img);
        button=(ElegantNumberButton)findViewById(R.id.product_details_button);
        cart=(FloatingActionButton)findViewById(R.id.product_details_cart);
        pid=getIntent().getStringExtra("pid");
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(state.equals("not shipped") || state.equals("not placed")){
                    Toast.makeText(getBaseContext(),"You can purchase more products,once your order is shipped or confirmed",Toast.LENGTH_SHORT).show();
                }
                else {

                    int n=Integer.parseInt(button.getNumber());

                    if(n<=quantity) {
                        addingToCartList();
                    }
                    else {
                        Toast.makeText(getBaseContext(),"Sorry only "+Integer.valueOf(quantity)+" items are currently available !!",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        getProductDetails(pid);
    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckOrderStatus();
    }

    private void addingToCartList() {
        final String saveCurrentDate;
        final String saveCurrentTime;
        final HashMap<String,Object> cartData=new HashMap<>();
        Calendar calForDate=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());


        final DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("cart");

        final DatabaseReference ref2=FirebaseDatabase.getInstance().getReference().child("product").child(pid);

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    Toast.makeText(getBaseContext(),dataSnapshot.child("image").getValue().toString(),Toast.LENGTH_LONG).show();

                    String img1 = dataSnapshot.child("image").getValue().toString();


                    cartData.put("pid",pid);
                    cartData.put("name",name.getText().toString());
                    cartData.put("description",description.getText().toString());
                    cartData.put("price",price.getText().toString());
                    cartData.put("date",saveCurrentDate);
                    cartData.put("img",img1);
                    cartData.put("time",saveCurrentTime);
                    cartData.put("quantity",button.getNumber());
                    cartData.put("discount","");
                    String s=getIntent().getStringExtra("seller");
                    cartData.put("seller",s);
                    cartData.put("buyer",Prevalent.currentonlineUsers.getPhone());
                    final DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("seller products").child(s).child("products");

                    ref1.child(pid).updateChildren(cartData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(getBaseContext(),"success R",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getBaseContext(),"Failed R",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    ref.child("User View").child(Prevalent.currentonlineUsers.getPhone()).child("Products").child(pid).updateChildren(cartData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                ref.child("Admin View").child(Prevalent.currentonlineUsers.getPhone()).child("Products").child(pid).updateChildren(cartData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){
                                            Toast.makeText(getBaseContext(),"Product Added Successfullly...",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(ProductDetails.this,HomeActivity.class));
                                        }
                                    }
                                });

                            }
                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }

    private void getProductDetails(String pid) {

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("product");

        ref.child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Products products=dataSnapshot.getValue(Products.class);
                    name.setText(products.getName());
                    description.setText(products.getDescription());
                    price.setText(products.getPrice());
                    quantity=Integer.parseInt(products.getQuantity());
                    Picasso.get().load(products.getImage()).into(img);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void CheckOrderStatus(){
        DatabaseReference ref;
        ref=FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentonlineUsers.getPhone());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    String shippingState=dataSnapshot.child("state").getValue().toString();
                    //String userName=dataSnapshot.child("name").getValue().toString();

                    if(shippingState.equals("shipped")){

                        state="order shipped";
                    }
                    else if(shippingState.equals("not shipped")){
                        state="order placed";
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
