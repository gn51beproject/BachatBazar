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
import android.widget.TextView;
import android.widget.Toast;

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

public class HomeOrders extends AppCompatActivity {

    private TextView line;
    private RecyclerView recyclerView;
    private static DatabaseReference ref;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_orders);

        line=(TextView)findViewById(R.id.home_orders);
        recyclerView=findViewById(R.id.home_orders_rv);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        ref= FirebaseDatabase.getInstance().getReference().child("Buyer Products").child(Prevalent.currentonlineUsers.getPhone())
                .child("Products");

        //FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(ref, Cart.class).build();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Toast.makeText(getBaseContext(),snapshot.getKey(),Toast.LENGTH_LONG).show();
                    DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("Buyer Products").child(Prevalent.currentonlineUsers.getPhone())
                            .child("Products").child(snapshot.getKey());
                    FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(ref1, Cart.class).build();

                    FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {

                            //DatabaseReference ref1=ref;

                            holder.txtProductName.setText(model.getName());
                            holder.txtProductPrice.setText("Price:" + model.getPrice());
                            holder.txtProductQuantity.setText("Quantity :" + model.getQuantity());
                        }

                        @NonNull
                        @Override
                        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_view, parent, false);

                            CartViewHolder holder = new CartViewHolder(view);
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









    }

}
