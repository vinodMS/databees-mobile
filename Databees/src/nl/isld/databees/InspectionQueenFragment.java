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
