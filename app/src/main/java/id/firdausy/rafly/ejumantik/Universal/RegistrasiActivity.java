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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import id.firdausy.rafly.ejumantik.Helper.Bantuan;
import id.firdausy.rafly.ejumantik.R;

public class RegistrasiActivity extends AppCompatActivity {

    private Context context = RegistrasiActivity.this;
    private TextInputLayout etNama;
    private TextInputLayout etEmail;
    private TextInputLayout etPassword;
    private TextInputLayout etKonfirmasiPasswrod;
    private TextInputLayout etAlamat;
    private TextInputLayout etNoHp;
    private Button btn_Register;
    private TextView tvLoginDisini;
    private FirebaseAuth firebaseAuth, firebaseAuth2;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etKonfirmasiPasswrod = findViewById(R.id.etKonfirmasiPasswrod);
        etAlamat = findViewById(R.id.etAlamat);
        etNoHp = findViewById(R.id.etNoHp);
        btn_Register = findViewById(R.id.btn_Register);
        tvLoginDisini = findViewById(R.id.tvLoginDisini);

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesDaftar();
            }
        });

        tvLoginDisini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        });
    }

    private void prosesDaftar() {
        if (TextUtils.isEmpty(etNama.getEditText().getText().toString()) ||
                TextUtils.isEmpty(etEmail.getEditText().getText().toString()) ||
                TextUtils.isEmpty(etAlamat.getEditText().getText().toString()) ||
                TextUtils.isEmpty(etNoHp.getEditText().getText().toString()) ||
                TextUtils.isEmpty(etPassword.getEditText().getText().toString()) ||
                TextUtils.isEmpty(etKonfirmasiPasswrod.getEditText().getText().toString())) {
            new Bantuan(context).swal_error("Masih ada data yang kosong!");
        } else if (!etPassword.getEditText().getText().toString()
                .equals(etKonfirmasiPasswrod.getEditText().getText().toString())) {
            new Bantuan(context).swal_error("Konfirmasi Password Salah!");
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();

            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setDatabaseUrl("https://new-jumantik.firebaseio.com/")
                    .setApiKey("AIzaSyAYFMt47sKGarh8W6Qj4ugdJZGCQKn_HnU")
                    .setApplicationId("id.firdausy.rafly.ejumantik")
                    .build();

            try {
                FirebaseApp firebaseApp = FirebaseApp.initializeApp(getApplicationContext(),
                        firebaseOptions,
                        getString(R.string.app_name));
                firebaseAuth2 = FirebaseAuth.getInstance(firebaseApp);
            } catch (IllegalStateException e) {
                firebaseAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance(getString(R.string.app_name)));
            }

            firebaseAuth2.createUserWithEmailAndPassword(etEmail.getEditText().getText().toString(),
                    etPassword.getEditText().getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            String user_id = authResult.getUser().getUid();
                            Map data = new HashMap();
                            data.put("email", etEmail.getEditText().getText().toString());
                            data.put("namaLengkap", etNama.getEditText().getText().toString());
                            data.put("nomerHp", etNoHp.getEditText().getText().toString());
                            data.put("alamat", etAlamat.getEditText().getText().toString());

                            databaseReference.child("user")
                                    .child(user_id)
                                    .setValue(data)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            firebaseAuth2.signOut();
                                            new Bantuan(context).toastLong("Pendaftaran Berhasil");
                                            startActivity(new Intent(context, LoginActivity.class));
                                            finish();
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
