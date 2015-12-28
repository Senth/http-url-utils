package com.spiddekauga.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Builds HTTP GET and POST parameters.
 * @author Matteus Magnusson (matteus.magnusson@spiddekauga.com)
 */
class HttpParameterBuilder {
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
			if (text != null && text.length() > 0) {
				mBuilder.append("=").append(URLEncoder.encode(text.toString(), mCharset));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
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
	 * Set the charset to use. UTF-8 by default. Don't change this after calling
	 * addParameter(...)
	 * @param charset
	 */
	public void setCharset(String charset) {
		mCharset = charset;
	}

	/** Added parameter */
	protected boolean mAddedParameter = false;
	/** Charset the parameters are encoded into */
	protected String mCharset = "UTF-8"; // Only in Java 7+ StandardCharsets.UTF_8.name();
	/** The parameter builder */
	protected StringBuilder mBuilder = new StringBuilder();
}
