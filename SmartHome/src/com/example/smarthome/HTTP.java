package com.example.smarthome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;


import android.util.Log;
import android.widget.Toast;

public class HTTP {

	static InputStream is = null;

	// Hàm xây dựng khởi tạo đối tượng
	public HTTP() {
		
	}

	public String getPUSHfromUrl(String url, StringEntity params, int timeout) {
		// Cố gắng thực hiện gửi yêu cầu lên HTTP
		String result = "";
		Log.v("result",params.toString());
		try {
			HttpParams httpParameters = new BasicHttpParams();
			int timeoutConnection = timeout;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			int timeoutSocket = 1000;//ms
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.addHeader("Authorization", "key=AIzaSyCm8kijmtm4WYetjwQo4nBMwElDXsbpTqE");
			httpPost.setEntity(params);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			// Da gui di va dang cho nhan ve
			HttpEntity httpEntity = httpResponse.getEntity();
			// Da nhan lai data -> di phan tich
			is = httpEntity.getContent();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.v("result", result);
		} catch (Exception e) {
			Log.e("Buffer Error", result);
		}
		
		return result;
	}
	
	
	public String getGETfromUrl(String url) {
		String result = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {// lay giu lieu ve httpEntity
			HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();
			if (httpEntity != null) {
				InputStream inputStream = httpEntity.getContent();
				Reader in = new InputStreamReader(inputStream);
				BufferedReader bufferedreader = new BufferedReader(in);
				StringBuilder stringBuilder = new StringBuilder();
				String stringReadLine = null;
				while ((stringReadLine = bufferedreader.readLine()) != null) {
					stringBuilder.append(stringReadLine + "\n");
				}
				result = stringBuilder.toString();
			} else {
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
		return result;
	}
}
