package com.example.ecommerce;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ecommerce.Model.Cart;
import com.example.ecommerce.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminUserActivityProducts extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layout;
    private static DatabaseReference ref;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);

        uid=getIntent().getStringExtra("pid");
        ref=FirebaseDatabase.getInstance().getReference().child("cart").child("Admin View").child(uid).child("Products");

        recyclerView=findViewById(R.id.rv3);
        recyclerView.setHasFixedSize(true);

        //next=(Button)findViewById(R.id.cart_next_button);

        layout=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart>().setQuery(ref,Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {


                holder.txtProductQuantity.setText("Quantity="+model.getQuantity());
                holder.txtProductPrice.setText("Price="+model.getPrice());
                holder.txtProductName.setText(model.getName());

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
    }
}
