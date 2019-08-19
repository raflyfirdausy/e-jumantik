package id.firdausy.rafly.ejumantik.FragmentAdmin;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

import id.firdausy.rafly.ejumantik.Adapter.UserAdapter;
import id.firdausy.rafly.ejumantik.Helper.Bantuan;
import id.firdausy.rafly.ejumantik.Model.UserModel;
import id.firdausy.rafly.ejumantik.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LihatDataAdminFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private RecyclerView rvKonten;
    private TextView tvDataTidakDitemukan;
    private List<UserModel> list = new ArrayList<>();


    public LihatDataAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lihat_data_admin, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        rvKonten = v.findViewById(R.id.rvKonten);
        tvDataTidakDitemukan = v.findViewById(R.id.tvDataTidakDitemukan);

        getDataUser();
        return v;
    }

    private void getDataUser() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        databaseReference.child("user")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        if (dataSnapshot.exists()) {
                            tvDataTidakDitemukan.setVisibility(View.GONE);
                            rvKonten.setVisibility(View.VISIBLE);

                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                UserModel userModel = data.getValue(UserModel.class);
                                userModel.setKeyUser(data.getKey());
                                list.add(userModel);
                            }

                            rvKonten.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            rvKonten.setAdapter(new UserAdapter(getActivity(), list, "lihat"));
                        } else {
                            tvDataTidakDitemukan.setVisibility(View.VISIBLE);
                            rvKonten.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressDialog.dismiss();
                        new Bantuan(getActivity()).swal_error(databaseError.getMessage());
                    }
                });
    }

}
