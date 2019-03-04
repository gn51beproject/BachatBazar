package com.example.ecommerce.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerce.Interface.itemClickListner;
import com.example.ecommerce.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtProductName,txtProductQuantity,txtProductPrice,txtProductSeller;
    public ImageView txtProductImg;
    Button ShowOrderBtn;
    private itemClickListner ItemClickListner;


    public CartViewHolder(View itemView) {
        super(itemView);

        txtProductName=itemView.findViewById(R.id.cart_items_name);
        txtProductQuantity=itemView.findViewById(R.id.cart_items_quantity);
        txtProductPrice=itemView.findViewById(R.id.cart_items_price);
        txtProductImg=itemView.findViewById(R.id.cart_items_img);
        txtProductSeller=itemView.findViewById(R.id.cart_items_seller);

    }


    @Override
    public void onClick(View view) {

        ItemClickListner.onClick(view,getAdapterPosition(),false);

    }
}
