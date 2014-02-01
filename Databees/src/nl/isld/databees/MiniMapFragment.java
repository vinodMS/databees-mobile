package nl.isld.databees;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MiniMapFragment extends SupportMapFragment {
	
	public static final int STANDARD_ZOOM	=	13;
	
	private LatLng position;
	
	
	public MiniMapFragment() {
	    super();
	    position = new LatLng(0,0);
	}
	
	public static MiniMapFragment newInstance(LatLng position){
	    MiniMapFragment miniMap = new MiniMapFragment();
	    miniMap.position = position;
	    return miniMap;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View v = super.onCreateView(inflater, container, savedInstanceState);
	    int height = (int) TypedValue.applyDimension(
	    		TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
	    v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
	    initMap();
	    return v;
	}
	
	public void moveMarker(LatLng position) {
		getMap().clear();
		getMap().addMarker(new MarkerOptions().position(position));
		getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(position, STANDARD_ZOOM));
	}
	
	private void initMap(){
		getMap().getUiSettings().setZoomControlsEnabled(false);
		getMap().getUiSettings().setAllGesturesEnabled(false);
		getMap().addMarker(new MarkerOptions().position(position));
		getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(position, STANDARD_ZOOM));
	}
}

