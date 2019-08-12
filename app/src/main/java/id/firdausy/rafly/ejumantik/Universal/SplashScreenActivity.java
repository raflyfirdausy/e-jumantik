package id.firdausy.rafly.ejumantik.Universal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import id.firdausy.rafly.ejumantik.R;

public class SplashScreenActivity extends AppCompatActivity {
    private Context context = SplashScreenActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Timer timer = new Timer();
        timer.schedule(new Splash(), 2000);
    }

    private class Splash extends TimerTask {
        @Override
        public void run() {
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }
    }
}
