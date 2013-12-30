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

public class JsonParser extends AsyncTask<Void, Void, JSONObject> {
	
	protected String				url		= null;
	protected JSONObject			object	= new JSONObject();
	protected JsonParserCallback	callback;
	
	/*
	 * This method can be called once an instance of this
	 * class has been created. Internally, it executes an
	 * asynchronous task to fetch the JSON file and convert
	 * it to a JSONObject.
	 * @param	url		the URL from where to fetch the
	 * 					JSON file
	 */
	public final void parse(String url, JsonParserCallback callback) {
		this.url 		= url;
		this.callback	= callback;
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
		return fetchAndParse();
	}
	
	@Override
	protected final void onPostExecute(JSONObject json) {
		callback.onParsed(json);
	}
	
	/*
	 * This method shall only be called asynchronously by
	 * doInBackground(). First it send an HTTP request, it
	 * processes the response and it creates the JSON Object.
	 * Finally it calls the callback method passing the object
	 * that was just created.
	 */
	private JSONObject fetchAndParse() {
		
		if(url == null) {
			return null;
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
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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
            return null;
        }
	}
	
}
