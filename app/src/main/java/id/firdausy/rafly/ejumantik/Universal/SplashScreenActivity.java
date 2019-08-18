package id.firdausy.rafly.ejumantik.Universal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import id.firdausy.rafly.ejumantik.Activity.AdminMainActivity;
import id.firdausy.rafly.ejumantik.Activity.UserMainActivity;
import id.firdausy.rafly.ejumantik.Helper.Bantuan;
import id.firdausy.rafly.ejumantik.R;

public class SplashScreenActivity extends AppCompatActivity {
    private Context context = SplashScreenActivity.this;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Timer timer = new Timer();
        timer.schedule(new Splash(), 2000);
    }

    private class Splash extends TimerTask {
        @Override
        public void run() {
            if (firebaseAuth.getCurrentUser() != null) {
                databaseReference.child("user")
                        .child(firebaseAuth.getCurrentUser().getUid())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    startActivity(new Intent(context, UserMainActivity.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(context, AdminMainActivity.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                new Bantuan(context).swal_error(databaseError.getMessage());
                            }
                        });
            } else {
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        }
    }
}
