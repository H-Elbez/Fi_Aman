package hajjHackathon.dzalpha.com.fiaman;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MapView map;
    CardView my_position,menu;
    Button SOS;
    IMapController mapController;
    RotationGestureOverlay mRotationGestureOverlay;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));


        map = findViewById(R.id.map);
        my_position = findViewById(R.id.my_position);
        menu = findViewById(R.id.menu);
        SOS = findViewById(R.id.SOS);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        mapController = map.getController();
        mapController.setZoom(9.0);
        GeoPoint startPoint = new GeoPoint(21.4359571, 39.7064624);
        mapController.setCenter(startPoint);
        mRotationGestureOverlay = new RotationGestureOverlay(this, map);
        mRotationGestureOverlay.setEnabled(true);
        map.setMultiTouchControls(true);
        map.setBuiltInZoomControls(false);
        map.getOverlays().add(mRotationGestureOverlay);
        MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay(map);
        myLocationoverlay.enableFollowLocation();
        myLocationoverlay.enableMyLocation();
        map.getOverlays().add(myLocationoverlay);
        mapController.setCenter(myLocationoverlay.getMyLocation());
        mapController.stopAnimation(false);
        mapController.setZoom(15.0);


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        my_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapController.setCenter(myLocationoverlay.getMyLocation());
                mapController.stopAnimation(false);
                mapController.setZoom(15.0);
            }
        });
        SOS.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                sendSos();
                Toast.makeText(MapActivity.this,"SOS Sent", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {}
                drawer.openDrawer(GravityCompat.START);
            }
        });
        if(!testGps()){
            ((SharedData)getApplication()).turnGPSOn(MapActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
           int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_query:
                startActivity(new Intent(MapActivity.this,NeedsActivity.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(MapActivity.this,SettingsActivity.class));
                break;
            case R.id.action_reset:
                // todo : reset
                finish();
                break;
            case R.id.action_about:
                startActivity(new Intent(MapActivity.this,AboutActivity.class));
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onResume(){
        super.onResume();
        map.onResume();
    }

    public void onPause(){
        super.onPause();
        map.onPause();
    }

    public boolean testGps(){
        LocationManager locationManager = (LocationManager) MapActivity.this.getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            return true;
        }else{
            return false;
        }}

    public void sendSos() {
        Thread thread = new Thread(() -> {
            try {
            //  URL url = new URL("http://192.168.137.196:3000/api/v1/Hajj/"+672692);
                URL url = new URL("http://192.168.137.196:3000/api/v1/Hajj/"+672692);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                JSONObject jsonParam = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonParam);
                jsonParam.put("date","2018-08-02");
                jsonParam.put("geo","21.453867,39.485447");
                jsonParam.put("demandType","sos");
                jsonParam.put("treated",false);
                JSONObject data = new JSONObject();
                data.put("helpDemands",jsonArray);

                Log.i("JSON", data.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonParam.toString());
                os.flush();
                os.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }

}
