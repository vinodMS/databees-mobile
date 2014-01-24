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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;

public class AsyncJsonMessage extends AsyncTask<Void, Void, JSONObject> {
	
	public enum HttpMethod { GET, POST };
	
	protected String			url			= new String();
	protected JSONObject		object		= new JSONObject();
	protected ResponseRecipient	recipient;
	
	protected HttpMethod		method;
	protected boolean			secure;
	
	/*
	 * This method can be called once an instance of this
	 * class has been created. Internally, it executes an
	 * asynchronous task to fetch the JSON file and convert
	 * it to a JSONObject.
	 * @param	url		the URL from where to fetch the
	 * 					JSON file
	 */
	public final void viaHttpRequest(String url, ResponseRecipient recipient,
			boolean secure, HttpMethod method) {
		this.url 		= url;
		this.recipient	= recipient;
		this.method		= method;
		this.secure		= secure;
		execute((Void) null);
	}
	
	/*
	 * Overridden method of class AsyncTask.
	 * It calls the private method fetchAndParse()
	 * asynchronously. No arguments are necessary.
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected final JSONObject doInBackground(Void...voids) {
		JSONObject json;
		
		switch(method) {
		
		case POST:
			json = secure ? 
					executeSecurePost() : executeSecurelessPost();
			break;
			
		case GET:
			json = executeSecureGet();
			
		default:
			json = new JSONObject();
			break;
		}
		
		return json;
	}
	
	@Override
	protected final void onPostExecute(JSONObject json) {
		if(recipient != null) {
			recipient.onResponseReceived(json);
		}
	}
	
	/*
	 * This method shall only be called asynchronously by
	 * doInBackground(). First it send an HTTP request, it
	 * processes the response and it creates the JSON Object.
	 * Finally it calls the callback method passing the object
	 * that was just created.
	 */
	private JSONObject executeSecurelessPost() {
		
		if(url == null) {
			return new JSONObject();
		}
		
		InputStream is;
		
		// Try to execute the HTTP request and get the response
		try {
            DefaultHttpClient httpClient	= new DefaultHttpClient();
            HttpPost httpPost 				= new HttpPost(url);
            HttpResponse httpResponse		= httpClient.execute(httpPost);
            HttpEntity httpEntity			= httpResponse.getEntity();
            							 is = httpEntity.getContent();           
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new JSONObject();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return new JSONObject();
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONObject();
        }
        
		// Once we have a response, try to convert it to a JSON object
        try {
            BufferedReader reader 	= new BufferedReader(new InputStreamReader(is), 8);
            StringBuilder sb		= new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            
            is.close();
            return new JSONObject(sb.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
	}
	
	private JSONObject executeSecurePost() {
		// TODO
		return null;
	}
	
	private JSONObject executeSecureGet() {
		// TODO
		return null;
	}
}
