package com.example.mameal.authentication.view;

import android.os.Bundle;
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
import com.example.mameal.authentication.presenter.LoginPresenter;
import com.example.mameal.network.FirebaseServicesImpl;
import com.example.mameal.utils.Utility;

public class LoginFragment extends Fragment implements LoginView {

    TextView register_Btn, forget_password_btn;

    ImageView google_signin_btn, facebook_signin_btn;

    EditText emailEditText, passwordEditText;
    Button signin_btn;

    LoginPresenter loginPresenter;


    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUiComponent(view);
        loginPresenter = new LoginPresenter(this, new FirebaseServicesImpl());

        signin_btn.setOnClickListener(v -> {
            loginUser();
        });

    }

    private void setupUiComponent(@NonNull View view) {
        register_Btn = view.findViewById(R.id.register_text_view_btn);
        forget_password_btn = view.findViewById(R.id.forget_password_text_view_btn);
        signin_btn = view.findViewById(R.id.signin_btn);
        google_signin_btn = view.findViewById(R.id.google_signin_img_view_btn);
        facebook_signin_btn = view.findViewById(R.id.facebook_signin_img_view_btn);
        emailEditText = view.findViewById(R.id.email_edit_text);
        passwordEditText = view.findViewById(R.id.password_edit_text);
    }

    private void loginUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        loginPresenter.loginUser(email, password);
    }


    @Override
    public void invalidEmailLoginData() {
        emailEditText.setError("Email is Invalid");
    }

    @Override
    public void invalidPasswordLoginData() {
        passwordEditText.setError("Password length invalid");

    }

    @Override
    public void showLoginSuccess() {
        Utility.showToast(getContext(), "Successful Login");
    }

    @Override
    public void showLoginError(String errorMessage) {
        Utility.showToast(getContext(), errorMessage);
    }
}