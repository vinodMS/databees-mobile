/*
	Databees a beekeeping organizer app.
    Copyright (C) 2014 NBV (Nederlandse Bijenhouders Vereniging)
    http://www.bijenhouders.nl/

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

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
