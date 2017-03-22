package com.practice.jmy3028.gmappracticeapplication;

import android.app.SearchManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private double seoulLat;
    private double seoulLon;
    private LatLng seoul;

    private GoogleMap mMap;
    private EditText mEdit;
    private Geocoder geocoder;
    private List<Address> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geocoder = new Geocoder(this);

        mEdit = (EditText) findViewById(R.id.aaa_edit);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        seoulLat = 37.541;
        seoulLon = 126.986;

        // Add a marker in Sydney and move the camera
        seoul = new LatLng(seoulLat, seoulLon);
        mMap.addMarker(new MarkerOptions().position(seoul).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul,12));


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(MainActivity.this, "마커를 클릭하셨습니다.", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

//        //검색 기능 활성화 한다.
//        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//        //검색 버튼을 가져온다.
//        MenuItem searchButton = menu.findItem(R.id.menu_search);
//        //검색버튼을 클릭했을 때 SearchView를 가져온다.
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchButton);
//        //검색 힌트를 설정한다.
////        searchView.setQueryHint("주소를 검색해 주세요.");
//
//        //SearchView를 검색 가능한 위젯으로 설정한다.
////        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_search:
                reMapReady(mEdit.getText().toString());
                if(list.size() != 0){
                    double lat = list.get(0).getLatitude();
                    double lon = list.get(0).getLongitude();
                    seoul = new LatLng(lat,lon);
                    mMap.addMarker(new MarkerOptions().position(seoul).title("Marker in Sydney"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul,16));
                }
                return true;
            case R.id.delete_menu:
                mMap.clear();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void reMapReady(String city){
        list = null;

        try {
            list = geocoder.getFromLocationName(
                    city, // 지역 이름
                    10); // 읽을 개수
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
        }

        if (list != null) {
            if (list.size() == 0) {
                Toast.makeText(this, "해당되는 주소정보는 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                //          list.get(0).getCountryName();  // 국가명
                //          list.get(0).getLatitude();     // 위도
                //          list.get(0).getLongitude();    // 경도
            }
        }
    }

}
