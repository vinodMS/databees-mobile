package nl.isld.databees;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class InspectionGeneralFragment extends Fragment
	implements OnClickListener {
	
	EditText	dayInput;
	EditText	monthInput;
	EditText	yearInput;
	
	Button		buttonNow;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_inspection_general, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		dayInput	= (EditText) view.findViewById(R.id.day_input);
		monthInput	= (EditText) view.findViewById(R.id.month_input);
		yearInput	= (EditText) view.findViewById(R.id.year_input);
		
		buttonNow	= (Button) view.findViewById(R.id.now_button);
		buttonNow.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		Calendar calendar = Calendar.getInstance();
		
		dayInput.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
		monthInput.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1));
		yearInput.setText(String.valueOf(calendar.get(Calendar.YEAR)));
	}
	
}
