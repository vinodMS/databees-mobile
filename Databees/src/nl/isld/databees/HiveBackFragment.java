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
