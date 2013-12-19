package nl.isld.databees;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ApiaryAdapter extends ArrayAdapter<Apiary>
	implements OnCheckedChangeListener {
	
	private static final int RESOURCE_ID = R.layout.list_view_item;
	
	/*
	 * Constructor of custom adapter ApiaryAdapter.
	 * @param	context		the context of the application
	 * @param	data		the data (usually an ArrayList) for the adapter
	 * 						to handle
	 * @return	a new instance of ApiaryAdapter
	 */
	public ApiaryAdapter(Context context, List<Apiary> data) {
		super(context, RESOURCE_ID, data);
		Log.d("Debug", "[ApiaryAdapter new]");
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("Debug", "[ApiaryAdapter getView]");
		Context context = parent.getContext();
		
		View view = convertView;
		
			if(position == (getCount() - 1)) {
				Log.d("Debug", "Inflating the last item on the list");
				view = LayoutInflater.from(context).inflate(R.layout.adapter_last_item, null);
				
				TextView addItemTextView = (TextView) view.findViewById(R.id.add_item_text_view);
				addItemTextView.setText(context.getString(R.string.action_add_new_apiary));
			}
			else {
				view = LayoutInflater.from(context).inflate(RESOURCE_ID, null);
				
				TextView name = (TextView) view.findViewById(R.id.text1);
				name.setText(getItem(position).getName());
				
				TextView notes = (TextView) view.findViewById(R.id.text2);
				notes.setText(getItem(position).getNotes());
				
				/*
				CheckBox checkbox = (CheckBox) view.findViewById(R.id.check_box);
				checkbox.setOnCheckedChangeListener(this);
				*/
			}
			
			return view;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return (super.getCount() + 1);
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged(android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
	}

}
