package com.example.customtabdialog.ui.fragments.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.customtabdialog.R;

import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {


    private static final String TAG = SignInFragment.class.getSimpleName();
    private EditText et_email, et_password;
    private Button mConfirm, mCancel;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        et_email = view.findViewById(R.id.email);
        et_password = view.findViewById(R.id.password);
        mConfirm = view.findViewById(R.id.dialog_confirm);
        mCancel = view.findViewById(R.id.dialog_cancel);


        mConfirm.setOnClickListener(v -> {
            String email = et_email.getText().toString();
            if (TextUtils.isEmpty(email)) {
                et_email.requestFocus();
                et_email.setError(getString(R.string.error_email_required));
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                et_email.requestFocus();
                et_email.setError(getString(R.string.error_email_pattern));
                return;
            }

            String password = et_password.getText().toString();
            if (TextUtils.isEmpty(password)) {
                et_password.requestFocus();
                et_password.setError(getString(R.string.error_password_required));
                return;
            }
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> Log.d(TAG, "onComplete: " + task.isSuccessful()))
                    .addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e.getLocalizedMessage()));

        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
}