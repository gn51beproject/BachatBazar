package com.example.ecommerce;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerce.Model.Cart;
import com.example.ecommerce.Model.MyOrders;
import com.example.ecommerce.Prevalent.Prevalent;
import com.example.ecommerce.ViewHolder.CartViewHolder;
import com.example.ecommerce.ViewHolder.MyOrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyOrdersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layout;
    private static DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        recyclerView=findViewById(R.id.rv5);
        recyclerView.setHasFixedSize(true);

        //next=(Button)findViewById(R.id.cart_next_button);

        layout=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
    }

    protected void onStart() {
        super.onStart();

        ref= FirebaseDatabase.getInstance().getReference().child("seller products").child(Prevalent.currentonlineUsers.getPhone()).child("products");
        FirebaseRecyclerOptions<MyOrders> options=new FirebaseRecyclerOptions.Builder<MyOrders>().setQuery(ref,MyOrders.class).build();

        FirebaseRecyclerAdapter<MyOrders,MyOrderViewHolder> adapter=new FirebaseRecyclerAdapter<MyOrders, MyOrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyOrderViewHolder holder, final int position, @NonNull MyOrders model) {

                holder.order2Name.setText(model.name);
                holder.order2Quantity.setText("Quantity :"+model.quantity);
                holder.order2Price.setText("Price :"+model.price);
                holder.order2Time.setText("Time :"+model.date+" "+model.time);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CharSequence options[]=new CharSequence[]{

                                "Yes",
                                "No"
                        };

                        AlertDialog.Builder builder=new AlertDialog.Builder(MyOrdersActivity.this);
                        builder.setTitle("Have You Shipped This Order?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(i==0){
                                    String s=getRef(position).getKey();
                                    ref=FirebaseDatabase.getInstance().getReference().child("seller products").child(Prevalent.currentonlineUsers.getPhone()).child("products").child(s);
                                    ref.removeValue();
                                }
                                else if(i==1){
                                    finish();
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }



            @NonNull
            @Override
            public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myorders,parent,false);

                MyOrderViewHolder holder=new MyOrderViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
