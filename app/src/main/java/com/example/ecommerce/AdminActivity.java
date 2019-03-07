package com.example.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecommerce.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {

    private String category,description,price,name,minPrice,saveCurrentDate,saveCurrentTime,productRandomKey,downloadImageUrl;
    private ImageView img;
    private EditText inputName,inputDescription,inputPrice,inputMinPrice,inputQuantity;
    private Button add;
    private static final int GalleryPick=1;
    private Uri uriImage;
    private StorageReference ProductImageRef;
    private DatabaseReference ref;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        inputName=(EditText)findViewById(R.id.select_product_name);
        inputDescription=(EditText)findViewById(R.id.select_product_description);
        inputPrice=(EditText)findViewById(R.id.select_product_price);
        inputMinPrice=(EditText)findViewById(R.id.select_product_min_price);
        inputQuantity=(EditText)findViewById(R.id.select_product_quantity);
        img=(ImageView)findViewById(R.id.select_product_image);
        add=(Button)findViewById(R.id.add);
        category=getIntent().getExtras().get("category").toString();
        Toast.makeText(getBaseContext(),category,Toast.LENGTH_SHORT).show();
        loading=new ProgressDialog(this);

        FirebaseApp.initializeApp(this);
        ProductImageRef= FirebaseStorage.getInstance().getReference().child("Product Image");
        ref=FirebaseDatabase.getInstance().getReference().child("product");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getBaseContext(),category,Toast.LENGTH_SHORT).show();

                loading.setTitle("Add Product");
                loading.setMessage("Adding Product...");
                loading.setCanceledOnTouchOutside(false);
                loading.show();
                validateData();

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void validateData() {

        name=inputName.getText().toString();
        description=inputDescription.getText().toString();
        price=inputPrice.getText().toString();
        minPrice=inputMinPrice.getText().toString();
        if(uriImage==null){
            Toast.makeText(getBaseContext(),"Image is Compulsary...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(name)){
            Toast.makeText(getBaseContext(),"Name is Compulsary...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(description)){
            Toast.makeText(getBaseContext(),"Description is Compulsary...",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(price)){
            Toast.makeText(getBaseContext(),"Price is Compulsary...",Toast.LENGTH_SHORT).show();
        }
        else {
            insertData(name,description,price,minPrice);
        }
    }

    private void insertData(String name, String description, String price,String minPrice) {
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        productRandomKey=saveCurrentDate + saveCurrentTime;

        final StorageReference filepath=ProductImageRef.child(uriImage.getLastPathSegment()+productRandomKey);
        final UploadTask uploadTask=filepath.putFile(uriImage);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.toString();
                Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getBaseContext(),"Image upload Success",Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful()){
                            throw task.getException();
                        }

                        downloadImageUrl=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful()){

                            downloadImageUrl=task.getResult().toString();
                            Toast.makeText(getBaseContext(),"Image Uploaded to database succceessfully..",Toast.LENGTH_SHORT).show();
                            saveProductInfo();
                        }
                        else {
                            Toast.makeText(getBaseContext(),"Image Upload Failed..",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    private void saveProductInfo() {
        HashMap<String,Object> productMap=new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",description);
        productMap.put("name",name);
        productMap.put("price",price);
        productMap.put("minprice",minPrice);
        productMap.put("quantity",inputQuantity.getText().toString());
        productMap.put("category",category);
        productMap.put("image",downloadImageUrl);
        productMap.put("seller", Prevalent.currentonlineUsers.getPhone());


        ref.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getBaseContext(),"Data Added  succceessfully..",Toast.LENGTH_SHORT).show();
                    loading.dismiss();

                }
                else {
                    String message=task.getException().toString();
                    Toast.makeText(getBaseContext(),"Data Update Failed.."+message,Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode==RESULT_OK  && data!=null){

            uriImage=data.getData();
            img.setImageURI(uriImage);
        }
    }
}
