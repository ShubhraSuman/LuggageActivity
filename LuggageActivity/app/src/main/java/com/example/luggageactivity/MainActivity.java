package com.example.luggageactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
//import android.R;

//import android.R.layout;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.luggageactivity.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
    Button signin, register;
    RelativeLayout rootLayout;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");


        signin = (Button) findViewById(R.id.b1);
        register = (Button) findViewById(R.id.b2);
        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog();

            }


        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });
        }

        private void showLoginDialog () {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Sign in");
            dialog.setMessage("Please use Email to Sign in");
            LayoutInflater inflater = LayoutInflater.from(this);
            View login_layout = inflater.inflate(R.layout.layout_login, null);
            final MaterialEditText edtEmail = login_layout.findViewById(R.id.m1);
            final MaterialEditText edtPassword = login_layout.findViewById(R.id.m2);

            dialog.setView(login_layout);
            dialog.setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //set disable button sign if it is processing
                    signin.setEnabled(false);

                    if (TextUtils.isEmpty(edtEmail.getText().toString())) {
                        Snackbar.make(rootLayout, "PLEASE ENTER EMAIL ADDRESS", Snackbar.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                        Snackbar.make(rootLayout, "Please enter password", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if (edtPassword.getText().toString().length() < 6) {
                        Snackbar.make(rootLayout, "Password too short!!!", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    final AlertDialog waitingDialog=new SpotsDialog(MainActivity.this);
                    waitingDialog.show();


                    //LOgin
                    auth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString()).
                            addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    waitingDialog.dismiss();
                                    startActivity(new Intent(MainActivity.this, Welcome.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            waitingDialog.dismiss();
                            Snackbar.make(rootLayout, "Failed" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            //Active button
                            signin.setEnabled(true);
                        }
                    });
                }
            });
            dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }



        private void showRegisterDialog () {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("REGISTER");
            dialog.setMessage("Please use Email to register");
            LayoutInflater inflater = LayoutInflater.from(this);
            View register_layout = inflater.inflate(R.layout.layout_regiter, null);
            final MaterialEditText edtEmail = register_layout.findViewById(R.id.m1);
            final MaterialEditText edtPassword = register_layout.findViewById(R.id.m2);
            final MaterialEditText edtName = register_layout.findViewById(R.id.m3);
            final MaterialEditText edtPhone = register_layout.findViewById(R.id.m4);

            dialog.setView(register_layout);
            dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (TextUtils.isEmpty(edtEmail.getText().toString())) {
                        Snackbar.make(rootLayout, "PLEASE ENTER EMAIL ADDRESS", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(edtPhone.getText().toString())) {
                        Snackbar.make(rootLayout, "PLEASE ENTER PHONE NUMBER", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                        Snackbar.make(rootLayout, "Please enter password", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if (edtPassword.getText().toString().length() < 6) {
                        Snackbar.make(rootLayout, "Pasword too short!!!", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    //Register new user
                    auth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    //save user to db
                                    User user = new User();
                                    user.setEmail(edtEmail.getText().toString());
                                    user.setName(edtName.getText().toString());
                                    user.setPhone(edtPhone.getText().toString());
                                    user.setPassword(edtPassword.getText().toString());
                                    //use email to key
                                    users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Snackbar.make(rootLayout, "Register successful", Snackbar.LENGTH_SHORT).show();

                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Snackbar.make(rootLayout, "Failed" + e.getMessage(), Snackbar.LENGTH_SHORT).show();

                                                }
                                            });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Snackbar.make(rootLayout, "Failed" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                }
                            });
                }
            });
            dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }




