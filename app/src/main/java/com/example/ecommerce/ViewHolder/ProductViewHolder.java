package com.example.ecommerce.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ecommerce.Interface.itemClickListner;
import com.example.ecommerce.R;
import com.rey.material.widget.ImageView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtProductName,txtProductDescription,txtProductPrice,txtSellerName;

    public ImageView productImage;
    public itemClickListner listner;
    public ProductViewHolder(View itemView) {
        super(itemView);

        txtProductName=(TextView)itemView.findViewById(R.id.product_name);
        //txtSellerName=(TextView)itemView.findViewById(R.id.product_name);
        txtProductDescription=(TextView)itemView.findViewById(R.id.product_description);
        txtProductPrice=(TextView)itemView.findViewById(R.id.product_price);
        productImage=(ImageView)itemView.findViewById(R.id.product_image1);


    }

    public void setItemClickListner(itemClickListner listner){

        this.listner=listner;

    }
    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);
    }
}
