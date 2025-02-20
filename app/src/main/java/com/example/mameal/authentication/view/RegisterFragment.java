package com.example.mameal.authentication.view;

import android.os.Bundle;
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
import com.example.mameal.authentication.presenter.RegisterPresenter;
import com.example.mameal.network.FirebaseServicesImpl;
import com.example.mameal.utils.Utility;


public class RegisterFragment extends Fragment implements RegisterView {

    EditText emailEditText, usernameEditText, passwordEditText, confirmPasswordEditText;

    CheckBox acceptTermsCheckBox;
    Button signupBtn;

    RegisterPresenter registerPresenter;

    public RegisterFragment() {

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
        setupUiComponent(view);
        registerPresenter = new RegisterPresenter(new FirebaseServicesImpl(),this);
        signupBtn.setOnClickListener(v -> createAccount());
    }

    private void setupUiComponent(@NonNull View view) {
        emailEditText = view.findViewById(R.id.email_edit_text_register);
        confirmPasswordEditText = view.findViewById(R.id.confirm_password_edit_text_register);
        passwordEditText = view.findViewById(R.id.password_edit_text_register);
        acceptTermsCheckBox = view.findViewById(R.id.accept_terms_check_box);
        signupBtn = view.findViewById(R.id.signup_btn);
    }

    private void createAccount() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        boolean termsAcceptance = acceptTermsCheckBox.isChecked();
        registerPresenter.register(email, password, confirmPassword, termsAcceptance);

    }


    @Override
    public void invalidEmailLoginData() {
        emailEditText.setError("Enter a valid email");
    }

    @Override
    public void invalidPasswordLoginData() {
        passwordEditText.setError("Password length should be more than 8 charachters");
    }

    @Override
    public void invalidPasswordConfirmation() {
        confirmPasswordEditText.setError("Password is not matched");
    }

    @Override
    public void invalidCheckingTerms() {
        acceptTermsCheckBox.setError("You should accept our terms and conditions");
    }

    @Override
    public void showRegisterSuccess() {
        Utility.showToast(getContext(), "Account has been successfully created");
    }

    @Override
    public void showRegisterError(String errorMessage) {
        Utility.showToast(getContext(), "An error has been occurred please try again later");
    }
}