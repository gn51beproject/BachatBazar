package com.example.ecommerce;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.example.ecommerce.Model.Cart;
import com.example.ecommerce.Prevalent.Prevalent;
import com.example.ecommerce.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserOrdersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private static DatabaseReference ref,ref1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_orders);

        FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentonlineUsers.getPhone()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    ref= FirebaseDatabase.getInstance().getReference().child("cart");
                    FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart>().setQuery(ref.child("User View").child(Prevalent.currentonlineUsers.getPhone()).child("Products"),Cart.class).build();

                    FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {

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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref= FirebaseDatabase.getInstance("Orders").getReference("Admin View").child("");
    }
}
