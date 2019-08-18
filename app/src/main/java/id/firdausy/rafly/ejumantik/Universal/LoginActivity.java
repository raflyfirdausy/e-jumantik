package id.firdausy.rafly.ejumantik.Universal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.firdausy.rafly.ejumantik.Activity.AdminMainActivity;
import id.firdausy.rafly.ejumantik.Activity.UserMainActivity;
import id.firdausy.rafly.ejumantik.Helper.Bantuan;
import id.firdausy.rafly.ejumantik.R;

public class LoginActivity extends AppCompatActivity {
    private Context context = LoginActivity.this;
    private TextInputEditText myet_email;
    private TextInputEditText myet_password;
    private Button btn_login;
    private TextView tv_DaftarDisini;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        myet_email = findViewById(R.id.myet_email);
        myet_password = findViewById(R.id.myet_password);
        btn_login = findViewById(R.id.btn_login);
        tv_DaftarDisini = findViewById(R.id.tv_DaftarDisini);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesLogin();
            }
        });

        tv_DaftarDisini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, RegistrasiActivity.class));
            }
        });
    }

    private void prosesLogin() {
        if (TextUtils.isEmpty(myet_email.getText().toString()) ||
                TextUtils.isEmpty(myet_password.getText().toString())) {
            new Bantuan(context).swal_error("Masih ada data yang kosong!");
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(myet_email.getText().toString(),
                    myet_password.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            databaseReference.child("user")
                                    .child(authResult.getUser().getUid())
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            progressDialog.dismiss();
                                            if (dataSnapshot.exists()) {
                                                startActivity(new Intent(context, UserMainActivity.class));
                                                finish();
                                            } else {
                                                startActivity(new Intent(context, AdminMainActivity.class));
                                                finish();
                                            }
                                            new Bantuan(context).toastLong("Berhasil Login");
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            progressDialog.dismiss();
                                            new Bantuan(context).swal_error(databaseError.getMessage());
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            new Bantuan(context).swal_error(e.getMessage());
                        }
                    });
        }
    }
}
