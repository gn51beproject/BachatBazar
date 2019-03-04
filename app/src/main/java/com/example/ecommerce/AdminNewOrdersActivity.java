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

import com.example.ecommerce.Model.AdminOrders;
import com.example.ecommerce.ViewHolder.AdminOrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrdersActivity extends AppCompatActivity {

    private RecyclerView orderList;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);

        ref= FirebaseDatabase.getInstance().getReference().child("Orders");

        orderList=findViewById(R.id.orders_rv);
        orderList.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options=new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(ref,AdminOrders.class).build();

        FirebaseRecyclerAdapter<AdminOrders,AdminOrderViewHolder> adapter=new FirebaseRecyclerAdapter<AdminOrders, AdminOrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrderViewHolder holder, final int position, @NonNull final AdminOrders model) {

                holder.orderName.setText("Name ="+model.getName());
                holder.orderPhone.setText("Phone ="+model.getPhone());
                holder.orderPrice.setText("Address ="+model.getAddress());
                holder.orderDate.setText("Date ="+model.getDate());
                holder.ordertime.setText("Time ="+model.getTime());
                holder.orderAddress.setText("Total Price =$"+model.getPrice());
                holder.ShowOrderBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s=getRef(position).getKey();
                        Intent intent=new Intent(AdminNewOrdersActivity.this,CheckActivity.class);
                        intent.putExtra("pid",s);
                        startActivity(intent);
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CharSequence options[]=new CharSequence[]{

                                "Yes",
                                "No"
                        };

                        AlertDialog.Builder builder=new AlertDialog.Builder(AdminNewOrdersActivity.this);
                        builder.setTitle("Have You Shipped This Order?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(i==0){
                                    String s=getRef(position).getKey();
                                    ref=FirebaseDatabase.getInstance().getReference().child("Orders").child(s);
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


                //holder.orderName.setText("Name ="+model.getName());
            }

            @NonNull
            @Override
            public AdminOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                return new AdminOrderViewHolder(view);
            }
        };

        orderList.setAdapter(adapter);
        adapter.startListening();



    }


}
