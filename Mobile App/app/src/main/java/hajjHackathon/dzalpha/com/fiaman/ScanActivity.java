package hajjHackathon.dzalpha.com.fiaman;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

/*
    Scan activity
 */

public class ScanActivity extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scannerView;
    SharedData shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        shared = ((SharedData)getApplication());
        scannerView = findViewById(R.id.scanner_view);
        // Real time permissions request
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()){
                    // Initialize scanning
                    codeScanner = new CodeScanner(ScanActivity.this, scannerView);
                    codeScanner.setDecodeCallback(new DecodeCallback() {
                        @Override
                        public void onDecoded(@NonNull final Result result) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Toast.makeText(ScanActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(ScanActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    shared.editor = shared.LocationInfos.edit();
                                    shared.editor.putBoolean("First_time",false);
                                    shared.editor.apply();
                                    startActivity(new Intent(ScanActivity.this,MapActivity.class));
                                    finish();
                                }
                            });
                        }
                    });
                    codeScanner.startPreview();
                }else{
                    Toast.makeText(ScanActivity.this,"You can'T proceed without permissions !",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //     codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        //     codeScanner.releaseResources();
        super.onPause();
    }
}
