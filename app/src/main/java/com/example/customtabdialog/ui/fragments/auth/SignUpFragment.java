package com.example.customtabdialog.ui.fragments.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.customtabdialog.R;
import com.example.customtabdialog.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.customtabdialog.Constants.COLLECTION_USERS;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    private static final String TAG = SignUpFragment.class.getSimpleName();
    private EditText et_name, et_password, et_email;
    private Button btnSignUp;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        et_name = view.findViewById(R.id.et_name);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        btnSignUp = view.findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    et_name.requestFocus();
                    et_name.setError(getString(R.string.error_name_required));
                    return;
                }

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
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    UserModel user = new UserModel(mAuth.getCurrentUser().getUid(), name, email);
                                    firebaseFirestore.collection(COLLECTION_USERS)
                                            .document(mAuth.getCurrentUser().getUid())
                                            .set(user);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
                            }
                        });
            }
        });
        return view;
    }
}