package edu.mit.discoverme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;



public class Authenticate {
	public static Boolean check(String username, String password) {
		String usr = username;
		String pass = password;

		// call authentication webpage and get resposne true or false

		return true;
	}

	public static CharSequence getURLContent(URL url) throws IOException {
		URLConnection conn = url.openConnection();
		String encoding = conn.getContentEncoding();
		if (encoding == null) {
			encoding = "ISO-8859-1";
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), encoding));
		StringBuilder sb = new StringBuilder(16384);
		try {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
		} finally {
			br.close();
		}
		return sb;
	}

}
