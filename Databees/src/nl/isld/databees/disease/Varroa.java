package nl.isld.databees.disease;

import nl.isld.databees.MainActivity;

import nl.isld.databees.R;
import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Varroa extends Fragment {
		
	public Varroa(){}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        
		View rootView = inflater.inflate(R.layout.disease_varroa, container, false);
        TextView txt = (TextView) rootView.findViewById(R.id.textView1);
		txt.setText(Html.fromHtml(getString(R.string.varroa)));
        return rootView;
    }
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent intent = new Intent(getActivity(), MainActivity.class);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
