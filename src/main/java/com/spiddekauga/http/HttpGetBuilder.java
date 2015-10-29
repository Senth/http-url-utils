package com.spiddekauga.http;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Create a new connection as a GET request
 * @author Matteus Magnusson <matteus.magnusson@spiddekauga.com>
 */
public class HttpGetBuilder extends HttpParameterBuilder {
	/**
	 * Create a new connection as a GET request
	 * @param url string to parse as URL
	 */
	public HttpGetBuilder(String url) {
		// Remove & or ? if it's at the end
		String fixedUrl = url;
		int length = url.length();
		if (length > 1 && url.lastIndexOf("?") == length || url.lastIndexOf("&") == length) {
			fixedUrl = url.substring(0, length - 1);
		}
		mBuilder.append(url);


		mAddedParameter = (fixedUrl.indexOf("?") != -1);
	}

	/**
	 * Adds a separator between URL and parameter or parameters.
	 */
	@Override
	protected void addSeparator() {
		if (mAddedParameter) {
			mBuilder.append("&");
		} else {
			mBuilder.append("?");
			mAddedParameter = true;
		}
	}

	/**
	 * Build the connection
	 * @return URLConnection with a GET request set
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public URLConnection build() throws MalformedURLException, IOException {
		URLConnection connection = new URL(mBuilder.toString()).openConnection();
		connection.setRequestProperty("Accept-Charset", mCharset);
		return connection;
	}

	/**
	 * Export to a HTTP Post Builder.
	 * @return converted Post Builder. The URL still contains the GET parameters added.
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public HttpPostBuilder toPostBuilder() throws MalformedURLException, IOException {
		return new HttpPostBuilder(this);
	}
}
