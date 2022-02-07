package org.nci.nih.gov.tld.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResolverUtil {

	public static String WHOIS_SERV = "WHOIS_SERVER";
	public static String WHOIS_DN = "whois.arin.net";
	public static String CHARSET_NAME = "8859_1";

	public String whois(String IP) {
		String serverName = System.getProperty(WHOIS_SERV, WHOIS_DN);
		InetAddress server = null;
		InputStream in = null;
		Writer out = null;
		Socket theSocket = null;
		try {
			server = InetAddress.getByName(serverName);
			theSocket = new Socket(server, 43);
			out = new OutputStreamWriter(theSocket.getOutputStream(), CHARSET_NAME);

			// pass in the current host to the whois
			out.write(IP + "\r\n");
			out.flush();

			in = new BufferedInputStream(theSocket.getInputStream());
			int c;
			if (in == null) {
				return null;
			}
			StringBuffer response = new StringBuffer();
			while ((c = in.read()) != -1) {
				response.append((char) c);
			}
			return filterWhoisForDomainName(response.toString());

		} catch (UnknownHostException e) {

			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (in != null) {
				try {

					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (theSocket != null) {
				try {
					theSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// Domain resolved using a linux based system call to dig. Returns a domain name

	public String digg(String IP) {
		if (IP == null) {
			return null;
		}
		BufferedReader br = null;
		java.lang.ProcessBuilder processBuilder = new java.lang.ProcessBuilder("dig", "-x", IP, "+short");

		Process process;
		String output = "UNKNOWN";
		try {
			process = processBuilder.start();
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			output = br.readLine();
			br.close();
		} catch (IOException e) {
			throw new RuntimeException("Dig command line call has failed", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// Filter raw ips
		if (output != null && (output.equals(IP + ".") || output.equals(IP))) {
			return null;
		}
		return processDomainFromResolvedIP(output);
	}

	// Filtering and trimming top level domains based on artifacts found after
	// resolution calls

	private String processDomainFromResolvedIP(String dugDNS) {
		if (dugDNS == null || dugDNS.equals(""))
			return "UNKNOWN";
		String tld = dugDNS.substring(0, dugDNS.lastIndexOf("."));
		tld = tld.substring(tld.lastIndexOf(".") + 1, tld.length());
		return tld;
	}

	private String filterWhoisForDomainName(String response) {

		String regexEmail = "OrgTechEmail:  \\S+@\\S+\\.(\\S+)";
		Pattern pEmail = Pattern.compile(regexEmail, Pattern.DOTALL);

		String domainTemp = "UNKNOWN";
		Matcher m = pEmail.matcher(response);
		if (m.find()) {
			// extract the domain and set it
			try {
				domainTemp = m.group(1);
			} catch (Exception e) {
				throw new RuntimeException("Parsing a whois pattern has failed", e);
			}
		}
		return domainTemp;
	}

}
