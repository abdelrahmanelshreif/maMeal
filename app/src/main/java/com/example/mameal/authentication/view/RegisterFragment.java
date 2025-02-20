package com.example.mameal.authentication.view;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mameal.R;
import com.example.mameal.utils.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterFragment extends Fragment {

    EditText emailEditText, usernameEditText, passwordEditText, confirmPasswordEditText;

    CheckBox acceptTermsCheckBox;
    Button signupBtn;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEditText = view.findViewById(R.id.email_edit_text_register);
        confirmPasswordEditText = view.findViewById(R.id.confirm_password_edit_text_register);
        passwordEditText = view.findViewById(R.id.password_edit_text_register);
        acceptTermsCheckBox = view.findViewById(R.id.accept_terms_check_box);
        signupBtn = view.findViewById(R.id.signup_btn);

        signupBtn.setOnClickListener(v -> createAccount());
    }

    private void createAccount() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (validateData(email, password, confirmPassword)) {
            createAccountAtFirebase(email, password);
        }

    }

    private void createAccountAtFirebase(String email, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(createAccountTask -> {
            if (createAccountTask.isSuccessful()) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                Utility.showToast(getContext(), "Account has been successfully created");
            }
        });

    }

    private boolean validateData(String email, String password, String confirmPassword) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email");
            return false;
        }
        if (password.length() < 8) {
            passwordEditText.setError("Password length should be more than 8 charachters");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Password is not matched");
            return false;
        }
        if(!acceptTermsCheckBox.isChecked()){
            acceptTermsCheckBox.setError("You should accept our terms and conditions");
            return false;
        }
        return true;
    }

}