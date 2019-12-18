package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.AdministrativeUnitHierarchyBusiness;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitHierarchyPersistence;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitHierarchy;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class AdministrativeUnitHierarchyBusinessImpl extends AbstractBusinessEntityImpl<AdministrativeUnitHierarchy, AdministrativeUnitHierarchyPersistence> implements AdministrativeUnitHierarchyBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
