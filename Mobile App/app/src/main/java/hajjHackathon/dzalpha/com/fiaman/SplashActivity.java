package hajjHackathon.dzalpha.com.fiaman;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/*
    Splash activity
 */

public class SplashActivity extends AppCompatActivity {
    SharedData shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        shared = ((SharedData)getApplication());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(shared.LocationInfos.getBoolean("First_time",true)){
                    startActivity(new Intent(SplashActivity.this,ScanActivity.class));
                }else{
                    startActivity(new Intent(SplashActivity.this,MapActivity.class));
                }

                finish();
            }
        }, 2500);
    }
}
