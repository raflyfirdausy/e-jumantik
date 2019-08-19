package id.firdausy.rafly.ejumantik.User;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import id.firdausy.rafly.ejumantik.Helper.Waktu;
import id.firdausy.rafly.ejumantik.Model.InputModel;
import id.firdausy.rafly.ejumantik.R;

public class DetailInputanActivity extends AppCompatActivity {

    private Context context = DetailInputanActivity.this;

    private Toolbar toolbar;

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
    private InputModel inputModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_inputan);

        inputModel = (InputModel) Objects.requireNonNull(getIntent().getExtras()).getSerializable("data");

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etHariTanggal = findViewById(R.id.etHariTanggal);
        etJmlBak = findViewById(R.id.etJmlBak);
        etJentikBak = findViewById(R.id.etJentikBak);
        etJmlTempayan = findViewById(R.id.etJmlTempayan);
        etJentikTempayan = findViewById(R.id.etJentikTempayan);
        etJmlPecahan = findViewById(R.id.etJmlPecahan);
        etJentikPecahan = findViewById(R.id.etJentikPecahan);
        etJmlBarangBekas = findViewById(R.id.etJmlBarangBekas);
        etJentikBarangBekas = findViewById(R.id.etJentikBarangBekas);
        etKulkasDispenser = findViewById(R.id.etKulkasDispenser);
        etJentikKulkasDispenser = findViewById(R.id.etJentikKulkasDispenser);
        etJmlBakMandi = findViewById(R.id.etJmlBakMandi);
        etJentikBakMandi = findViewById(R.id.etJentikBakMandi);
        etJmlVasBunga = findViewById(R.id.etJmlVasBunga);
        etJentikVasBunga = findViewById(R.id.etJentikVasBunga);
        etJmlPotBunga = findViewById(R.id.etJmlPotBunga);
        etJentikPotBunga = findViewById(R.id.etJentikPotBunga);
        etJmlKontainerLain = findViewById(R.id.etJmlKontainerLain);
        etJentikKontainerLain = findViewById(R.id.etJentikKontainerLain);
        tvKontainerDiPeriksa = findViewById(R.id.tvKontainerDiPeriksa);
        tvTotalPositifJentik = findViewById(R.id.tvTotalPositifJentik);
        btnSimpan = findViewById(R.id.btnSimpan);

        btnSimpan.setVisibility(View.GONE);

        etHariTanggal.setFocusable(false);
        etJmlBak.setFocusable(false);
        etJentikBak.setFocusable(false);
        etJmlTempayan.setFocusable(false);
        etJentikTempayan.setFocusable(false);
        etJmlPecahan.setFocusable(false);
        etJentikPecahan.setFocusable(false);
        etJmlBarangBekas.setFocusable(false);
        etJentikBarangBekas.setFocusable(false);
        etKulkasDispenser.setFocusable(false);
        etJentikKulkasDispenser.setFocusable(false);
        etJmlBakMandi.setFocusable(false);
        etJentikBakMandi.setFocusable(false);
        etJmlVasBunga.setFocusable(false);
        etJentikVasBunga.setFocusable(false);
        etJmlPotBunga.setFocusable(false);
        etJentikPotBunga.setFocusable(false);
        etJmlKontainerLain.setFocusable(false);
        etJentikKontainerLain.setFocusable(false);

        etHariTanggal.setText(new Waktu(context).getHariTanggalIndonesia(Long.parseLong(inputModel.getWaktu())));
        etJmlBak.setText(inputModel.getJmlBak());
        etJentikBak.setText(inputModel.getJentikBak());
        etJmlTempayan.setText(inputModel.getJmlTempayan());
        etJentikTempayan.setText(inputModel.getJentikTempayan());
        etJmlPecahan.setText(inputModel.getJmlPecahan());
        etJentikPecahan.setText(inputModel.getJmlPecahan());
        etJmlBarangBekas.setText(inputModel.getJmlBarangBekas());
        etJentikBarangBekas.setText(inputModel.getJentikBarangBekas());
        etKulkasDispenser.setText(inputModel.getJmlKulkasDispenser());
        etJentikKulkasDispenser.setText(inputModel.getJentikKulkasDispenser());
        etJmlBakMandi.setText(inputModel.getJmlBakMandi());
        etJentikBakMandi.setText(inputModel.getJentikBakMandi());
        etJmlVasBunga.setText(inputModel.getJmlVasBunga());
        etJentikVasBunga.setText(inputModel.getJentikVasBunga());
        etJmlPotBunga.setText(inputModel.getJmlPotBunga());
        etJentikPotBunga.setText(inputModel.getJentikPotBunga());
        etJmlKontainerLain.setText(inputModel.getJmlKontainerLain());
        etJentikKontainerLain.setText(inputModel.getJentikKontainerLain());

        tvTotalPositifJentik.setText(inputModel.getTotalPositifJentik());
        tvKontainerDiPeriksa.setText(inputModel.getTotalKontainer());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
