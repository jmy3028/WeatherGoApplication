package com.practice.jmy3028.gmappracticeapplication;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.practice.jmy3028.gmappracticeapplication.api.GetApi;
import com.practice.jmy3028.gmappracticeapplication.api.RetrofitUtil;
import com.practice.jmy3028.gmappracticeapplication.fragments.ListFragment;
import com.practice.jmy3028.gmappracticeapplication.fragments.WeatherFragment;
import com.practice.jmy3028.gmappracticeapplication.model.WeatherMain;
import com.practice.jmy3028.gmappracticeapplication.model2.Example;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GetApi mGetApi;

    private GoogleMap mMap;
    private Geocoder geocoder;
    private List<Address> list;

    private WeatherMain mResult1;
    private Example mResult2;
    private SimpleDateFormat formatter;

    private double firstLat = 37.541;
    private double firstLon = 126.986;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private LocationManager mLocationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        mLocationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);

        geocoder = new Geocoder(this);

        //retrofitUtil을 통해서 api 연결
        mGetApi = new RetrofitUtil().getUserApi();

        formatter = new SimpleDateFormat("HH:mm", Locale.KOREA);


    }

    private boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, 1000);

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        1000);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                getCallResult(firstLat, firstLon, 12);
                mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        getLatLon();
                        if(!isGPSEnabled && !isNetworkEnabled){
                            Toast.makeText(MainActivity.this, "GPS를 켜주세요.", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "GPS가 정상작동 됩니다.", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
            } else {
                getCallResult(firstLat, firstLon, 12);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //권한이 승인 되었습니다.
                    Toast.makeText(this, "권한이 승인되었습니다.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //EditText 공백체크.
                if (!query.equals("")) {
                    //만약 공백이 아니면 reMapReady에 입력값 전송.
                    reMapReady(query);
                    //reMapReady작업이 끝나고 결과 값을 리턴해주면 그 값이 비어있는지 확인.
                    if (list != null) {
                        //위도 경도 값 가져와서 지도에 마커 표시하기
                        final double lat = list.get(0).getLatitude();
                        final double lon = list.get(0).getLongitude();

                        //주소를 검색 하였을때 다시한번 그지역에 맞는 날씨를 콜한다.
                        getCallResult(lat, lon, 16);


                        onResponseComplete(16);

                    } else {

                    }
                } else {
                    Toast.makeText(MainActivity.this, "주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("MainActivity", "onQueryTextChange: " + newText);
                return false;
            }
        });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_menu:
                //지우는 버튼 눌렀을때 실행되는 부분
                mMap.clear();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void reMapReady(String city) {

        try {
            list = geocoder.getFromLocationName(
                    city, // 지역 이름
                    10); // 읽을 개수
        } catch (IOException e) {
            Toast.makeText(this, "입력 오류", Toast.LENGTH_SHORT).show();
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

    public void onResponseComplete(int zoom) {
        LatLng latLng = new LatLng(mResult1.getCoord().getLat(), mResult1.getCoord().getLon());
        mMap.addMarker(new MarkerOptions().position(latLng).title("" +
                formatter.format(mResult1.getSys().getSunrise() * 1000L) + " > " +
                formatter.format(mResult1.getSys().getSunset() * 1000L)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng)
                        .title("" + formatter.format(mResult1.getSys().getSunrise() * 1000L)
                                + " > " +
                                formatter.format(mResult1.getSys().getSunset() * 1000L)));
                getCallResult(latLng.latitude, latLng.longitude, 16);
            }
        });
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MainActivity.this, FragmentsActivity.class);
                intent.putExtra("Result1", mResult1);
                intent.putExtra("Result2", mResult2);
                startActivity(intent);
            }
        });
    }


    //Call를 요청하는 부분
    public void getCallResult(final double lat, final double lon, final int zoom) {

        //첫번째 Call
        Call<WeatherMain> call = mGetApi.latlon(GetApi.BASE_APPID,
                lat, lon);
        call.enqueue(new Callback<WeatherMain>() {
            @Override
            public void onResponse(Call<WeatherMain> call, Response<WeatherMain> response) {
                mResult1 = response.body();

                //두번째 Call
                Call<Example> call2 = mGetApi.latlon2(GetApi.BASE_APPID,
                        lat, lon);
                call2.enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call,
                                           Response<Example> response) {
                        mResult2 = response.body();

                        onResponseComplete(zoom);
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                    }
                });

            }

            @Override
            public void onFailure(Call<WeatherMain> call, Throwable t) {
            }
        });


    }

    public void getLatLon() {
        // GPS 프로바이더 사용가능여부
        isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 네트워크 프로바이더 사용가능여부
        isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
        Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                firstLat = location.getLatitude();
                firstLon = location.getLongitude();

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

}
