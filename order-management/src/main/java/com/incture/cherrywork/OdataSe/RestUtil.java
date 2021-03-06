package com.incture.cherrywork.OdataSe;



import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.incture.cherrywork.util.ServicesUtil;
import com.sap.core.connectivity.api.authentication.AuthenticationHeader;
import com.sap.core.connectivity.api.authentication.AuthenticationHeaderProvider;

@Component
public class RestUtil {

	/** The Constant logger. */

	// private static final Logger logger =
	// LoggerFactory.getLogger(RestUtil.class);

	/**
	 * 
	 * Call rest service.
	 *
	 * 
	 * 
	 * @param requestURL
	 *            the request URL
	 * 
	 * @param samlHeaderKey
	 *            - null
	 * 
	 * @param entity
	 *            - input entity for POST call
	 * 
	 * @param method
	 *            - POST/GET - PMCConstant.HTTP_METHOD_POST /
	 *            PMCConstant.HTTP_METHOD_GET / PMCConstant.HTTP_METHOD_PATCH
	 * 
	 * @param contentType
	 *            - Content Type - PMCConstant.APPLICATION_JSON /
	 *            PMCConstant.APPLICATION_XML etc.
	 * 
	 * @param isSaml
	 *            - true if trying to do SSO using SAML2.0
	 * 
	 * @param xCsrfToken
	 *            - "Fetch", if need to do a PATCH/POST operation and
	 *            x-csrf-token is required
	 * 
	 * @param userId
	 *            - userId for Basic Authentication
	 * 
	 * @param password
	 *            - password for Basic Authentication
	 * 
	 * @param accessToken
	 *            - Access token for other types of Authentications
	 * 
	 * @param tokenType
	 *            - Access token for other types of Authentications -
	 *            Bearer/Basic etc.
	 * 
	 * @return RestResponse with returnObject, responseCode, httpResponse
	 * 
	 */

	public HttpResponse callRestService(String requestURL, String samlHeaderKey, String entity, String method,

			String contentType, Boolean isSaml, String xCsrfToken, String userId, String password, String accessToken,

			String tokenType) {

		// RestResponse restResponse = null;

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		HttpRequestBase httpRequestBase = null;

		HttpResponse httpResponse = null;

		StringEntity input = null;

		String json = null;

		JSONObject obj = null;

		JSONArray array = null;

		Object returnJson = null;

		//AuthenticationHeader appToAppSSOHeader = null;

		if (requestURL != null) {

			// restResponse = new RestResponse();

//			if (isSaml) {
//
//				if (appToAppSSOHeader == null) {
//
//					appToAppSSOHeader = refreshAppToAppSSOHeader(requestURL);
//
//				}
//
//			}

			try {

				if (xCsrfToken != null && xCsrfToken.equalsIgnoreCase("fetch")) {

					httpRequestBase = new HttpGet(requestURL);

					httpRequestBase.setHeader("x-csrf-token", xCsrfToken);

//					if (appToAppSSOHeader != null) {
//
//						httpRequestBase.addHeader(appToAppSSOHeader.getName(), appToAppSSOHeader.getValue());
//
//					}

					httpResponse = httpClient.execute(httpRequestBase);

					Header[] headers = httpResponse.getAllHeaders();

					for (Header header : headers) {

						if (header.getName().equalsIgnoreCase("x-csrf-token")) {

							xCsrfToken = header.getValue();

						}

					}

				}

			} catch (IOException e) {

				System.err.println("Exception : " + e.getMessage());

			}

			if (method.equalsIgnoreCase("GET")) {

				httpRequestBase = new HttpGet(requestURL);

			} else if (method.equalsIgnoreCase("POST")) {

				httpRequestBase = new HttpPost(requestURL);

				if (entity != null) {

					try {

						input = new StringEntity(entity);

						input.setContentType(contentType);

					} catch (UnsupportedEncodingException e) {

					}

					((HttpPost) httpRequestBase).setEntity(input);

				}

			} else if (method.equalsIgnoreCase("PATCH")) {

				httpRequestBase = new HttpPatch(requestURL);

				if (!ServicesUtil.isEmpty(entity)) {

					try {

						input = new StringEntity(entity);

						input.setContentType(contentType);

					} catch (UnsupportedEncodingException e) {

					}

					((HttpPatch) httpRequestBase).setEntity(input);

				}

			}

//			if (appToAppSSOHeader != null) {
//
//				httpRequestBase.addHeader(appToAppSSOHeader.getName(), appToAppSSOHeader.getValue());
//
//			}

			if (!ServicesUtil.isEmpty(xCsrfToken) && !xCsrfToken.equalsIgnoreCase("fetch"))

				httpRequestBase.addHeader("x-csrf-token", xCsrfToken);

			httpRequestBase.addHeader("accept", contentType);

			if (!ServicesUtil.isEmpty(userId) && !ServicesUtil.isEmpty(password)) {

				httpRequestBase.addHeader("Authorization", ServicesUtil.getBasicAuth(userId, password));

			}

			if (!ServicesUtil.isEmpty(accessToken) && !ServicesUtil.isEmpty(tokenType)

					&& ServicesUtil.isEmpty(userId)) {

				httpRequestBase.addHeader("Authorization", ServicesUtil.getAuthorization(accessToken, tokenType));

			}

			try {

				httpResponse = httpClient.execute(httpRequestBase);

				System.err.println(httpResponse);

				if (!ServicesUtil.isEmpty(httpResponse) && !ServicesUtil.isEmpty(httpResponse.getEntity())) {

					json = EntityUtils.toString(httpResponse.getEntity());

					try {

						if (!ServicesUtil.isEmpty(json)) {

							returnJson = new Object[2];

							if (json.charAt(0) == '{') {

								obj = new JSONObject(json);

								returnJson = obj;

							} else if (json.charAt(0) == '[') {

								array = new JSONArray(json);

								returnJson = array;

							} else {

								returnJson = json;

								System.err.println("Error parsing Response : " + returnJson);

							}

							// restResponse.setResponseObject(returnJson);

						}

					} catch (JSONException e) {

					}

				}

				// restResponse.setHttpResponse(httpResponse);

				// restResponse.setResponseCode(httpResponse.getStatusLine().getStatusCode());

				httpClient.close();

			} catch (IOException e) {

			}

		}
		return httpResponse;

		// return restResponse;

	}

	/**
	 * 
	 * Refresh app to app SSO header.
	 *
	 * 
	 * 
	 * @param requestURL
	 *            the request URL
	 * 
	 * @return the authentication header
	 * 
	 */

//	private static AuthenticationHeader refreshAppToAppSSOHeader(String requestURL) {
//
//		Context ctx;
//
//		AuthenticationHeader appToAppSSOHeader = null;
//
//		AuthenticationHeaderProvider authHeaderProvider;
//
//		try {
//
//			ctx = new InitialContext();
//
//			authHeaderProvider = (AuthenticationHeaderProvider) ctx.lookup("java:comp/env/myAuthHeaderProvider");
//
//			appToAppSSOHeader = authHeaderProvider.getAppToAppSSOHeader(requestURL);
//
//		} catch (Exception ex) {
//
//		}
//
//		return appToAppSSOHeader;
//
//	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * 
	 * String APPROVE_TASK =
	 * "https://bpmworkflowruntimecbbe88bff-uk81qreeol.ap1.hana.ondemand.com/workflow-service/rest/v1/task-instances/36762911-b9c5-11e9-b287-00163e9142b0";
	 * 
	 * 
	 * 
	 * String token = "";
	 * 
	 * try { RestUtil.callRestService(APPROVE_TASK, "samlHeaderKeyTI",
	 * 
	 * "" , "PATCH", "application/json", true, "Fetch", null, null, null, null);
	 * 
	 * 
	 * } catch (Exception e) {
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * // return response;
	 * 
	 * }
	 */
}

