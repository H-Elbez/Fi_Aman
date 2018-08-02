package hajjHackathon.dzalpha.com.fiaman;

import android.app.Application;
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


}