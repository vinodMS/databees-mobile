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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class InspectionQueenFragment extends Fragment {
	
	private CheckBox			queenSeenInput;
	private CheckBox			queenMarkedInput;
	private EditText			queenOriginInput;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_inspection_queen, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		queenSeenInput		= (CheckBox) getView().findViewById(R.id.queen_seen_input);
		queenSeenInput.setOnCheckedChangeListener((InspectionActivity) getActivity());
		queenMarkedInput	= (CheckBox) getView().findViewById(R.id.queen_marked_input);
		queenMarkedInput.setOnCheckedChangeListener((InspectionActivity) getActivity());
		queenOriginInput	= (EditText) getView().findViewById(R.id.queen_origin_input);
		queenOriginInput.addTextChangedListener((InspectionActivity) getActivity());
		
		if(getArguments() != null) {
			Inspection inspection = LocalStore.findInspectionById(getArguments().getString(
					InspectionActivity.REQUEST_EXTRA_INSPECTION_ID));
			
		queenSeenInput.setChecked(inspection.getParameters().queenSeen);
		queenMarkedInput.setChecked(inspection.getParameters().queenMarked);
		queenOriginInput.requestFocus();
		queenOriginInput.setText(inspection.getParameters().queenOrigin);
		}
	}
	
}
