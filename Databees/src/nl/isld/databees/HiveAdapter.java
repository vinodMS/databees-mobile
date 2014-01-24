/*
	Databees a beekeeping organizer app.
    Copyright (C) 2014 NBV (Nederlandse Bijenhouders Vereniging)
    http://www.bijenhouders.nl/

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package nl.isld.databees;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HiveAdapter extends ArrayAdapter<Hive> {
	
	private static final int RESOURCE_ID = R.layout.list_view_item;
	
	/*
	 * Constructor of custom adapter HiveAdapter.
	 * @param	context		the context of the application
	 * @param	data		the data (usually an ArrayList) for the adapter
	 * 						to handle
	 * @return	a new instance of HiveAdapter
	 */
	public HiveAdapter(Context context, List<Hive> data) {
		super(context, RESOURCE_ID, data);
	}
	
	/*
	 * Overridden method of class ArrayAdapter<T>.
	 * We construct the view for each item in the list.
	 * The layout for each item is declared in the file
	 * id passed to the RESOURCE_ID member constant. The
	 * last item in the list serves as an action to add a
	 * new apiary, thus its view is different from the
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
			addItemTextView.setText(context.getString(R.string.action_add_new_hive));
		}
		else {
			view = LayoutInflater.from(context).inflate(RESOURCE_ID, null);
			
			TextView name = (TextView) view.findViewById(R.id.text1);
			name.setText(getItem(position).getName());
			
			TextView notes = (TextView) view.findViewById(R.id.text2);
			if(this.getItem(position).getInspections().size() > 0) {
				notes.setText(context.getResources().getString(R.string.last_inspected_on) + " " +
						new SimpleDateFormat("dd/MM/yyyy").format(getItem(position).getLastInspectionDate()));
			} else {
				notes.setText(context.getResources().getString(R.string.hive_list_item_never_inspected));
			}
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
