package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityTypePersistence;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivityType;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class AdministrativeUnitActivityTypePersistenceImpl extends AbstractPersistenceEntityImpl<AdministrativeUnitActivityType> implements AdministrativeUnitActivityTypePersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}