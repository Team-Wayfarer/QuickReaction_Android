package com.example.QuickReactionMJ;

import android.os.Bundle;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapView.POIItemEventListener;
import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        MapView mapView = new MapView(this);

        mapView.setDaumMapApiKey("55b506eec012c6de79bbd7eaf854f71f"); // 안 쓰면 검은화면 ㅋㅋ

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.5514579595, 126.951949155);
        mapView.setMapCenterPoint(mapPoint, true);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker);

    }
}
