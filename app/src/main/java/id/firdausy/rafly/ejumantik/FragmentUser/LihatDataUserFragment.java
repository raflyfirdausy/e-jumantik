package id.firdausy.rafly.ejumantik.FragmentUser;


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

import id.firdausy.rafly.ejumantik.Adapter.LihatDataUserAdapter;
import id.firdausy.rafly.ejumantik.Helper.Bantuan;
import id.firdausy.rafly.ejumantik.Model.InputModel;
import id.firdausy.rafly.ejumantik.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LihatDataUserFragment extends Fragment {

    List<InputModel> list = new ArrayList<>();
    private RecyclerView rvKonten;
    private TextView tvDataTidakDitemukan;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public LihatDataUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lihat_data_user, container, false);
        rvKonten = v.findViewById(R.id.rvKonten);
        tvDataTidakDitemukan = v.findViewById(R.id.tvDataTidakDitemukan);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        getData();

        return v;
    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        databaseReference.child("input")
                .child(firebaseAuth.getCurrentUser().getUid())
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
                            rvKonten.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            rvKonten.setAdapter(new LihatDataUserAdapter(getActivity(), list));

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
