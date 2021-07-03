 package com.swayam.chatter.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.swayam.chatter.R;
import com.swayam.chatter.Modelclass.User;

 public class ResgistrationActivity extends AppCompatActivity {
    TextView txt_signin, btn_signup;
    ImageView profile_image;
    EditText reg_email, reg_Password, reg_cPassword, reg_name;
    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Uri imageuri;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String imageURI;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgistration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait....");
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();


        txt_signin = findViewById(R.id.txt_signin);
        profile_image = findViewById(R.id.profile_image);
        reg_email = findViewById(R.id.reg_email);
        reg_name = findViewById(R.id.reg_name);
        reg_Password = findViewById(R.id.reg_Pass);
        reg_cPassword = findViewById(R.id.reg_cPass);
        btn_signup = findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String name = reg_name.getText().toString();
                String email = reg_email.getText().toString();
                String password = reg_Password.getText().toString();
                String cPassword = reg_cPassword.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) ||
                        TextUtils.isEmpty(password) || TextUtils.isEmpty(cPassword)) {
                    progressDialog.dismiss();
                    Toast.makeText(ResgistrationActivity.this, "Please enter valid Data", Toast.LENGTH_SHORT).show();
                } else if (email.matches(emailPattern)) {
                    reg_email.setError("Plz enter valid email");
                    progressDialog.dismiss();

                    Toast.makeText(ResgistrationActivity.this, "Plz enter valid email", Toast.LENGTH_SHORT).show();
                } else if (password.equals(cPassword) || password.length() > 6) {
                    progressDialog.dismiss();

                    Toast.makeText(ResgistrationActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();

                                DatabaseReference reference = database.getReference().child("user").child(auth.getUid());
                                StorageReference storageReference = storage.getReference().child("upload").child(auth.getUid());
                                if (imageuri != null) {
                                    storageReference.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageURI = uri.toString();
                                                        User user = new User(auth.getUid(), name, email, imageURI);
                                                        reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    startActivity(new Intent(ResgistrationActivity.this, HomeActivity.class));
                                                                } else {
                                                                    Toast.makeText(ResgistrationActivity.this, "Error in creating a new user", Toast.LENGTH_SHORT).show();
                                                                }

                                                            }
                                                        });

                                                    }
                                                });
                                            }

                                        }
                                    });
                                } else {
                                    imageURI = "https://firebasestorage.googleapis.com/v0/b/chatter-2de5c.appspot.com/o/profile.png?alt=media&token=6ac51617-fd37-4c21-864e-ba514187a0a7";
                                    User user = new User(auth.getUid(), name, email, imageURI);
                                    Task<Void> error_in_creating_a_newuser = reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                startActivity(new Intent(ResgistrationActivity.this, HomeActivity.class));
                                            } else {
                                                Toast.makeText(ResgistrationActivity.this, "Error in creating a newuser", Toast.LENGTH_SHORT).show();
                                            }

                                        }


                                    });
                                }
                            }
                            profile_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 123);
                                }
                            });

                        }


                    });

                }


            }


        }); txt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResgistrationActivity.this, LoginActivity.class));
            }




    });
}}