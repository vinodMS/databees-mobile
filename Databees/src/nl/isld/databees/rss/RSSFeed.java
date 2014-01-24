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

package nl.isld.databees.rss;

import java.util.ArrayList;

import java.util.HashMap;

import nl.isld.databees.MainActivity;
import nl.isld.databees.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class RSSFeed extends Activity
	implements XMLParser.Callback {
	// All static variables
	static final String URL = "http://bijenhouders.nl/rss/nieuws";
	// XML node keys
	static final String Item = "item"; // parent node
	static final String Title = "title";
	static final String Description = "description";
	static final String PublishDate = "pubDate";
	static final String Link = "link";
	static final String ImageURL = "url";
	
	ListView list;
    LazyAdapter adapter;
    NodeList n1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rss);

    	getActionBar().setDisplayShowCustomEnabled(true);
    	getActionBar().setDisplayHomeAsUpEnabled(true);
    	getActionBar().setHomeButtonEnabled(true);
		
    	if (isOnline() == true) {
    		XMLParser parser = new XMLParser(URL, this);
    		parser.execute((Void) null);
    	}
    	else {
    		Toast.makeText(getApplicationContext(), "You are not connected to the internet", Toast.LENGTH_LONG).show();
    	Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    	}
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent intent = new Intent(this, MainActivity.class);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onXMLParsed(String xml) {
		Log.d("Beebug", "XML PARSED");
		Document doc = XMLParser.getDomElement(xml);
		final ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
		
		final NodeList nl = doc.getElementsByTagName(Item);
		// looping through all song nodes <song>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(Link, XMLParser.getValue(e, Link));
			map.put(Title, XMLParser.getValue(e, Title));
			map.put(Description, XMLParser.getValue(e, Description));
			map.put(PublishDate, XMLParser.getValue(e, PublishDate));
			map.put(ImageURL, XMLParser.getValue(e, ImageURL));
			
			// adding HashList to ArrayList
			myList.add(map);
		}		
		
		list = (ListView)findViewById(R.id.list);
		
		// Getting adapter by passing xml data ArrayList
        adapter = new LazyAdapter(this, myList);        
        list.setAdapter(adapter);
        

        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				for(int i = 0; i < myList.size(); i++) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myList.get(position).get(Link)));
					startActivity(browserIntent);
					break;
				}
			}
		});		
	}
	
	public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent); 
        return;
    }   
}