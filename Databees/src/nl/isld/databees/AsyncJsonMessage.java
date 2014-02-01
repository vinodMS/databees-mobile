package nl.isld.databees;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncJsonMessage extends AsyncTask<Void, Void, JSONObject> {
	
	public enum HttpMethod { GET, POST };
	
	protected String			url			= new String();
	protected JSONObject		content		= new JSONObject();
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
	
	public final AsyncJsonMessage setContent(JSONObject content) {
		this.content = content;
		return this;
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
			break;
			
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
		
		DefaultHttpClient httpClient	= new DefaultHttpClient();
        HttpPost httpPost 				= new HttpPost(url);
        InputStream is;
		
		// Try to execute the HTTP request and get the response
		try {
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
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		HttpParams params = new BasicHttpParams();
		SingleClientConnManager mgr = new SingleClientConnManager(params, schemeRegistry);
		HttpClient client = new DefaultHttpClient(mgr, params);
		  
		HttpPost post = new HttpPost(url);
		post.addHeader("content-type", "application/json");
		post.addHeader("Authorization", BackendController.API_KEY);
		Log.d("Beebug", "Content " + content.toString());
		try {
			post.setEntity(new StringEntity(content.toString()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		InputStream is;
		  
		try {
			HttpResponse response 		= client.execute(post);
			HttpEntity httpEntity		= response.getEntity();
									 is = httpEntity.getContent();   
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
	
	private JSONObject executeSecureGet() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("https",SSLSocketFactory.getSocketFactory(), 443));
		HttpParams params = new BasicHttpParams(); 
		SingleClientConnManager mgr = new SingleClientConnManager(params, schemeRegistry);
		HttpClient client = new DefaultHttpClient(mgr, params);
		
		InputStream is;
		  
		try {
			HttpResponse response 		= client.execute(new HttpGet(url));
			HttpEntity httpEntity		= response.getEntity();
									 is = httpEntity.getContent(); 
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
}
