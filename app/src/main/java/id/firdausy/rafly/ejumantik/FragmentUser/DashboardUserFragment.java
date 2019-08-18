package id.firdausy.rafly.ejumantik.FragmentUser;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
public class DashboardUserFragment extends Fragment {

    private TextView tvBerapaKali;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    public DashboardUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard_user, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        tvBerapaKali = v.findViewById(R.id.tvBerapaKali);

        databaseReference.child("input")
                .child(firebaseAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tvBerapaKali.setText(dataSnapshot.getChildrenCount() + " Kali");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        new Bantuan(getActivity()).swal_error(databaseError.getMessage());
                    }
                });

        return v;
    }

}
