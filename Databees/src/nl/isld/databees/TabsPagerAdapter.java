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

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	
	private Inspection inspection;
	private int 		requestCode;

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		this.inspection = null;
		this.requestCode = 0;
	}
	
	public TabsPagerAdapter(FragmentManager fm, Inspection inspection, int requestCode) {
		super(fm);
		this.inspection = inspection;
		this.requestCode = requestCode;
	}

	
	@Override
	public Fragment getItem(int position) {
		Fragment fragment;
		
		switch (position) {
        case 0:
        	fragment = new InspectionGeneralFragment();		break;
        	
        case 1:
        	fragment = new InspectionQueenFragment();		break;
        	
        case 2:
        	fragment = new InspectionDiseasesFragment();	break;
        	
        default:
        	fragment = new Fragment();
        }
		
		if(requestCode == InspectionActivity.REQUEST_EDIT_INSPECTION) {
    		Bundle args = new Bundle();
    		args.putString(InspectionActivity.REQUEST_EXTRA_INSPECTION_ID, inspection.getId());
    		fragment.setArguments(args);
    	}
 
        return fragment;
	}
	

	@Override
	public int getCount() {
		return 3;
	}

}
