
package com.incture.cherrywork.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class DestinationReaderUtil {

	public static Map<String, Object> getDestination(String destinationName) throws URISyntaxException, IOException {

		System.err.println("getDestination started..");
		HttpClient client = HttpClientBuilder.create().build();

		HttpPost httpPost = new HttpPost(ComConstants.DESTINATION_TOKEN_URL);
		httpPost.addHeader("Content-Type", "application/json");

		System.err.println("httpPost in get Destination"+httpPost);
		// Encoding username and password
		String auth = HelperClass.encodeUsernameAndPassword(ComConstants.DESTINATION_CLIENT_ID,
				ComConstants.DESTINATION_CLIENT_SECRET);
		httpPost.addHeader("Authorization", auth);

		HttpResponse res = client.execute(httpPost);
		
		System.err.println("res in get destination"+res);

		String data = HelperClass.getDataFromStream(res.getEntity().getContent());
		System.err.println("data in getDestination "+data);
		if (res.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
			System.err.println("in get destination for loop");
			String jwtToken = new JSONObject(data).getString("access_token");
			System.err.println("in get destination jwtToken "+jwtToken);

			HttpGet httpGet = new HttpGet(ComConstants.DESTINATION_BASE_URL
					+ "/destination-configuration/v1/destinations/" + destinationName);
			System.err.println("in get destination httpGet"+httpGet);

			httpGet.addHeader("Content-Type", "application/json");

			httpGet.addHeader("Authorization", "Bearer " + jwtToken);

			HttpResponse response = null;
			try{
				response = client.execute(httpGet);
			}
			catch(Exception e){
				System.err.println("Exception in executing client");
				e.printStackTrace();
			}
			String dataFromStream = HelperClass.getDataFromStream(response.getEntity().getContent());
			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {

				System.err.println("in get Destination response status is 200");
				JSONObject json = new JSONObject(dataFromStream);
				return json.getJSONObject("destinationConfiguration").toMap();

			}
			System.err.println("in get Destination res status is 200");
		}

		return null;
	}

	public static String getConectivityProxy() throws URISyntaxException, IOException {

		System.err.println("77 destination");
		HttpClient client = HttpClientBuilder.create().build();

		HttpPost httpPost = new HttpPost(ComConstants.DESTINATION_TOKEN_URL);
		httpPost.addHeader("Content-Type", "application/json");

		// Encoding username and password
		String auth = HelperClass.encodeUsernameAndPassword(ComConstants.CONECTIVITY_CLIENT_ID,
				ComConstants.CONECTIVITY_CLIENT_SECRET);

		httpPost.addHeader("Authorization", auth);

		HttpResponse res = client.execute(httpPost);

		System.err.println(" 92 rest" + res);

		String data = HelperClass.getDataFromStream(res.getEntity().getContent());
		if (res.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
			String jwtToken = new JSONObject(data).getString("access_token");

			System.err.println("jwtProxyToken " + jwtToken);

			return jwtToken;

		}

		return null;
	}

	public static String getJwtTokenForAuthenticationForSapApi() throws URISyntaxException, IOException {
		System.err.println("77 destination");
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(ComConstants.OAUTH_TOKEN_URL);
		httpPost.addHeader("Content-Type", "application/json");
		// Encoding username and password
		String auth = HelperClass.encodeUsernameAndPassword(ComConstants.WORKFLOW_CLIENT_ID,
				ComConstants.WORKFLOW_CLIENT_SECRETE);
		httpPost.addHeader("Authorization", auth);
		HttpResponse res = client.execute(httpPost);
		System.err.println(" 92 rest" + res);
		String data = HelperClass.getDataFromStream(res.getEntity().getContent());
		if (res.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
			String jwtToken = new JSONObject(data).getString("access_token");
			System.err.println("jwtProxyToken " + jwtToken);
			return jwtToken;
		}
		return null;
	}
}
//=======
//package com.incture.cherrywork.util;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.util.Map;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.json.JSONObject;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DestinationReaderUtil {
//
//	public static Map<String, Object> getDestination(String destinationName) throws URISyntaxException, IOException {
//
//		System.err.println("getDestination started..");
//		HttpClient client = HttpClientBuilder.create().build();
//
//		HttpPost httpPost = new HttpPost(ComConstants.DESTINATION_TOKEN_URL);
//		httpPost.addHeader("Content-Type", "application/json");
//
//		System.err.println("httpPost in get Destination"+httpPost);
//		// Encoding username and password
//		String auth = HelperClass.encodeUsernameAndPassword(ComConstants.DESTINATION_CLIENT_ID,
//				ComConstants.DESTINATION_CLIENT_SECRET);
//		httpPost.addHeader("Authorization", auth);
//
//		HttpResponse res = client.execute(httpPost);
//		
//		System.err.println("res in get destination"+res);
//
//		String data = HelperClass.getDataFromStream(res.getEntity().getContent());
//		if (res.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
//			String jwtToken = new JSONObject(data).getString("access_token");
//
//			HttpGet httpGet = new HttpGet(ComConstants.DESTINATION_BASE_URL
//					+ "destination-configuration/v1/destinations/" + destinationName);
//
//			httpGet.addHeader("Content-Type", "application/json");
//
//			httpGet.addHeader("Authorization", "Bearer " + jwtToken);
//
//			HttpResponse response = client.execute(httpGet);
//			String dataFromStream = HelperClass.getDataFromStream(response.getEntity().getContent());
//			if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
//
//				System.err.println("in get Destination response status is 200");
//				JSONObject json = new JSONObject(dataFromStream);
//				return json.getJSONObject("destinationConfiguration").toMap();
//
//			}
//			System.err.println("in get Destination res status is 200");
//		}
//
//		return null;
//	}
//	
//	public static String getConectivityProxy() throws URISyntaxException, IOException {
//
//		System.err.println("77 destination");
//		HttpClient client = HttpClientBuilder.create().build();
//
//		HttpPost httpPost = new HttpPost(ComConstants.DESTINATION_TOKEN_URL);
//		httpPost.addHeader("Content-Type", "application/json");
//
//		// Encoding username and password
//		String auth = HelperClass.encodeUsernameAndPassword(ComConstants.CONECTIVITY_CLIENT_ID,
//				ComConstants.CONECTIVITY_CLIENT_SECRET);
//		
//		
//		httpPost.addHeader("Authorization", auth);
//
//		HttpResponse res = client.execute(httpPost);
//		
//		System.err.println( " 92 rest" + res);
//
//		String data = HelperClass.getDataFromStream(res.getEntity().getContent());
//		if (res.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
//			String jwtToken = new JSONObject(data).getString("access_token");
//			
//			System.err.println("jwtProxyToken "+jwtToken);
//
//			
//				return jwtToken;
//
//			}
//		
//		
//
//		return null;
//	}
//	public static String getJwtTokenForAuthenticationForSapApi() throws URISyntaxException, IOException {
//		System.err.println("77 destination");
//		HttpClient client = HttpClientBuilder.create().build();
//		HttpPost httpPost = new HttpPost(ComConstants.OAUTH_TOKEN_URL);
//		httpPost.addHeader("Content-Type", "application/json");
//		// Encoding username and password
//		String auth = HelperClass.encodeUsernameAndPassword(ComConstants.WORKFLOW_CLIENT_ID,
//				ComConstants.WORKFLOW_CLIENT_SECRETE);
//		httpPost.addHeader("Authorization", auth);
//		HttpResponse res = client.execute(httpPost);
//		System.err.println( " 92 rest" + res);
//		String data = HelperClass.getDataFromStream(res.getEntity().getContent());
//		if (res.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
//			String jwtToken = new JSONObject(data).getString("access_token");
//			System.err.println("jwtProxyToken "+jwtToken);
//				return jwtToken;
//			}
//		return null;
//	}
//}
//>>>>>>> 7d779a97118c12d1811378be9f7c83fdeaf836f0
