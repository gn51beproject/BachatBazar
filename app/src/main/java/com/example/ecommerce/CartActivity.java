package com.example.ecommerce;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Model.Cart;
import com.example.ecommerce.Prevalent.Prevalent;
import com.example.ecommerce.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity {

    Button next;
    TextView msg1;
    TextView total_price;
    private static int int_total_price=0;
    private RecyclerView recyclerView;
    private static DatabaseReference ref;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        total_price=(TextView)findViewById(R.id.cart_total_price);
        msg1=(TextView)findViewById(R.id.msg1);
        recyclerView=findViewById(R.id.cart_rv);
        recyclerView.setHasFixedSize(true);

        next=(Button)findViewById(R.id.cart_next_button);

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




    }


    @Override
    protected void onStart() {
        super.onStart();
        //CheckOrderStatus();

        ref= FirebaseDatabase.getInstance().getReference().child("cart");
        FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart>().setQuery(ref.child("User View").child(Prevalent.currentonlineUsers.getPhone()).child("Products"),Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {

                int price=Integer.valueOf(model.getPrice())*Integer.valueOf(model.getQuantity());
                holder.txtProductQuantity.setText("Quantity="+model.getQuantity());
                holder.txtProductPrice.setText("Price="+Integer.toString(price));
                holder.txtProductName.setText(model.getName());
                Picasso.get().load(model.getImg()).into(holder.txtProductImg);



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[]=new CharSequence[]{
                          "Edit","Remove"
                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(i==0){

                                    Intent intent=new Intent(CartActivity.this,ProductDetails.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);
                                }
                                if(i==1){
                                    ref.child("User View").child(Prevalent.currentonlineUsers.getPhone()).child("Products").child(model.getPid()).removeValue();
                                    ref.child("Admin View").child(Prevalent.currentonlineUsers.getPhone()).child("Products").child(model.getPid()).removeValue();


                                }
                            }
                        });
                        builder.show();
                    }
                });

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        total_price.setText("Total Price ="+Integer.toString(int_total_price));
                        Intent intent=new Intent(CartActivity.this,ConfirmFinalOrdersActivity.class);
                        intent.putExtra("price",Integer.toString(int_total_price));
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_view,parent,false);

                CartViewHolder holder=new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
        //total_price.setText("Total Price ="+Integer.toString(int_total_price));

    }

    private void CheckOrderStatus(){

        ref=FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentonlineUsers.getPhone());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    if(dataSnapshot.child("state").getValue()!= null){

                        String shippingState=dataSnapshot.child("state").getValue().toString();
                        String userName=dataSnapshot.child("name").getValue().toString();

                        if(shippingState.equals("shipped")){

                            msg1.setText("Dear "+userName+"\nYour Order is placed successfully");
                            recyclerView.setVisibility(View.GONE);
                            msg1.setVisibility(View.VISIBLE);
                            msg1.setText("Congratulations,Your order has been shipped.Soon You will Recieve Order At Your Door");
                            next.setVisibility(View.GONE);
                            Toast.makeText(getBaseContext(),"You can buy more products,once you recieve final order ",Toast.LENGTH_SHORT).show();
                        }
                        else if(shippingState.equals("not shipped")){
                            msg1.setText("Shipping State = Not Shipped");
                            recyclerView.setVisibility(View.GONE);
                            msg1.setVisibility(View.VISIBLE);
                            next.setVisibility(View.GONE);
                            Toast.makeText(getBaseContext(),"You can buy more products,once you recieve final order ",Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getBaseContext(),"Success",Toast.LENGTH_LONG).show();
                    }
                    else if(dataSnapshot.child("name").getValue()!= null){
                       Toast.makeText(getBaseContext(),"2nd",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getBaseContext(),"Error",Toast.LENGTH_LONG).show();
                    }

                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
