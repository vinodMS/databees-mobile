package nl.isld.databees;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class InspectionGeneralFragment extends Fragment
	implements OnClickListener {
	
	private EditText	dayInput;
	private EditText	monthInput;
	private EditText	yearInput;
	private EditText	notesInput;
	private Spinner		temperInput;
	private CheckBox	eggsInput;
	private CheckBox	polenInput;
	
	private Button		buttonNow;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_inspection_general, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		dayInput	= (EditText) view.findViewById(R.id.day_input);
		dayInput.addTextChangedListener((InspectionActivity) getActivity());
		monthInput	= (EditText) view.findViewById(R.id.month_input);
		monthInput.addTextChangedListener((InspectionActivity) getActivity());
		yearInput	= (EditText) view.findViewById(R.id.year_input);
		yearInput.addTextChangedListener((InspectionActivity) getActivity());
		buttonNow	= (Button) view.findViewById(R.id.now_button);
		buttonNow.setOnClickListener(this);
		notesInput	= (EditText) getView().findViewById(R.id.notes_input);
		notesInput.addTextChangedListener((InspectionActivity) getActivity());
		temperInput	= (Spinner)  getView().findViewById(R.id.temper_input);
		temperInput.setOnItemSelectedListener((InspectionActivity) getActivity());
		eggsInput	= (CheckBox) getView().findViewById(R.id.eggs_present_input);
		eggsInput.setOnCheckedChangeListener((InspectionActivity) getActivity());
		polenInput	= (CheckBox) getView().findViewById(R.id.polen_in_input);
		polenInput.setOnCheckedChangeListener((InspectionActivity) getActivity());
		
		if(getArguments() != null) {
			Inspection inspection = LocalStore.findInspectionById(getArguments().getString(
					InspectionActivity.REQUEST_EXTRA_INSPECTION_ID));
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(inspection.getDate());
			
			dayInput.requestFocus();
			dayInput.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
			monthInput.requestFocus();
			monthInput.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1));
			yearInput.requestFocus();
			yearInput.setText(String.valueOf(calendar.get(Calendar.YEAR)));
			temperInput.setSelection(inspection.getParameters().temper.ordinal());
			eggsInput.setChecked(inspection.getParameters().eggs);
			polenInput.setChecked(inspection.getParameters().polen);
			notesInput.requestFocus();
			notesInput.setText(inspection.getNotes());
		}
	}

	@Override
	public void onClick(View view) {
		Calendar calendar = Calendar.getInstance();
		
		dayInput.requestFocus();
		dayInput.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
		monthInput.requestFocus();
		monthInput.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1));
		yearInput.requestFocus();
		yearInput.setText(String.valueOf(calendar.get(Calendar.YEAR)));
	}
	
}
