package com.icandothisallday2020.ex67googlemap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InWinAdapter implements GoogleMap.InfoWindowAdapter {
    //Inflater 받아오기 위해 context 받기
    Context context;

    public InWinAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {//내용을 바꿈
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.infowindow,null);

        switch (marker.getTitle()){
            case "미래능력개발교육원":
                TextView tv=view.findViewById(R.id.tv);
                tv.setText(marker.getTitle());
                TextView tv2=view.findViewById(R.id.tv2);
                tv2.setText(marker.getSnippet());
                break;
            case "seoul":
                break;
        }
        return view;
    }
}
