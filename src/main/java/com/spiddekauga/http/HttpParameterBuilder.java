package com.spiddekauga.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds HTTP GET and POST parameters.
 */
class HttpParameterBuilder {
private static Map<String, String> mDefaultRequestProperties = new HashMap<>();
/** Added parameter */
protected boolean mAddedParameter = false;
/** Charset the parameters are encoded into */
protected String mCharset = "UTF-8"; // Only in Java 7+ StandardCharsets.UTF_8.name();
/** The parameter builder */
protected StringBuilder mBuilder = new StringBuilder();

/**
 * Add a default request property. For all new {@link HttpPostBuilder} and {@link HttpGetBuilder}
 * these request properties will be set by default. Empty by default
 * @param key For example "Accept-Charset"
 * @param value For example "UTF-8"
 */
public static void addDefaultRequestProperty(String key, String value) {
	mDefaultRequestProperties.put(key, value);
}

/**
 * Remove a default request property. For all new {@link HttpPostBuilder} and {@link HttpGetBuilder}
 * these request properties will be set by default. Does nothing if the key wasn't set. Empty by default.
 * @param key the property to remove. E.g. "Accept-Charset"
 */
public static void removeDefaultRequestProperty(String key) {
	mDefaultRequestProperties.remove(key);
}

/**
 * Remove/Clear all default request properties. Empty by default. Used by {@link HttpPostBuilder} and {@link HttpGetBuilder},
 * as these properties will be set by default for all new instances.
 */
public static void clearDefaultRequestProperties() {
	mDefaultRequestProperties.clear();
}

/**
 * Set all default request properties for the specified connection
 * @param connection the connection to set all request properties
 */
protected static void setDefaultRequestProperties(HttpURLConnection connection) {
	for (Map.Entry<String, String> entry : mDefaultRequestProperties.entrySet()) {
		connection.setRequestProperty(entry.getKey(), entry.getValue());
	}
}

/**
 * Add a parameter to the request
 * @param name field name
 * @throws IOException
 */
public void addParameter(String name) throws IOException {
	addParameter(name, (CharSequence) null);
}

/**
 * Add a parameter to the request
 * @param name field name
 * @param text the value of the parameter (can be null)
 * @throws IOException
 */
public void addParameter(String name, CharSequence text) throws IOException {
	addSeparator();
	try {
		mBuilder.append(URLEncoder.encode(name, mCharset));
		mBuilder.append("=");
		if (text != null && text.length() > 0) {
			mBuilder.append(URLEncoder.encode(text.toString(), mCharset));
		}
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}
}

/**
 * Adds a separator between URL and parameter or parameters.
 */
protected void addSeparator() {
	if (mAddedParameter) {
		mBuilder.append("&");
	} else {
		mAddedParameter = true;
	}
}

/**
 * Add a parameter to the request
 * @param name field name
 * @param text the value of the parameter (can be null)
 * @throws IOException
 */
public void addParameter(String name, char[] text) throws IOException {
	addParameter(name, new String(text));
}

/**
 * Add a parameter to the request
 * @param name field name
 * @param number the value of the parameter (can be null)
 * @throws IOException
 */
public void addParameter(String name, Number number) throws IOException {
	addParameter(name, String.valueOf(number));
}

/**
 * Add binary parameter.
 * @param name field name
 * @param array binary array. Will be encoded as a string and then URL-encoded.
 * @throws IOException
 */
public void addParameter(String name, byte[] array) throws IOException {
	String byteString = new String(array);
	addParameter(name, byteString);
}

/**
 * Set the charset to use. UTF-8 by default. Don't change this after calling
 * addParameter(...)
 * @param charset
 */
public void setCharset(String charset) {
	mCharset = charset;
}
}
