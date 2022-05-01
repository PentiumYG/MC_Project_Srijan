package com.example.mc_project_srijan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mc_project_srijan.databinding.ActivitySigninBinding;
import com.example.mc_project_srijan.databinding.ActivitySignupBinding;
import com.example.mc_project_srijan.model.user;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signin extends AppCompatActivity {
    ActivitySigninBinding binding;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(signin.this);
        progressDialog.setTitle("Log In");
        progressDialog.setMessage("Your details is being Verified");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.password.getText().toString().isEmpty() || binding.email.getText().toString().isEmpty()){
                    Toast.makeText(signin.this,"Please Enter Your Complete Details to Proceed",Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(binding.email.getText().toString(),binding.password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                // Toast.makeText(signup.this, "Sign Up Successfull", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(signin.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(signin.this,task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        if(mAuth.getCurrentUser()!=null){
            Intent intent=new Intent(signin.this,MainActivity.class);
            startActivity(intent);
        }
        binding.account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(signin.this,signup.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().hide();
    }
}