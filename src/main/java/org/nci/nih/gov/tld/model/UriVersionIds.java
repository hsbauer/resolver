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


public class UriVersionIds {
	private String resourceType;
	private String resourceName;
	private List<String> versionIds;
	public String getResourceType() {
		return resourceType;
	}
	public String getResourceName() {
		return resourceName;
	}
	public List<String> getVersionIds() {
		return versionIds;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public void setVersionIds(List<String> versionIds) {
		this.versionIds = versionIds;
	}

}
