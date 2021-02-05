package com.example.googlemap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements GoogleMap.OnMarkerDragListener,
        OnMapReadyCallback,GoogleMap.OnMarkerClickListener,GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,GoogleMap.OnPolylineClickListener {
    private GoogleMap mapa;
    RadioButton c1,c2,c3,c4;
    Polyline line=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c1 = findViewById(R.id.MapHibrido);
        c2 = findViewById(R.id.MapSatelital);
        c3 = findViewById(R.id.MapTerreno);
        c4 = findViewById(R.id.MapNormal);
        c4.setChecked(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        LatLng ecuador = new LatLng(-1.0243508,-79.4663323);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ecuador,8));
       // mapa.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMarkerDragListener(this);
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnPolylineClickListener(this);
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        String lat,lng;
        lat = Double.toString(marker.getPosition().latitude);
        lng = Double.toString(marker.getPosition().longitude);
        Toast.makeText(this,lat +", "+lng,Toast.LENGTH_SHORT).show();
        return false;
    }
    @Override
    public void onMarkerDragStart(Marker marker) {
        Toast.makeText(this,"Marcador Seleccionado",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onMarkerDrag(Marker marker) {
        Toast.makeText(this,"Marcador Deslizandose",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onMarkerDragEnd(Marker marker) {
        Toast.makeText(this,"Marcador Fijado",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onMapLongClick(LatLng latLng) {
        String lat,lng;
        lat = Double.toString(latLng.latitude);
        lng = Double.toString(latLng.longitude);
        Toast.makeText(this,lat +", "+lng,Toast.LENGTH_SHORT).show();
        marcador(latLng);
    }
    public void tipoMapa(View view)
    {
        if (c1.isChecked()==true){
        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }else if(c2.isChecked()==true){
            mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }else if(c3.isChecked()==true){
            mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        } else if(c4.isChecked()==true){
            mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }
    private void marcador(LatLng posicion){
        MarkerOptions opMark= new MarkerOptions().position(posicion).draggable(true);
        markerList.add(mapa.addMarker(opMark));
    }
int conteoClick=0;
    List<LatLng> latlngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();
    @Override
    public void onMapClick(LatLng latLng) {
        marcador(latLng);
       latlngList.add(latLng);
    }
    public void cleanPolyline(View view){
if(line!= null) line.remove();
for (Marker mark: markerList) mark.remove();
latlngList.clear();
markerList.clear();

    }

public void dibujaPolyline(View view){
    if(line!= null) line.remove();
    PolylineOptions polOption = new PolylineOptions().addAll(latlngList).clickable(true);
    line = mapa.addPolyline(polOption);
}
    @Override
    public void onPolylineClick(Polyline polyline) {

    }
}