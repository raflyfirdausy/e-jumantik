package id.firdausy.rafly.ejumantik.FragmentAdmin;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class TambahAdminFragment extends Fragment {

    private EditText et_namaLengkapAdmin;
    private EditText et_alamatLengkapAdmin;
    private EditText et_noHpAdmin;
    private EditText et_emailAdmin;
    private EditText et_passwordAdmin;
    private EditText et_UlangPasswordAdmin;
    private Button btn_daftar;

    private FirebaseAuth firebaseAuth, firebaseAuth2;
    private DatabaseReference databaseReference;


    public TambahAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tambah_admin_kader, container, false);
        et_namaLengkapAdmin = v.findViewById(R.id.et_namaLengkapAdmin);
        et_alamatLengkapAdmin = v.findViewById(R.id.et_alamatLengkapAdmin);
        et_noHpAdmin = v.findViewById(R.id.et_noHpAdmin);
        et_emailAdmin = v.findViewById(R.id.et_emailAdmin);
        et_passwordAdmin = v.findViewById(R.id.et_passwordAdmin);
        et_UlangPasswordAdmin = v.findViewById(R.id.et_UlangPasswordAdmin);
        btn_daftar = v.findViewById(R.id.btn_daftar);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesDaftar();
            }
        });
        return v;
    }

    private void resetForm() {
        et_namaLengkapAdmin.setText("");
        et_alamatLengkapAdmin.setText("");
        et_noHpAdmin.setText("");
        et_emailAdmin.setText("");
        et_passwordAdmin.setText("");
        et_UlangPasswordAdmin.setText("");
    }

    private void prosesDaftar() {
        if ((TextUtils.isEmpty(et_namaLengkapAdmin.getText().toString())) ||
                (TextUtils.isEmpty(et_alamatLengkapAdmin.getText().toString())) ||
                (TextUtils.isEmpty(et_noHpAdmin.getText().toString())) ||
                (TextUtils.isEmpty(et_emailAdmin.getText().toString())) ||
                (TextUtils.isEmpty(et_passwordAdmin.getText().toString())) ||
                (TextUtils.isEmpty(et_UlangPasswordAdmin.getText().toString()))
        ) {
            new Bantuan(getActivity()).swal_error("Masih ada data yang kosong!");
        } else if (!et_passwordAdmin.getText().toString().equals(et_UlangPasswordAdmin.getText().toString())) {
            new Bantuan(getActivity()).swal_error("konfirmasi Password Saslah!");
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();

            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setDatabaseUrl("https://new-jumantik.firebaseio.com/")
                    .setApiKey("AIzaSyAYFMt47sKGarh8W6Qj4ugdJZGCQKn_HnU")
                    .setApplicationId("id.firdausy.rafly.ejumantik")
                    .build();

            try {
                FirebaseApp firebaseApp = FirebaseApp.initializeApp(getActivity().getApplicationContext(),
                        firebaseOptions,
                        getString(R.string.app_name));
                firebaseAuth2 = FirebaseAuth.getInstance(firebaseApp);
            } catch (IllegalStateException e) {
                firebaseAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance(getString(R.string.app_name)));
            }

            firebaseAuth2.createUserWithEmailAndPassword(et_emailAdmin.getText().toString(),
                    et_passwordAdmin.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            String user_id = authResult.getUser().getUid();
                            Map data = new HashMap();
                            data.put("alamat", et_alamatLengkapAdmin.getText().toString());
                            data.put("email", et_emailAdmin.getText().toString());
                            data.put("namaLengkap", et_namaLengkapAdmin.getText().toString());
                            data.put("nomerHp", et_noHpAdmin.getText().toString());

                            databaseReference.child("admin")
                                    .child(user_id)
                                    .setValue(data)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            resetForm();
                                            firebaseAuth2.signOut();
                                            new Bantuan(getActivity()).swal_sukses("Berhasil Menambahkan Admin");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            new Bantuan(getActivity()).swal_error(e.getMessage());
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            new Bantuan(getActivity()).swal_error(e.getMessage());
                        }
                    });

        }
    }

}
