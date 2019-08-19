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

import id.firdausy.rafly.ejumantik.Helper.Bantuan;
import id.firdausy.rafly.ejumantik.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardAdminFragment extends Fragment {

    private TextView tv_totalAdmin;
    private TextView tv_totalKader;
    private TextView tv_totalData;
    private DatabaseReference databaseReference;


    public DashboardAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard_admin, container, false);
        tv_totalAdmin = v.findViewById(R.id.tv_totalAdmin);
        tv_totalKader = v.findViewById(R.id.tv_totalKader);
        tv_totalData = v.findViewById(R.id.tv_totalData);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        getTotalAdmin();
        getTotalKader();
        getTotalData();
        return v;
    }

    private void getTotalData() {
        databaseReference.child("input")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            int totalData = 0;
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                totalData += data.getChildrenCount();
                            }
                            tv_totalData.setText(totalData + " Data");
                        } else {
                            tv_totalData.setText("0 Data");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        new Bantuan(getActivity()).swal_error(databaseError.getMessage());
                    }
                });
    }

    private void getTotalKader() {
        databaseReference.child("user")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tv_totalKader.setText(dataSnapshot.getChildrenCount() + " Kader");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        new Bantuan(getActivity()).swal_error(databaseError.getMessage());
                    }
                });
    }

    private void getTotalAdmin() {
        databaseReference.child("admin")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tv_totalAdmin.setText(dataSnapshot.getChildrenCount() + " Admin");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        new Bantuan(getActivity()).swal_error(databaseError.getMessage());
                    }
                });
    }

}
