
package com.jfixby.scarabei.red.desktop.test;

import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.desktop.DesktopSetup;
import com.jfixby.scarabei.api.log.L;

public class ErrTest {

	public static void main (final String[] args) {
		DesktopSetup.deploy();

		L.e("some problem", new Error("some problem stack"));

		Debug.checkNull("param", null);
	}

}