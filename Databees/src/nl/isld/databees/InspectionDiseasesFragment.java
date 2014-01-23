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
