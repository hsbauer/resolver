package org.nci.nih.gov.tld.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.nci.nih.gov.tld.model.Domain;

public class ProcessLog {

	public Stream<String> readLogLine(String fileName) {
		
	    Path path;
	    Stream<String> lines = null;
		try {
			path = Paths.get(fileName);
		    lines = Files.lines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return lines;
	}

	public Domain mapLogLineToConsolidated(Hashtable<String, Domain> candidates, String logLine) {
			   ProcessLog processor = new ProcessLog();
			   Domain consolidated = new Domain();
			   String ip = getIpFromLine(logLine);
			   
			   if(consolidated.getIp().equals("NOIP")) {return makeEmptyDomain();}

			  else 
			   { 
				  consolidated.setIp(processor.getAndFilterLineIps(logLine));
				  candidates.put(consolidated.getIp(), consolidated);}
			   return consolidated;
		   }

	private Domain makeEmptyDomain() {
		// TODO Auto-generated method stub
		return null;
	}

	private String getAndFilterLineIps(String line) {

	    	if(line == null 
	    			|| line.equals("") 
	    			|| isBot(line) 
	    			|| isHealthPing(line))
	    				{ return "NOIP";}
	    	return getIpFromLine(line);
	    }
	
	
    // Filter for health checker ping
    private boolean isHealthPing(String domain) {
    	if(domain.contains("ELB-HealthChecker") || domain.contains("nagios") || domain.contains("bitdiscovery")) { return true;}
    	return false;
	}
    
	// Filter for google bot entries in log.
    public boolean isBot(String domain) {
    	if(domain.contains("Googlebot") || domain.contains("bingbot") 
    			|| domain.contains("qwant") || domain.contains("Nimbostratus-Bot") 
    			|| domain.contains("CensysInspect") || domain.contains("l9explore") || domain.contains("bitdiscovery")
    			|| domain.contains("l9tcpid") || domain.contains("302"))
    	{System.out.println("Removing bot reference"); return true;}
    	return false;
    }
    
    private String getIpFromLine(String line) {
    	String ip = null;
        final String regex = 
        		"^([\\d\\/.]+)";
   
        final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(line);
		if(matcher.find()) {
			ip = matcher.group(1);
		}
    	return ip;
    }



}
