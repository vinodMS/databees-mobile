package nl.isld.databees;

import java.text.DateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class InspectionAdapter extends ArrayAdapter<Inspection> {
	
	private static final int RESOURCE_ID = R.layout.list_view_item;
	
	/*
	 * Constructor of custom adapter HiveAdapter.
	 * @param	context		the context of the application
	 * @param	data		the data (usually an ArrayList) for the adapter
	 * 						to handle
	 * @return	a new instance of HiveAdapter
	 */
	public InspectionAdapter(Context context, List<Inspection> data) {
		super(context, RESOURCE_ID, data);
	}
	
	/*
	 * Overridden method of class ArrayAdapter<T>.
	 * We construct the view for each item in the list.
	 * The layout for each item is declared in the file
	 * id passed to the RESOURCE_ID member constant. The
	 * last item in the list serves as an action to add a
	 * new inspection, thus its view is different from the
	 * rest.
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Context context = parent.getContext();
		View view = convertView;
		
		if(position == (getCount() - 1)) {
			view = LayoutInflater.from(context).inflate(R.layout.adapter_last_item, null);
			
			TextView addItemTextView = (TextView) view.findViewById(R.id.add_item_text_view);
			addItemTextView.setText(context.getString(R.string.action_add_new_inspection));
		}
		else {
			view = LayoutInflater.from(context).inflate(RESOURCE_ID, null);
			
			TextView date = (TextView) view.findViewById(R.id.text1);
			date.setText(DateFormat.getInstance().format(getItem(position).getDate()));
			
			TextView notes = (TextView) view.findViewById(R.id.text2);
			notes.setText(getItem(position).getNotes());
		}
		
		return view;
	}
	
	/*
	 * Overridden method of class ArrayAdapter<T>.
	 * It returns the count of the items in the List<Apiary>,
	 * plus one to include the special item placed at the end
	 * of the ListView.
	 * @see android.widget.ArrayAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return (super.getCount() + 1);
	}

}
