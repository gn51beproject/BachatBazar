package com.example.ecommerce.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ecommerce.Interface.itemClickListner;
import com.example.ecommerce.R;

public class OrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public itemClickListner ItemClickListner;

    public TextView order3Name,order3Time,order3Price,order3Quantity;

    public OrdersViewHolder(View itemView) {
        super(itemView);
        order3Name=(TextView)itemView.findViewById(R.id.order3_product_name);
        order3Quantity=(TextView)itemView.findViewById(R.id.order3_product_quantity);
        order3Price=(TextView)itemView.findViewById(R.id.order3_product_price);
        order3Time=(TextView)itemView.findViewById(R.id.order3_product_time);

    }


    @Override
    public void onClick(View view) {

        ItemClickListner.onClick(view,getAdapterPosition(),false);

    }
}
