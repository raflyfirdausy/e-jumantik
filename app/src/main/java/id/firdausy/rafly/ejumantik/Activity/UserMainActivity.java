package id.firdausy.rafly.ejumantik.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import id.firdausy.rafly.ejumantik.FragmentUser.DashboardUserFragment;
import id.firdausy.rafly.ejumantik.FragmentUser.InputDataUserFragment;
import id.firdausy.rafly.ejumantik.FragmentUser.LihatDataUserFragment;
import id.firdausy.rafly.ejumantik.FragmentUser.PencegahanUserFragment;
import id.firdausy.rafly.ejumantik.Helper.Bantuan;
import id.firdausy.rafly.ejumantik.R;
import id.firdausy.rafly.ejumantik.Universal.LoginActivity;
import id.firdausy.rafly.ejumantik.Universal.UbahPasswordFragment;

public class UserMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean tekanKembaliUntukKeluar = false;
    private Context context = UserMainActivity.this;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("E Jumantik");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        final TextView tv_namaPengguna = navigationView.getHeaderView(0).findViewById(R.id.headerNama);
        final TextView tv_emailPengguna = navigationView.getHeaderView(0).findViewById(R.id.headerEmail);

        databaseReference.child("user")
                .child(firebaseAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tv_namaPengguna.setText(dataSnapshot.child("namaLengkap").getValue(String.class));
                        tv_emailPengguna.setText(firebaseAuth.getCurrentUser().getEmail());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        new Bantuan(context).swal_error(databaseError.getMessage());
                    }
                });

        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fl_content,
                        new DashboardUserFragment(),
                        "action_dashboard")
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (tekanKembaliUntukKeluar) {
                super.onBackPressed();
            }

            tekanKembaliUntukKeluar = true;
            new Bantuan(context).toastLong("Tekan sekali lagi untuk keluar");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tekanKembaliUntukKeluar = false;
                }
            }, 2000);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        FragmentTransaction FT = getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        if (id == R.id.action_dashboard) {
            FT.replace(R.id.fl_content,
                    new DashboardUserFragment(),
                    "action_dashboard")
                    .commit();
        } else if (id == R.id.action_inputData) {
            FT.replace(R.id.fl_content,
                    new InputDataUserFragment(),
                    "action_inputData")
                    .commit();
        } else if (id == R.id.action_lihat) {
            FT.replace(R.id.fl_content,
                    new LihatDataUserFragment(),
                    "action_lihat")
                    .commit();
        } else if (id == R.id.action_pencegahan) {
            FT.replace(R.id.fl_content,
                    new PencegahanUserFragment(),
                    "action_pencegahan")
                    .commit();
        } else if (id == R.id.action_ubahPassword) {
            FT.replace(R.id.fl_content,
                    new UbahPasswordFragment(),
                    "action_ubahPassword")
                    .commit();
        } else if (id == R.id.action_logout) {
            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Peringatan")
                    .setContentText("Apakah kamu ingin logout dari aplikasi ?")
                    .setConfirmText("YA")
                    .setCancelText("TIDAK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            firebaseAuth.signOut();
                            sDialog
                                    .setTitleText("Berhasil Logout !")
                                    .setConfirmText("OK")
                                    .setContentText("Klik OK Untuk kembali ke halaman login")
                                    .showContentText(false)
                                    .setConfirmClickListener(null)
                                    .showCancelButton(false)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            startActivity(new Intent(context, LoginActivity.class));
                                            finish();
                                        }
                                    })
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }
                    })
                    .show();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
