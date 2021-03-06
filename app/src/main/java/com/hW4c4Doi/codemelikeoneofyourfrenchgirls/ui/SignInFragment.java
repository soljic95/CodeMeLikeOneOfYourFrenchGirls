package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.model.User;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.room.EventDatabase;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.FirebaseViewModel;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.viewModel.MyViewModelFactory;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    @BindView(R.id.btnSignUp)
    TextView btnSignUp;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    final static int RC_SIGN_IN = 1;

    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    private FirebaseViewModel viewModel;
    private AlertDialog alertDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);


    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getContext())
                .setView(R.layout.progress_dialog)
                .create();

        viewModel = ViewModelProviders.of(getActivity(), new MyViewModelFactory(getActivity().getApplication(), EventDatabase.getInstance(getContext())))
                .get(FirebaseViewModel.class);


        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d("marko", "onAuthStateChanged: " + firebaseAuth.getUid());
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    checkIfSignedIn(user);
                }
            }
        };


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Toast.makeText(getContext(), "SIGN IN FAILED", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void checkIfSignedIn(FirebaseUser currentUser) {
        if (currentUser != null) {
            alertDialog.show();
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

    }

    @OnClick(R.id.btnSignIn)
    void login() {

        if (!validateForm()) {
            return;
        }

        alertDialog.show();

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            alertDialog.show();
                            viewModel.isUserInRoomDatabase(task.getResult().getUser().getUid()).subscribe(new SingleObserver<User>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(User user) {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    viewModel.getUserFromFirebase(task.getResult().getUser().getUid()).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for (DocumentSnapshot dc : queryDocumentSnapshots) {
                                                User currentUser = new User();
                                                currentUser = dc.toObject(User.class);

                                                viewModel.insertUserInDatabase(currentUser).subscribe(new CompletableObserver() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {

                                                    }

                                                    @Override
                                                    public void onComplete() {
                                                        Log.d("marko", "onSuccess: User added in Room database!");
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        Log.d("marko", "onError: user couldn be added");
                                                    }
                                                });
                                            }
                                        }
                                    });

                                }
                            });

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("marko", "onFailure: " + e.getMessage());
            }
        });
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Obavezno popuniti");
            result = false;
        } else if (!isEmailValid(etEmail.getText().toString().trim())) {
            etEmail.setError("Email adresa nije valjana");
            result = false;
        } else {
            etEmail.setError(null);
        }
        if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            etPassword.setError("Obavezno popuniti");
            result = false;

        } else if (isPasswordValid(etPassword.getText().toString().trim())) {
            etPassword.setError("Lozinka nije dovoljno duga");
            result = false;
        } else {
            etPassword.setError(null);
        }

        return result;
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAuth != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @OnClick(R.id.btnSignUp)
    void setBtnSignUp() {
        Navigation.findNavController(getView()).navigate(R.id.signUpFragment);
    }


    private boolean isPasswordValid(String password) {
        return password.length() < 7;
    }


}
