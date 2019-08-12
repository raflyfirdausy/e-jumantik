package id.firdausy.rafly.ejumantik.Universal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import id.firdausy.rafly.ejumantik.Admin.AdminMainActivity;
import id.firdausy.rafly.ejumantik.R;

public class LoginActivity extends AppCompatActivity {
    private Context context = LoginActivity.this;
    private TextInputEditText myet_email;
    private TextInputEditText myet_password;
    private Button btn_login;
    private TextView tv_DaftarDisini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myet_email = findViewById(R.id.myet_email);
        myet_password = findViewById(R.id.myet_password);
        btn_login = findViewById(R.id.btn_login);
        tv_DaftarDisini = findViewById(R.id.tv_DaftarDisini);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AdminMainActivity.class));
            }
        });

        tv_DaftarDisini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, RegistrasiACtivity.class));
            }
        });
    }
}
