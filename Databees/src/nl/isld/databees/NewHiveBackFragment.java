package nl.isld.databees;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewHiveBackFragment extends Fragment {
	
	/*
	 * Overridden method of class Fragment.
	 * It creates the fragment view and returns it to the
	 * activity that uses the fragment.
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_new_hive_back, container, false);

		return view;
	}

}
