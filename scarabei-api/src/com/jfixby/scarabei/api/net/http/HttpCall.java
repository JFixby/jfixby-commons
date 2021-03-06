
package com.jfixby.scarabei.api.net.http;

import javax.net.ssl.SSLSocketFactory;

import com.jfixby.scarabei.api.collections.Mapping;

public interface HttpCall {

	void addRequestHeader (String parameter_name, String value);

	HttpURL getUrl ();

	boolean getUseSSL ();

	boolean getUseAgent ();

	void addRequestHeaders (Mapping<String, String> headers);

	Mapping<String, String> listRequestHeaders ();

	long getServerTimeout ();

	public void setServerTimeout (long timeout);

	SSLSocketFactory getSSLFactory ();

}
