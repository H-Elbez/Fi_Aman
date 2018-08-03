package hajjHackathon.dzalpha.com.fiaman;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 *  Shared informations
 */
public class SharedData extends Application {
    SharedPreferences LocationInfos  ;
    SharedPreferences.Editor editor ;

    @Override
    public void onCreate() {
        super.onCreate();
        LocationInfos = getSharedPreferences("Data",0);
    }

    public void turnGPSOn(Context c) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
        alertDialogBuilder.setMessage("Would you like to enable GPS ?")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                callGPSSettingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}