/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/URI_Resolver/LICENSE.txt for details.
*/
package org.nci.nih.gov.tld.model;

import java.util.List;

public class UriResourceNames {
	private String resourceType;
	private List<String> resourceNames;
	
	
	public String getResourceType() {
		return resourceType;
	}
	
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
	public List<String> getResourceNames() {
		return resourceNames;
	}
	
	public void setResourceNames(List<String> resourceNames) {
		this.resourceNames = resourceNames;
	}

}
