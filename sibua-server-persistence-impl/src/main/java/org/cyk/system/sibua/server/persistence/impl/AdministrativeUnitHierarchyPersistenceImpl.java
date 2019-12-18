package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitHierarchyPersistence;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitHierarchy;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class AdministrativeUnitHierarchyPersistenceImpl extends AbstractPersistenceEntityImpl<AdministrativeUnitHierarchy> implements AdministrativeUnitHierarchyPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}