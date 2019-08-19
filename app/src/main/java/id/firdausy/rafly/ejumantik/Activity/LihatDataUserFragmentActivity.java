package id.firdausy.rafly.ejumantik.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.firdausy.rafly.ejumantik.Adapter.LihatDataUserAdapter;
import id.firdausy.rafly.ejumantik.Helper.Bantuan;
import id.firdausy.rafly.ejumantik.Model.InputModel;
import id.firdausy.rafly.ejumantik.Model.UserModel;
import id.firdausy.rafly.ejumantik.R;

public class LihatDataUserFragmentActivity extends AppCompatActivity {

    List<InputModel> list = new ArrayList<>();
    private Context context = LihatDataUserFragmentActivity.this;
    private RecyclerView rvKonten;
    private TextView tvDataTidakDitemukan;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private UserModel userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_admin);

        userModel = (UserModel) getIntent().getExtras().getSerializable("data");

        rvKonten = findViewById(R.id.rvKonten);
        tvDataTidakDitemukan = findViewById(R.id.tvDataTidakDitemukan);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(userModel.getNamaLengkap().toUpperCase());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        getData();
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        databaseReference.child("input")
                .child(userModel.getKeyUser())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        list.clear();
                        if (dataSnapshot.exists()) {
                            tvDataTidakDitemukan.setVisibility(View.GONE);
                            rvKonten.setVisibility(View.VISIBLE);
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                InputModel inputModel = data.getValue(InputModel.class);
                                assert inputModel != null;
                                inputModel.setKeyInputan(data.getKey());
                                list.add(inputModel);
                            }
                            rvKonten.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                            rvKonten.setAdapter(new LihatDataUserAdapter(context, list));

                        } else {
                            tvDataTidakDitemukan.setVisibility(View.VISIBLE);
                            rvKonten.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                        new Bantuan(context).swal_error(databaseError.getMessage());
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
