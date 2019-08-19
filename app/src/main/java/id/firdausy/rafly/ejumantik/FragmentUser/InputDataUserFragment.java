package id.firdausy.rafly.ejumantik.FragmentUser;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import id.firdausy.rafly.ejumantik.Helper.Bantuan;
import id.firdausy.rafly.ejumantik.Helper.Waktu;
import id.firdausy.rafly.ejumantik.Model.InputModel;
import id.firdausy.rafly.ejumantik.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputDataUserFragment extends Fragment implements TextWatcher {


    int JmlBak,
            JmlTempayan,
            JmlPecahan,
            JmlBarangBekas,
            JmlKulkasDispenser,
            JmlBakMandi,
            JmlVasBunga,
            JmlPotBunga,
            JmlKontainerLain;

    int JentikBak,
            JentikTempayan,
            JentikPecahan,
            JentikBarangBekas,
            JentikKulkasDispenser,
            JentikBakMandi,
            JentikVasBunga,
            JentikPotBunga,
            JentikKontainerLain;

    private EditText etHariTanggal;
    private EditText etJmlBak;
    private EditText etJentikBak;
    private EditText etJmlTempayan;
    private EditText etJentikTempayan;
    private EditText etJmlPecahan;
    private EditText etJentikPecahan;
    private EditText etJmlBarangBekas;
    private EditText etJentikBarangBekas;
    private EditText etKulkasDispenser;
    private EditText etJentikKulkasDispenser;
    private EditText etJmlBakMandi;
    private EditText etJentikBakMandi;
    private EditText etJmlVasBunga;
    private EditText etJentikVasBunga;
    private EditText etJmlPotBunga;
    private EditText etJentikPotBunga;
    private EditText etJmlKontainerLain;
    private EditText etJentikKontainerLain;
    private TextView tvKontainerDiPeriksa;
    private TextView tvTotalPositifJentik;
    private Button btnSimpan;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    public InputDataUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_input_data_user, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        etHariTanggal = v.findViewById(R.id.etHariTanggal);
        etJmlBak = v.findViewById(R.id.etJmlBak);
        etJentikBak = v.findViewById(R.id.etJentikBak);
        etJmlTempayan = v.findViewById(R.id.etJmlTempayan);
        etJentikTempayan = v.findViewById(R.id.etJentikTempayan);
        etJmlPecahan = v.findViewById(R.id.etJmlPecahan);
        etJentikPecahan = v.findViewById(R.id.etJentikPecahan);
        etJmlBarangBekas = v.findViewById(R.id.etJmlBarangBekas);
        etJentikBarangBekas = v.findViewById(R.id.etJentikBarangBekas);
        etKulkasDispenser = v.findViewById(R.id.etKulkasDispenser);
        etJentikKulkasDispenser = v.findViewById(R.id.etJentikKulkasDispenser);
        etJmlBakMandi = v.findViewById(R.id.etJmlBakMandi);
        etJentikBakMandi = v.findViewById(R.id.etJentikBakMandi);
        etJmlVasBunga = v.findViewById(R.id.etJmlVasBunga);
        etJentikVasBunga = v.findViewById(R.id.etJentikVasBunga);
        etJmlPotBunga = v.findViewById(R.id.etJmlPotBunga);
        etJentikPotBunga = v.findViewById(R.id.etJentikPotBunga);
        etJmlKontainerLain = v.findViewById(R.id.etJmlKontainerLain);
        etJentikKontainerLain = v.findViewById(R.id.etJentikKontainerLain);
        tvKontainerDiPeriksa = v.findViewById(R.id.tvKontainerDiPeriksa);
        tvTotalPositifJentik = v.findViewById(R.id.tvTotalPositifJentik);
        btnSimpan = v.findViewById(R.id.btnSimpan);

        etHariTanggal.setFocusable(false);
        etHariTanggal.setText(new Waktu(getActivity()).getHariTanggalIndonesia(new Date().getTime()));

        etJmlBak.addTextChangedListener(this);
        etJentikBak.addTextChangedListener(this);
        etJmlTempayan.addTextChangedListener(this);
        etJentikTempayan.addTextChangedListener(this);
        etJmlPecahan.addTextChangedListener(this);
        etJentikPecahan.addTextChangedListener(this);
        etJmlBarangBekas.addTextChangedListener(this);
        etJentikBarangBekas.addTextChangedListener(this);
        etKulkasDispenser.addTextChangedListener(this);
        etJentikKulkasDispenser.addTextChangedListener(this);
        etJmlBakMandi.addTextChangedListener(this);
        etJentikBakMandi.addTextChangedListener(this);
        etJmlVasBunga.addTextChangedListener(this);
        etJentikVasBunga.addTextChangedListener(this);
        etJmlPotBunga.addTextChangedListener(this);
        etJentikPotBunga.addTextChangedListener(this);
        etJmlKontainerLain.addTextChangedListener(this);
        etJentikKontainerLain.addTextChangedListener(this);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Informasi");
                builder.setMessage("Apakah data yang anda masukan sudah benar ?");
                builder.setNegativeButton("Belum", null);
                builder.setPositiveButton("Sudah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        prosesSimpan();
                    }
                });
                builder.show();
            }
        });

        return v;
    }

    private void prosesSimpan() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        InputModel inputModel = new InputModel();
        inputModel.setJmlBak(TextUtils.isEmpty(etJmlBak.getText().toString().trim()) ? "0" : etJmlBak.getText().toString().trim());
        inputModel.setJmlTempayan(TextUtils.isEmpty(etJmlTempayan.getText().toString().trim()) ? "0" : etJmlTempayan.getText().toString().trim());
        inputModel.setJmlPecahan(TextUtils.isEmpty(etJmlPecahan.getText().toString().trim()) ? "0" : etJmlPecahan.getText().toString().trim());
        inputModel.setJmlBarangBekas(TextUtils.isEmpty(etJmlBarangBekas.getText().toString().trim()) ? "0" : etJmlBarangBekas.getText().toString().trim());
        inputModel.setJmlKulkasDispenser(TextUtils.isEmpty(etKulkasDispenser.getText().toString().trim()) ? "0" : etKulkasDispenser.getText().toString().trim());
        inputModel.setJmlBakMandi(TextUtils.isEmpty(etJmlBakMandi.getText().toString().trim()) ? "0" : etJmlBakMandi.getText().toString().trim());
        inputModel.setJmlVasBunga(TextUtils.isEmpty(etJmlVasBunga.getText().toString().trim()) ? "0" : etJmlVasBunga.getText().toString().trim());
        inputModel.setJmlPotBunga(TextUtils.isEmpty(etJmlPotBunga.getText().toString().trim()) ? "0" : etJmlPotBunga.getText().toString().trim());
        inputModel.setJmlKontainerLain(TextUtils.isEmpty(etJmlKontainerLain.getText().toString().trim()) ? "0" : etJmlKontainerLain.getText().toString().trim());

        inputModel.setJentikBak(TextUtils.isEmpty(etJentikBak.getText().toString().trim()) ? "0" : etJentikBak.getText().toString().trim());
        inputModel.setJentikTempayan(TextUtils.isEmpty(etJentikTempayan.getText().toString().trim()) ? "0" : etJentikTempayan.getText().toString().trim());
        inputModel.setJentikPecahan(TextUtils.isEmpty(etJentikPecahan.getText().toString().trim()) ? "0" : etJentikPecahan.getText().toString().trim());
        inputModel.setJentikBarangBekas(TextUtils.isEmpty(etJentikBarangBekas.getText().toString().trim()) ? "0" : etJentikBarangBekas.getText().toString().trim());
        inputModel.setJentikKulkasDispenser(TextUtils.isEmpty(etJentikKulkasDispenser.getText().toString().trim()) ? "0" : etJentikKulkasDispenser.getText().toString().trim());
        inputModel.setJentikBakMandi(TextUtils.isEmpty(etJentikBakMandi.getText().toString().trim()) ? "0" : etJentikBakMandi.getText().toString().trim());
        inputModel.setJentikVasBunga(TextUtils.isEmpty(etJentikVasBunga.getText().toString().trim()) ? "0" : etJentikVasBunga.getText().toString().trim());
        inputModel.setJentikPotBunga(TextUtils.isEmpty(etJentikPotBunga.getText().toString().trim()) ? "0" : etJentikPotBunga.getText().toString().trim());
        inputModel.setJentikKontainerLain(TextUtils.isEmpty(etJentikKontainerLain.getText().toString().trim()) ? "0" : etJentikKontainerLain.getText().toString().trim());

        inputModel.setWaktu(String.valueOf(new Date().getTime()));

        inputModel.setTotalKontainer(tvKontainerDiPeriksa.getText().toString());
        inputModel.setTotalPositifJentik(tvTotalPositifJentik.getText().toString());


        databaseReference.child("input")
                .child(firebaseAuth.getCurrentUser().getUid())
                .push()
                .setValue(inputModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        resetForm();
                        new Bantuan(getContext()).swal_sukses("Berhasil Menyimpan data");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        new Bantuan(getContext()).swal_error(e.getMessage());
                    }
                });
    }

    private void hitung() {
        JmlBak = TextUtils.isEmpty(etJmlBak.getText().toString().trim()) ? 0 : Integer.parseInt(etJmlBak.getText().toString().trim());
        JmlTempayan = TextUtils.isEmpty(etJmlTempayan.getText().toString().trim()) ? 0 : Integer.parseInt(etJmlTempayan.getText().toString().trim());
        JmlPecahan = TextUtils.isEmpty(etJmlPecahan.getText().toString().trim()) ? 0 : Integer.parseInt(etJmlPecahan.getText().toString().trim());
        JmlBarangBekas = TextUtils.isEmpty(etJmlBarangBekas.getText().toString().trim()) ? 0 : Integer.parseInt(etJmlBarangBekas.getText().toString().trim());
        JmlKulkasDispenser = TextUtils.isEmpty(etKulkasDispenser.getText().toString().trim()) ? 0 : Integer.parseInt(etKulkasDispenser.getText().toString().trim());
        JmlBakMandi = TextUtils.isEmpty(etJmlBakMandi.getText().toString().trim()) ? 0 : Integer.parseInt(etJmlBakMandi.getText().toString().trim());
        JmlVasBunga = TextUtils.isEmpty(etJmlVasBunga.getText().toString().trim()) ? 0 : Integer.parseInt(etJmlVasBunga.getText().toString().trim());
        JmlPotBunga = TextUtils.isEmpty(etJmlPotBunga.getText().toString().trim()) ? 0 : Integer.parseInt(etJmlPotBunga.getText().toString().trim());
        JmlKontainerLain = TextUtils.isEmpty(etJmlKontainerLain.getText().toString().trim()) ? 0 : Integer.parseInt(etJmlKontainerLain.getText().toString().trim());

        JentikBak = TextUtils.isEmpty(etJentikBak.getText().toString().trim()) ? 0 : Integer.parseInt(etJentikBak.getText().toString().trim());
        JentikTempayan = TextUtils.isEmpty(etJentikTempayan.getText().toString().trim()) ? 0 : Integer.parseInt(etJentikTempayan.getText().toString().trim());
        JentikPecahan = TextUtils.isEmpty(etJentikPecahan.getText().toString().trim()) ? 0 : Integer.parseInt(etJentikPecahan.getText().toString().trim());
        JentikBarangBekas = TextUtils.isEmpty(etJentikBarangBekas.getText().toString().trim()) ? 0 : Integer.parseInt(etJentikBarangBekas.getText().toString().trim());
        JentikKulkasDispenser = TextUtils.isEmpty(etJentikKulkasDispenser.getText().toString().trim()) ? 0 : Integer.parseInt(etJentikKulkasDispenser.getText().toString().trim());
        JentikBakMandi = TextUtils.isEmpty(etJentikBakMandi.getText().toString().trim()) ? 0 : Integer.parseInt(etJentikBakMandi.getText().toString().trim());
        JentikVasBunga = TextUtils.isEmpty(etJentikVasBunga.getText().toString().trim()) ? 0 : Integer.parseInt(etJentikVasBunga.getText().toString().trim());
        JentikPotBunga = TextUtils.isEmpty(etJentikPotBunga.getText().toString().trim()) ? 0 : Integer.parseInt(etJentikPotBunga.getText().toString().trim());
        JentikKontainerLain = TextUtils.isEmpty(etJentikKontainerLain.getText().toString().trim()) ? 0 : Integer.parseInt(etJentikKontainerLain.getText().toString().trim());

        tvKontainerDiPeriksa.setText(String.valueOf(
                JmlBak +
                        JmlTempayan +
                        JmlPecahan +
                        JmlBarangBekas +
                        JmlKulkasDispenser +
                        JmlBakMandi +
                        JmlVasBunga +
                        JmlPotBunga +
                        JmlKontainerLain
        ));

        tvTotalPositifJentik.setText(String.valueOf(
                JentikBak +
                        JentikTempayan +
                        JentikPecahan +
                        JentikBarangBekas +
                        JentikKulkasDispenser +
                        JentikBakMandi +
                        JentikVasBunga +
                        JentikPotBunga +
                        JentikKontainerLain
        ));

    }

    private void resetForm() {
        etHariTanggal.setText(new Waktu(getActivity()).getHariTanggalIndonesia(new Date().getTime()));
        etJmlBak.setText("");
        etJentikBak.setText("");
        etJmlTempayan.setText("");
        etJentikTempayan.setText("");
        etJmlPecahan.setText("");
        etJentikPecahan.setText("");
        etJmlBarangBekas.setText("");
        etJentikBarangBekas.setText("");
        etKulkasDispenser.setText("");
        etJentikKulkasDispenser.setText("");
        etJmlBakMandi.setText("");
        etJentikBakMandi.setText("");
        etJmlVasBunga.setText("");
        etJentikVasBunga.setText("");
        etJmlPotBunga.setText("");
        etJentikPotBunga.setText("");
        etJmlKontainerLain.setText("");
        etJentikKontainerLain.setText("");
        tvKontainerDiPeriksa.setText("0");
        tvTotalPositifJentik.setText("0");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        hitung();
    }
}
