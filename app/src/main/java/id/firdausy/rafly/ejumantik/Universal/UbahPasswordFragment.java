package id.firdausy.rafly.ejumantik.Universal;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import id.firdausy.rafly.ejumantik.Helper.Bantuan;
import id.firdausy.rafly.ejumantik.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UbahPasswordFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    private EditText et_email;
    private EditText et_passwordSaatIni;
    private EditText et_PasswordBaru;
    private EditText et_ulangiPasswordBaru;
    private Button btn_simpan;


    public UbahPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ubah_password, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        et_email = v.findViewById(R.id.et_email);
        et_passwordSaatIni = v.findViewById(R.id.et_passwordSaatIni);
        et_PasswordBaru = v.findViewById(R.id.et_PasswordBaru);
        et_ulangiPasswordBaru = v.findViewById(R.id.et_ulangiPasswordBaru);
        btn_simpan = v.findViewById(R.id.btn_simpan);

        et_email.setText(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail());
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesUbahPassword();
            }
        });
        return v;
    }

    private void prosesUbahPassword() {
        if (TextUtils.isEmpty(et_passwordSaatIni.getText().toString()) ||
                TextUtils.isEmpty(et_PasswordBaru.getText().toString()) ||
                TextUtils.isEmpty(et_ulangiPasswordBaru.getText().toString())) {
            new Bantuan(getActivity()).swal_error("Masih ada data yang kosong!");
        } else if (!et_PasswordBaru.getText().toString().equals(et_ulangiPasswordBaru.getText().toString())) {
            new Bantuan(getActivity()).swal_error("Konfirmasi Password salah!");
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();

            AuthCredential credential = EmailAuthProvider.getCredential(Objects.requireNonNull(
                    Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()),
                    et_passwordSaatIni.getText().toString());

            firebaseAuth.getCurrentUser()
                    .reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                firebaseAuth.getCurrentUser()
                                        .updatePassword(et_PasswordBaru.getText().toString())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                new Bantuan(getActivity()).toastLong("Password berhasil diubah, silahkan login kembali");
                                                firebaseAuth.signOut();
                                                Intent intent = new Intent(getActivity(), SplashScreenActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                new Bantuan(getActivity()).swal_error(e.getMessage());
                                            }
                                        });
                            } else {
                                new Bantuan(getActivity()).swal_error("Password lama yang anda masukan salah");
                            }
                        }
                    });
        }
    }

}
