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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.firdausy.rafly.ejumantik.Helper.Bantuan;
import id.firdausy.rafly.ejumantik.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PencegahanAdminFragment extends Fragment {

    private EditText et_caraPencegahanGiziBuruk;
    private Button btn_simpan;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    public PencegahanAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pencegahan_admin, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        et_caraPencegahanGiziBuruk = v.findViewById(R.id.et_caraPencegahanGiziBuruk);
        btn_simpan = v.findViewById(R.id.btn_simpan);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesUpdate();
            }
        });

        getData();
        return v;
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        databaseReference.child("config")
                .child("pencegahan")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        if (dataSnapshot.exists()) {
                            et_caraPencegahanGiziBuruk.setText(dataSnapshot.getValue(String.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                        new Bantuan(getActivity()).swal_error(databaseError.getMessage());
                    }
                });
    }

    private void prosesUpdate() {
        if (TextUtils.isEmpty(et_caraPencegahanGiziBuruk.getText().toString())) {
            new Bantuan(getActivity()).swal_error("Pencegahan tidak boleh kosong!");
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();

            databaseReference.child("config")
                    .child("pencegahan")
                    .setValue(et_caraPencegahanGiziBuruk.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            new Bantuan(getActivity()).swal_sukses("berhasil memperbaharui cara pencegahan");
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
