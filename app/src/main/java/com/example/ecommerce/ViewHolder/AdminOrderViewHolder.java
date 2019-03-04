package com.example.ecommerce.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecommerce.Interface.itemClickListner;
import com.example.ecommerce.R;

public class AdminOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public itemClickListner ItemClickListner;
    public TextView orderName,orderPhone,orderAddress,orderDate,ordertime,orderPrice;
    public Button ShowOrderBtn;

    public AdminOrderViewHolder(View itemView) {
        super(itemView);

        orderName=itemView.findViewById(R.id.order1_user_name);
        orderAddress=itemView.findViewById(R.id.order_address);
        orderPhone=itemView.findViewById(R.id.order_phone_number);
        orderPrice=itemView.findViewById(R.id.order_total_price);
        orderDate=itemView.findViewById(R.id.order_date);
        ordertime=itemView.findViewById(R.id.order_time);
        ShowOrderBtn=itemView.findViewById(R.id.show_order);
    }


    @Override
    public void onClick(View view) {
        ItemClickListner.onClick(view,getAdapterPosition(),false);
    }

}
