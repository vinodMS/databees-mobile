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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class InspectionDiseasesFragment extends Fragment
	implements OnCheckedChangeListener {
	
	CheckBox	diseasePresent;
	CheckBox	otherPresent;
	EditText	diseaseName;
	EditText	otherDescr;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_inspection_diseases, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		diseasePresent	= (CheckBox) view.findViewById(R.id.disease_present);
		diseasePresent.setOnCheckedChangeListener(this);
		otherPresent	= (CheckBox) view.findViewById(R.id.other_problem);
		otherPresent.setOnCheckedChangeListener(this);
		diseaseName		= (EditText) view.findViewById(R.id.disease_name);
		diseaseName.addTextChangedListener((InspectionActivity) getActivity());
		otherDescr		= (EditText) view.findViewById(R.id.other_descr);
		otherDescr.addTextChangedListener((InspectionActivity) getActivity());
		
		if(getArguments() != null) {
			Inspection inspection = LocalStore.findInspectionById(getArguments().getString(
					InspectionActivity.REQUEST_EXTRA_INSPECTION_ID));
			
			diseasePresent.setChecked(inspection.getParameters().disease.equals(new String()) ? false : true);
			diseaseName.requestFocus();
			diseaseName.setText(inspection.getParameters().disease);
			otherPresent.setChecked(inspection.getParameters().other.equals(new String()) ? false : true);
			otherDescr.requestFocus();
			otherDescr.setText(inspection.getParameters().other);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton button, boolean checked) {
		
		switch(button.getId()) {
		
		case R.id.disease_present:
			diseaseName.setEnabled(checked);		break;
			
		case R.id.other_problem:
			otherDescr.setEnabled(checked);			break;
			
		default:break;
		
		}
	}
	
}
