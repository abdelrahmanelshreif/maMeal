package com.example.mameal.authentication.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mameal.R;
import com.example.mameal.authentication.presenter.LoginPresenter;
import com.example.mameal.network.FirebaseServicesImpl;
import com.example.mameal.shared.Utility;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment implements LoginView {

    TextView register_Btn, forget_password_btn;
    ImageView google_signin_btn;
    EditText emailEditText, passwordEditText;
    Button signin_btn, guest_mode_btn;
    LoginPresenter loginPresenter;
    private ActivityResultLauncher<Intent> googleSignInLauncher;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupGoogleSignIn();
    }

    private void setupGoogleSignIn() {
        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Task<GoogleSignInAccount> task =
                                GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        loginPresenter.handleGoogleSignInResult(task);
                    } else {
                        showLoginError("Google Sign-In failed");
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUiComponent(view);

        loginPresenter = new LoginPresenter(this, new FirebaseServicesImpl(),requireContext());

        guest_mode_btn.setOnClickListener(v -> setGuest());
        register_Btn.setOnClickListener(v -> loginPresenter.register());
        signin_btn.setOnClickListener(v -> loginUser());
        google_signin_btn.setOnClickListener(v -> loginPresenter.initiateGoogleSignIn());

    }

    private void setGuest() {
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment);
    }


    private void setupUiComponent(@NonNull View view) {
        register_Btn = view.findViewById(R.id.register_text_view_btn);
        forget_password_btn = view.findViewById(R.id.forget_password_text_view_btn);
        signin_btn = view.findViewById(R.id.signin_btn);
        google_signin_btn = view.findViewById(R.id.google_signin_img_view_btn);
        emailEditText = view.findViewById(R.id.email_edit_text);
        passwordEditText = view.findViewById(R.id.password_edit_text);
        guest_mode_btn = view.findViewById(R.id.guest_mode_btn);
    }

    private void loginUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        loginPresenter.loginUser(email, password);
    }


    @Override
    public void launchGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        googleSignInLauncher.launch(signInIntent);
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
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment);
    }

    @Override
    public void showLoginError(String errorMessage) {
        Utility.showToast(getContext(), errorMessage);
    }

    @Override
    public void onGoogleSignInSuccess(FirebaseUser user) {
        Utility.showToast(requireContext(), user.getEmail() + "\nSuccessfully Logged in ");
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment);
    }

    @Override
    public void onGoogleSignInFailure(String errorMessage) {
        Utility.showToast(requireContext(), errorMessage);
    }

    @Override
    public void NavigateRegisterScreen() {
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registerFragment);
    }
}