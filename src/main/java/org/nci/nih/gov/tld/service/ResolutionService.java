package org.nci.nih.gov.tld.service;


import org.nci.nih.gov.tld.model.Domain;
import org.nci.nih.gov.tld.repo.DomainRepository;
import org.nci.nih.gov.tld.util.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResolutionService {
	
	public static final String UNKNOWN = "UNKNOWN";
	
	@Autowired
	DomainRepository repository;
	
	public String resolveFromStoredDomains(String ipFragment) {
		
		//Check persistent lookup for  domain value
		
		return null;
	}
	
	public Domain resolveFreshDomain(String ip) {
		
		Domain domain = new Domain();
		ResolverUtil util = new ResolverUtil();
	
		//Try digg
		String tld = util.digg(ip);
		
		if (tld == null)
		{util.digg(ip);}
		if (tld == null || tld == UNKNOWN) {
		 tld = util.whois(ip);
		}
		if (tld == null) {return null;}

		domain.setIp(ip);
		domain.setTld(tld);
		repository.save(domain);
		if(tld == UNKNOWN) {return null;}
		return domain;
	}

	public Domain getDomain(String ip) {
		Domain domain = repository.findByIp(ip);
		if(domain == null) {
			return resolveFreshDomain(ip);
		}
		else if(domain.getTld() == UNKNOWN)
		{return null;}
		return domain;
	}

}
