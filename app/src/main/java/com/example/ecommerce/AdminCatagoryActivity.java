package com.example.ecommerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminCatagoryActivity extends AppCompatActivity {

    ImageView fruits,clothes,foods,agro,ayurvedik,craft,paperbags;
    Button logout,check_new_orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_catagory);
        fruits=(ImageView)findViewById(R.id.fruits);
        foods=(ImageView)findViewById(R.id.foods);
        clothes=(ImageView)findViewById(R.id.clothes);
        agro=(ImageView)findViewById(R.id.agro);
        craft=(ImageView)findViewById(R.id.craft);
        ayurvedik=(ImageView)findViewById(R.id.ayurved);
        paperbags=(ImageView)findViewById(R.id.paperbags);

        logout=(Button)findViewById(R.id.admin_logout);
        check_new_orders=(Button)findViewById(R.id.admin_check_orders);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCatagoryActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        check_new_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCatagoryActivity.this,MyOrdersActivity.class);
                startActivity(intent);
            }
        });

        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminActivity.class);
                intent.putExtra("category","fruits");
                startActivity(intent);

            }
        });
        foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminActivity.class);
                intent.putExtra("category","foods");
                startActivity(intent);

            }
        });
        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminActivity.class);
                intent.putExtra("category","cloth");
                startActivity(intent);

            }
        });
        agro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminActivity.class);
                intent.putExtra("category","agro");
                startActivity(intent);

            }
        });
        paperbags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminActivity.class);
                intent.putExtra("category","paperbag");
                startActivity(intent);

            }
        });
        craft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminActivity.class);
                intent.putExtra("category","craft");
                startActivity(intent);

            }
        });
        ayurvedik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminActivity.class);
                intent.putExtra("category","ayurvedik");
                startActivity(intent);

            }
        });
    }
}
