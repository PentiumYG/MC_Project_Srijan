package com.example.mc_project_srijan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mc_project_srijan.databinding.ActivitySignupBinding;
import com.example.mc_project_srijan.model.user;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    ActivitySignupBinding binding;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(signup.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Your Account will be created soon");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.name.getText().toString().isEmpty() || binding.password.getText().toString().isEmpty() || binding.email.getText().toString().isEmpty()){
                    Toast.makeText(signup.this,"Please Enter Your Complete Details to Proceed",Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(binding.email.getText().toString(),binding.password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                               // Toast.makeText(signup.this, "Sign Up Successfull", Toast.LENGTH_SHORT).show();
                                user us=new user(binding.name.getText().toString(),binding.email.getText().toString(),binding.password.getText().toString());
                                String userid=task.getResult().getUser().getUid();
                                database.getReference().child("OurUsers").child(userid).setValue(us);
                                Intent intent=new Intent(signup.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(signup.this,task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        binding.account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(signup.this,signin.class);
                startActivity(intent);
            }
        });
        binding.google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

        int RC_SIGN_IN=65;
        private void signIn() {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("TAG", "Google sign in failed", e);
                }
            }
        }




    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser us = mAuth.getCurrentUser();
                            user u=new user();
                            u.setUserId(us.getUid());
                            u.setName(us.getDisplayName());
                            u.setProfile_pic(us.getPhotoUrl().toString());
                            database.getReference().child("OurUsers").child(us.getUid()).setValue(u);
                            Intent intent=new Intent(signup.this,MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(signup.this, "Sign In with Google", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }
}