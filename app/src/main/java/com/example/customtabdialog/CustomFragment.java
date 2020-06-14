package com.example.customtabdialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class CustomFragment extends Fragment {

    private String mText = "";
    private AutoCompleteTextView mEmail, mPassword;
    private Button mConfirm, mCancel;
    private static final String TAG = "CustomFragment";
    FirebaseAuth mAuth;

    public static CustomFragment createInstance(String txt) {
        CustomFragment fragment = new CustomFragment();
        fragment.mText = txt;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sample,container,false);
        ((TextView) v.findViewById(R.id.tab_text_view)).setText(mText);
        //((AutoCompleteTextView) v.findViewById(R.id.username)).setText(mText);
        //((AutoCompleteTextView) v.findViewById(R.id.password)).setText(mText);
        mEmail = v.findViewById(R.id.email);
        mPassword = v.findViewById(R.id.password);
        mConfirm = v.findViewById(R.id.dialog_confirm);
        mCancel = v.findViewById(R.id.dialog_cancel);

        mAuth = FirebaseAuth.getInstance();

        Log.d(TAG, "onCreateView: Register Loaded and connected to Firebase Auth");

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getActivity(), "Password too short. \nEnter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);
                //create user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                //progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    //Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onComplete: error registering" + task.getException());
                                } else {
                                    Log.d(TAG, "Email and Password Register Successful" + task.isSuccessful());
                                    startActivity(new Intent(getActivity(), HomeActivity.class));
                                    //getDialog().dismiss();
                                    getActivity().onBackPressed();

                                }
                            }
                        });


            }
        });


        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: closing the dialog");
                //getDialog().dismiss();
                getActivity().onBackPressed();
            }
        });

        return v;
    }
}

