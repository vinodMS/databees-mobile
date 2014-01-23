package nl.isld.databees;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class UserExpandableAdapter extends BaseExpandableListAdapter {
	
	private Context 						context;
	private String							userInfoParentItem;
	private String[]						userOptionsChildItems;
	
	
	public UserExpandableAdapter(Context context, String userInfoParentItem,
            String[] userOptionsChildItems) {
        this.context				= context;
        this.userInfoParentItem		= userInfoParentItem;
        this.userOptionsChildItems	= userOptionsChildItems;
    }

	@Override
	public Object getChild(int parentPos, int childPos) {
		return this.userOptionsChildItems[childPos];
	}

	@Override
	public long getChildId(int parentPos, int childPos) {
		return childPos;
	}

	@Override
	public View getChildView(int parentPos, int childPos, boolean isLastChildItem,
			View convertView, ViewGroup rootView) {
		
		if(convertView == null) {
			LayoutInflater layoutInflater = 
					(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.adapter_nav_drawer_user_account_options, null);
		}
		
		TextView childItemTextView = (TextView) convertView.findViewById(R.id.child_item_text_view);
		childItemTextView.setText((String) this.getChild(parentPos, childPos));
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int parentPos) {
		return 2;
	}

	@Override
	public Object getGroup(int parentPos) {
		return userInfoParentItem;
	}

	@Override
	public int getGroupCount() {
		return 1;
	}

	@Override
	public long getGroupId(int parentPos) {
		return 0;
	}

	@Override
	public View getGroupView(int parentPos, boolean isLastParentItem,
			View convertView, ViewGroup rootView) {
		
		if(convertView == null) {
			LayoutInflater layoutInflater = 
					(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.adapter_nav_drawer_user_account, null);
		}
		
		TextView userInfoTextView = (TextView) convertView.findViewById(R.id.user_info_text_view);
		userInfoTextView.setText(this.userInfoParentItem);
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int parentPos, int childPos) {
		return true;
	}

}
