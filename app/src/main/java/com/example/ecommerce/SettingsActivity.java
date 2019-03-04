package com.example.ecommerce;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Model.Users;
import com.example.ecommerce.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.rey.material.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class SettingsActivity extends AppCompatActivity {


    private  StorageReference UserImageRef ;
    TextView close,update_img;
    EditText name,address,phone,password;
    CircleImageView img;
    Button submit;
    private static String clicked="";
    private DatabaseReference dbRef;
    private StorageTask uploadTask;
    private StorageReference stRef;
    private Users user;
    private Uri imageUri;
    private String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        FirebaseApp.initializeApp(this);
        close=(TextView)findViewById(R.id.close_settings);
        update_img=(TextView)findViewById(R.id.update_image_settings);
        name=(EditText) findViewById(R.id.update_name_settings);
        address=(EditText) findViewById(R.id.update_adress_settings);
        phone=(EditText) findViewById(R.id.update_phone_settings);
        password=(EditText) findViewById(R.id.update_password_settings);

        img=(CircleImageView)findViewById(R.id.image_settings);

        submit=(Button)findViewById(R.id.submit_settings);
        UserImageRef= FirebaseStorage.getInstance().getReference().child("User Image");
        userInfoDisplay(img,name,address,phone,password);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(getBaseContext(),"X123",Toast.LENGTH_LONG).show();
                if(TextUtils.isEmpty(name.getText().toString())){

                    Toast.makeText(getBaseContext(),"Name is Mandatory",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(phone.getText().toString())){
                    Toast.makeText(getBaseContext(),"Phone is Mandatory",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(getBaseContext(),"Password is Mandatory",Toast.LENGTH_SHORT).show();

                }
                else if(TextUtils.isEmpty(address.getText().toString())){
                    Toast.makeText(getBaseContext(),"Address is Mandatory",Toast.LENGTH_SHORT).show();

                }



                else if(clicked.equals("clicked")){
                    
                    userInfoSaved();
                //    CropImage.activity(imageUri).setAspectRatio(1, 1);
               //     CropImage.activity(imageUri).start((Activity) getBaseContext());
                }
                else {
                    
                    updateOnlyInfo();
                }


            }
        });

        update_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked="clicked";
                CropImage.activity(imageUri).setAspectRatio(1, 1);
                CropImage.activity(imageUri).start(SettingsActivity.this);
            }
        });



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!= null){

            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            imageUri=result.getUri();
            img.setImageURI(imageUri);

        }
        else {
            Toast.makeText(getBaseContext(),"Error,Try Again",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsActivity.this,SettingsActivity.class));
            finish();
        }
    }

    private void userInfoSaved() {

         if(clicked.equals("clicked")){

            uploadImage();
        }


    }

    private void uploadImage() {

        Toast.makeText(getBaseContext(),"upload image()",Toast.LENGTH_LONG).show();
        final ProgressDialog loading=new ProgressDialog(this);
        loading.setTitle("uploading image");
        loading.setMessage("Image is Uploading ....Please Wait");
        loading.show();

        if(imageUri!= null){

            final StorageReference fileRef=UserImageRef.child(Prevalent.currentonlineUsers.getPhone() +".jpg");
            uploadTask=fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUrl=task.getResult();
                        url=downloadUrl.toString();
                        dbRef=FirebaseDatabase.getInstance().getReference().child("Member").child("Member");
                        HashMap<String,Object> userData=new HashMap<>();
                        userData.put("address",address.getText().toString());
                        userData.put("name",name.getText().toString());
                        userData.put("phone",phone.getText().toString());
                        userData.put("password",password.getText().toString());
                        userData.put("profileImg",url);
                        dbRef.child(Prevalent.currentonlineUsers.getPhone()).updateChildren(userData);
                        loading.dismiss();

                        startActivity(new Intent(SettingsActivity.this,MainActivity.class));
                        Toast.makeText(getBaseContext(),"Updated successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        loading.dismiss();
                        Toast.makeText(getBaseContext(),"Error",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
        else {

            Toast.makeText(getBaseContext(),"Image Not Selected...",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateOnlyInfo() {

        Toast.makeText(getBaseContext(),"XXXXX...",Toast.LENGTH_SHORT).show();
        dbRef=FirebaseDatabase.getInstance().getReference().child("Member").child("Member");
        HashMap<String,Object> userData=new HashMap<>();
        userData.put("address",address.getText().toString());
        userData.put("name",name.getText().toString());
        userData.put("phone",phone.getText().toString());
        userData.put("password",password.getText().toString());
   //     userData.put("profileImg",url);
        dbRef.child(Prevalent.currentonlineUsers.getPhone()).updateChildren(userData);
        Paper.init(this);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Member");













        finish();
    }

    private void userInfoDisplay(final CircleImageView img, final EditText name, final EditText address, final EditText phone, final EditText password) {


        dbRef= FirebaseDatabase.getInstance().getReference().child("Member").child("Member").child(Prevalent.currentonlineUsers.getPhone());
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    if(dataSnapshot.child("profileImg").exists()){

                        if(dataSnapshot.child("image").getValue()!= null){
                            String image=dataSnapshot.child("image").getValue().toString();
                            Picasso.get().load(image).into(img);
                        }
                        else if (dataSnapshot.child("name").getValue()!=null){
                            String nm=dataSnapshot.child("name").getValue().toString();
                            name.setText(nm);
                        }
                        else if (dataSnapshot.child("phone").getValue()!=null){
                            String ph=dataSnapshot.child("phone").getValue().toString();
                            phone.setText(ph);
                        }
                        else if (dataSnapshot.child("password").getValue()!= null){
                            String pw=dataSnapshot.child("password").getValue().toString();
                            password.setText(pw);
                        }
                        else if (dataSnapshot.child("address").getValue()!=null){
                            String adrs=dataSnapshot.child("address").getValue().toString();
                            address.setText(adrs);
                        }

                        //String nm=dataSnapshot.child("name").getValue().toString();




                        //Picasso.get().load(image).into(img);







                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
