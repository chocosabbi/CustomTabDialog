package com.example.customtabdialog.ui.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customtabdialog.R;
import com.example.customtabdialog.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.customtabdialog.Constants.COLLECTION_USERS;

public class HomeActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private TextView tv_welcome;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv_welcome = findViewById(R.id.tv_welcome);
        getUserData();
    }

    private void getUserData() {
        if (mAuth.getCurrentUser() != null) {
            firebaseFirestore.collection(COLLECTION_USERS)
                    .document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                UserModel user = task.getResult().toObject(UserModel.class);
                                if (user != null && user.getName() != null)
                                    tv_welcome.setText(getString(R.string.template_welcome, user.getName()));
                            }
                        }
                    });
        }
    }
}
