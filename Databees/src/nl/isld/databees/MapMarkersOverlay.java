package nl.isld.databees;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapMarkersOverlay extends ItemizedOverlay<OverlayItem> {
	
	ArrayList<OverlayItem> items;

	public MapMarkersOverlay(Drawable marker) {
		super(marker);
		
		items = new ArrayList<OverlayItem>();
	}
	
	public void addItem(OverlayItem item) {
		items.add(item);
		populate();
	}

	@Override
	protected OverlayItem createItem(int position) {
		return items.get(position);
	}

	@Override
	public int size() {
		return items.size();
	}

}
