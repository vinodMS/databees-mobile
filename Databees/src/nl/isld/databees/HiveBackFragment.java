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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class HiveBackFragment extends Fragment {
	
	EditText	hiveSuppers;
	
	/*
	 * Overridden method of class Fragment.
	 * It creates the fragment view and returns it to the
	 * activity that uses the fragment.
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hive_back, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		hiveSuppers = (EditText) view.findViewById(R.id.suppers_value_input);
		hiveSuppers.addTextChangedListener((HiveActivity) getActivity());
		
		if(getArguments().getInt("REQUEST_CODE") == HiveActivity.REQUEST_EDIT_HIVE) {
			Hive hive = LocalStore.findHiveById(
					getArguments().getString(HiveActivity.REQUEST_EXTRA_HIVE_ID));
			hiveSuppers.setText(String.valueOf(hive.getNumberOfSuppers()));
		}
	}

}
