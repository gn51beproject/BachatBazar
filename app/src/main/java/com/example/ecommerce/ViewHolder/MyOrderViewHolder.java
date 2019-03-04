package com.example.ecommerce.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ecommerce.Interface.itemClickListner;
import com.example.ecommerce.R;

public class MyOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public itemClickListner ItemClickListner;
    public TextView order2Name,order2Time,order2Price,order2Quantity;

    public MyOrderViewHolder(View itemView) {
        super(itemView);

        order2Name=(TextView)itemView.findViewById(R.id.order2_product_name);
        order2Quantity=(TextView)itemView.findViewById(R.id.order2_product_quantity);
        order2Price=(TextView)itemView.findViewById(R.id.order2_product_price);
        order2Time=(TextView)itemView.findViewById(R.id.order2_product_time);
    }


    @Override
    public void onClick(View view) {
        ItemClickListner.onClick(view,getAdapterPosition(),false);
    }
}
