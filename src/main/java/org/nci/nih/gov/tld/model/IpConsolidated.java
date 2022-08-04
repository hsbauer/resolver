package org.nci.nih.gov.tld.model;

import java.util.Date;

public class IpConsolidated {
	
		public IpConsolidated(String ip, String tmstp, long size, long duration, Date day) {
   			this.ip = ip;
   			this.tmstp = tmstp;
   			this.size = size;
   			this.duration = duration;
   			this.day = day;
   		}
  		
   		public IpConsolidated() {
		}

		String ip;
   		String tmstp;
   		long size;
   		long duration;
   		Date day;

}
