package org.nci.nih.gov.tld.repo;

import org.nci.nih.gov.tld.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<Domain, Long> {

	Domain findByIp(String ip);

}
