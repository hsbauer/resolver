package org.nci.nih.gov.tld.service;


import org.nci.nih.gov.tld.model.Domain;
import org.nci.nih.gov.tld.repo.DomainRepository;
import org.nci.nih.gov.tld.util.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResolutionService {
	

	
	@Autowired
	DomainRepository repository;

	
	public Domain resolveFreshDomain(String ip) {
		//ip null check
		if(ip == null) {return null;}
		
		Domain domain = new Domain();
		ResolverUtil util = new ResolverUtil();
	
		//Try digg
		String tld = util.digg(ip);

		//digg can't find an ip, try whois
		if (tld == null || tld == ResolverUtil.UNKNOWN_TLD) {
		 tld = util.whois(ip);
		}

		//Save new domain object to database
		domain.setIp(ip);
		domain.setTld(tld);
		repository.save(domain);
		
		//If we can't find it, Don't send a value to the controller, let it be empty
		if(tld == ResolverUtil.UNKNOWN_TLD) {return null;}
		return domain;
	}

	public Domain getDomain(String ip) {
		//check the database first
		Domain domain = repository.findByIp(ip);
		//else resolve from services
		if(domain == null) {
			return resolveFreshDomain(ip);
		}
	    //return no value to the controller if we can't find domain
		else if(domain.getTld().equals(ResolverUtil.UNKNOWN_TLD))
		{return null;}
		
		return domain;
	}

}
