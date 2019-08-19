package id.firdausy.rafly.ejumantik.FragmentAdmin;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import id.firdausy.rafly.ejumantik.Helper.Bantuan;
import id.firdausy.rafly.ejumantik.Helper.Waktu;
import id.firdausy.rafly.ejumantik.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HitungAbjAdminFragment extends Fragment {

    private MaterialSpinner msBulan;
    private MaterialSpinner msTahun;
    private TextView tvPersen;
    private TextView tvTidakDitemukanJentik;
    private TextView tvTotalRumahDiPeriksa;
    private DatabaseReference databaseReference;
    private int bulanFix, tahunFix;

    private String[] namaBulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

    private List<String> tahun = new ArrayList<>();

    public HitungAbjAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hitung_abj_admin, container, false);
        msBulan = v.findViewById(R.id.msBulan);
        msTahun = v.findViewById(R.id.msTahun);
        tvPersen = v.findViewById(R.id.tvPersen);
        tvTidakDitemukanJentik = v.findViewById(R.id.tvTidakDitemukanJentik);
        tvTotalRumahDiPeriksa = v.findViewById(R.id.tvTotalRumahDiPeriksa);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        msBulan.setItems(namaBulan);

        gettahun();

        return v;
    }


    private void getData(final long bulan, final long tahun) {
        databaseReference.child("input")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long tidakDitemukan = 0;
                        long rumahDiperiksa = 0;
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                for (DataSnapshot dataLagi : data.getChildren()) {
                                    String tahunString = String.valueOf(new Waktu(getActivity()).getTahunTimestamp(Long.parseLong(dataLagi.child("waktu").getValue(String.class))));
                                    String bulanString = String.valueOf(new Waktu(getActivity()).getBulanTimestamp(Long.parseLong(dataLagi.child("waktu").getValue(String.class))));

                                    long tahunLong = Long.parseLong(tahunString);
                                    long bulanLong = Long.parseLong(bulanString);

                                    if (bulan == bulanLong && tahun == tahunLong) {
                                        rumahDiperiksa += 1;
                                        if (dataLagi.child("totalPositifJentik").getValue(String.class).equalsIgnoreCase("0")) {
                                            tidakDitemukan += 1;
                                        }
                                    }
                                }
                            }
                            float persen = 0;
                            if (rumahDiperiksa != 0) {
                                persen = ((float) tidakDitemukan) / rumahDiperiksa * 100;
                            }

                            tvPersen.setText(String.valueOf(String.format("%.2f", persen)));
                            tvTidakDitemukanJentik.setText("Total Rumah Tidak Ditemukan Jentik : " + tidakDitemukan);
                            tvTotalRumahDiPeriksa.setText("Total Rumah yang di periksa : " + rumahDiperiksa);
                        } else {
                            new Bantuan(getActivity()).swal_error("Data tidak ditemukan");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        new Bantuan(getActivity()).swal_error(databaseError.getMessage());
                    }
                });
    }

    private void gettahun() {
        databaseReference.child("input")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tahun.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                for (DataSnapshot dataLagi : data.getChildren()) {
                                    String tahunString = String.valueOf(new Waktu(getActivity()).getTahunTimestamp(Long.parseLong(dataLagi.child("waktu").getValue(String.class))));
                                    boolean kosong = true;
                                    for (int i = 0; i < tahun.size(); i++) {
                                        if (tahun.get(i).contains(tahunString)) {
                                            kosong = false;
                                            break;
                                        }
                                    }

                                    if (kosong) {
                                        tahun.add(tahunString);
                                    }
                                }
                            }
                        }
                        msTahun.setItems(tahun);
                        tahunFix = Integer.parseInt(tahun.get(0));
                        getData(bulanFix, tahunFix);

                        msBulan.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//                new Bantuan(getActivity()).swal_sukses(String.valueOf(view.getSelectedIndex()));
                                bulanFix = view.getSelectedIndex();
                                getData(bulanFix, tahunFix);
                            }
                        });


                        msTahun.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//                new Bantuan(getActivity()).swal_sukses(view.getItems().get(position).toString());
                                tahunFix = Integer.parseInt(view.getItems().get(position).toString());
                                getData(bulanFix, tahunFix);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        new Bantuan(getActivity()).swal_error(databaseError.getMessage());
                    }
                });
    }

}
