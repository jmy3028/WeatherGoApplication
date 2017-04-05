package com.practice.jmy3028.gmappracticeapplication;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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

    private LatLng seoul;
    private GetApi mGetApi;

    private GoogleMap mMap;
    private Geocoder geocoder;
    private List<Address> list;

    private WeatherMain mResult1;
    private Example mResult2;
    private SimpleDateFormat formatter;

    private double firstLat = 37.541;
    private double firstLon = 126.986;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geocoder = new Geocoder(this);

        //retrofitUtil을 통해서 api 연결
        mGetApi = new RetrofitUtil().getUserApi();

        formatter = new SimpleDateFormat("HH:mm", Locale.KOREA);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        seoul = new LatLng(firstLat, firstLon);

        getCallResult(firstLat,firstLon);
        mMap.addMarker(new MarkerOptions().position(seoul).title("" +
                formatter.format(mResult1.getSys().getSunrise() * 1000L) + " > " +
                formatter.format(mResult1.getSys().getSunset() * 1000L)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 12));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng)
                        .title("" + formatter.format(mResult1.getSys().getSunrise() * 1000L)
                                + " > " +
                                formatter.format(mResult1.getSys().getSunset() * 1000L)));
                getCallResult(latLng.latitude,latLng.longitude);
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
                        getCallResult(lat,lon);

                        seoul = new LatLng(lat, lon);

                        mMap.addMarker(new MarkerOptions().position(seoul).title("Marker in Sydney"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 16));

                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                Intent intent = new Intent(MainActivity.this, FragmentsActivity.class);
                                intent.putExtra("Result1", mResult1);
                                intent.putExtra("Result2", mResult2);
                                startActivity(intent);
                            }
                        });

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


    //Call를 요청하는 부분
    public void getCallResult(double lat, double lon) {

        //첫번째 Call
        Call<WeatherMain> call = mGetApi.latlon(GetApi.BASE_APPID,
                lat, lon);
        call.enqueue(new Callback<WeatherMain>() {
            @Override
            public void onResponse(Call<WeatherMain> call, Response<WeatherMain> response) {
                mResult1 = response.body();

            }

            @Override
            public void onFailure(Call<WeatherMain> call, Throwable throwable) {
            }
        });

        //두번째 Call
        Call<Example> call2 = mGetApi.latlon2(GetApi.BASE_APPID,
                lat, lon);
        call2.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call,
                                   Response<Example> response) {
                mResult2 = response.body();

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
            }
        });
    }

}
