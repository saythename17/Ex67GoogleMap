package com.icandothisallday2020.ex67googlemap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    //※1.Google Map API Library 추가 [play-services-maps]
    //※2.Google 지도 사용에 대한 API key 발급
    //※3.Google 지도를 제어하는 객체 참조변수
    GoogleMap GMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //xml 에 있는 SupportMapFragment 참조를 위해 프레그먼트매니저 객체 호출
        final FragmentManager manager=getSupportFragmentManager();
        //매니저를 통해 Fragment 참조
        SupportMapFragment mapFragment=(SupportMapFragment) manager.findFragmentById(R.id.map);
        //[비동기(:동시==Async) 방식](별도의 Thread 생성하여 작업)으로 지도 호출
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //내 멤버변수에 얻어온 GoogleMap 대입
                GMap=googleMap;
                //원하는 좌표에 지도를 설정하기 위해 좌표 객체 생성
                LatLng seoul=new LatLng(37.56,126.97);
                //지도에 마커 옵션설정 객체 생성
                MarkerOptions mOptions=new MarkerOptions();
                mOptions.position(seoul);
                mOptions.title("서울");
                mOptions.snippet("대한민국의 수도");
                //지도에 마커 추가
                GMap.addMarker(mOptions);//옵션설정을 주면 마커를 생성
                //원하는 좌표위치로 카메라 이동 GMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));

                //카메라 이동:smooth 효과 & zoom 적용
                GMap.animateCamera(CameraUpdateFactory.newLatLngZoom(seoul,17));//v:1~25(최대확대)

                //마커 여러개 추가
                LatLng mrhi=new LatLng(37.5608,127.0346);
                MarkerOptions options=new MarkerOptions();
                options.position(mrhi);
                options.title("미래능력개발교육원");
                options.snippet("http://www.mrhi.or.kr");
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.bread));
                mOptions.anchor(0.5f,0.5f);//x축,y축
                Marker marker=GMap.addMarker(options);//추가된 마커 객체를 리턴해줌
                //마커 객체를 통해 마커를 클릭하지 않아도 InfoWindow 가 보이도록 설정
                marker.showInfoWindow();
                //InfoWindow 클릭 시 반응(링크와 연결)
                GMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        String title=marker.getTitle();
                        if(title.equals("미래능력개발교육원")){
                            //연결된 페이지로 이동
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            Uri uri= Uri.parse(marker.getSnippet());
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    }
                });
                GMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mrhi,17));

                //InfoWindow Custom->Create InfoWindow Adapter
                GMap.setInfoWindowAdapter(new InWinAdapter(MainActivity.this));
                //ZoomControl [+][-]버튼 생성
                UiSettings settings=GMap.getUiSettings();
                settings.setZoomControlsEnabled(true);
                //내위치 보이기[동적 퍼미션 작업 필요]
                GMap.setMyLocationEnabled(true);

            }
        });
        //SupportMapFragment 안에 있는 GoogleMap 객체 얻기

    }
}
