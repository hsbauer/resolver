package org.nci.nih.gov.tld.controller;

import java.util.Optional;

import org.nci.nih.gov.tld.model.Domain;
import org.nci.nih.gov.tld.repo.DomainRepository;
import org.nci.nih.gov.tld.service.ResolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ResolutionController {
	
	@Autowired
	ResolutionService service;
	
	@Autowired
	DomainRepository repository;
	
	  @GetMapping("/domain/{ip}")
	  Domain getDomain(@PathVariable String ip) {
		Domain domain = repository.findByIp(ip);
		if(domain == null ) {return service.getDomain(ip);}
		else {
	    return domain;
		}
	  }
	  
	  @GetMapping("/domainid/{id}")
	  String findById(@PathVariable long id) {
		Optional<Domain> domain = repository.findById(id);
	    return domain.get().getTld();
	  }
	

}
