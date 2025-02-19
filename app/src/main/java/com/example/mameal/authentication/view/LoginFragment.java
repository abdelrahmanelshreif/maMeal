package com.example.mameal.authentication.view;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mameal.R;
import com.example.mameal.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    TextView register_Btn;
    TextView forget_password_btn;
    Button signin_btn;
    ImageView google_signin_btn;
    ImageView facebook_signin_btn;

    EditText emailEditText;
    EditText passwordEditText;


    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        register_Btn = view.findViewById(R.id.register_text_view_btn);
        forget_password_btn = view.findViewById(R.id.forget_password_text_view_btn);
        signin_btn = view.findViewById(R.id.signin_btn);
        google_signin_btn = view.findViewById(R.id.google_signin_img_view_btn);
        facebook_signin_btn = view.findViewById(R.id.facebook_signin_img_view_btn);
        emailEditText = view.findViewById(R.id.email_edit_text);
        passwordEditText = view.findViewById(R.id.password_edit_text);

        signin_btn.setOnClickListener(v -> {
            loginUser();
        });

    }

    private void loginUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean isValidated = validateData(email,password);

        if(!isValidated){
            return;
        }
        loginInFirebase(email,password);
    }

    private boolean validateData(String email, String password) {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Email is Invalid");
            return false;
        }else{
            if(password.length()<6){
                passwordEditText.setError("Password length invalid");
                return false;
            }
        }
        return true;
    }

    private void loginInFirebase(String email, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Utility.showToast(getContext(), "Successful Login");
                }else{
                    Utility.showToast(getContext(), "Login Failed");
                }
            }
        });

    }
}