package nl.isld.databeess.disease;

import nl.isld.databees.R;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class European extends Fragment {
	
	public European(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.disease_european, container, false);
		TextView txt = (TextView) rootView.findViewById(R.id.textView1);
		txt.setText(Html.fromHtml(getString(R.string.european)));
        return rootView;
    }
}
