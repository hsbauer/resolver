package org.nci.nih.gov.tld.domain.loader;

import java.util.Hashtable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.stream.Stream;

import org.nci.nih.gov.tld.model.Domain;
import org.nci.nih.gov.tld.model.IpConsolidated;
import org.nci.nih.gov.tld.util.ProcessLog;


public class DomainLoader {
	
	final BlockingQueue<IpConsolidated> queue = new LinkedBlockingQueue<>(100);

	ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

	public static void main(String[] args) {
		new DomainLoader().run(args);

	}
	
	public void run(String ... args){
		String accessPath = "/Users/bauerhs/Desktop/access.log";
		
//		String accessPath = "/Users/bauerhs/git/evs-domain-resolve/src/main/java/nci/nih/gov/evs/ipconverter/access.sample.log";
		ProcessLog processor = new ProcessLog();

		Stream<String> lines = processor.readLogLine(args.length == 0?accessPath:args[0]);

		Hashtable<String, Domain> consolidated = new Hashtable<String, Domain>();
		lines.forEach(x -> processor.mapLogLineToConsolidated(consolidated, x));

			consolidated.values()
				.stream()
				.map(x -> blockingTimedQuery(x))
				.forEach(y -> writeToDb(y));
           scheduler.shutdown();
		
	}

	private void writeToDb(Object y) {
		// Query db
		//if not there write to db
		
	}

	private Object blockingTimedQuery(Domain x) {
		// TODO Auto-generated method stub
		return null;
	}

}
