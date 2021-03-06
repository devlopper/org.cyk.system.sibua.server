package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitFromDestinationPersistence;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitFromDestination;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class AdministrativeUnitFromDestinationPersistenceImpl extends AbstractPersistenceEntityImpl<AdministrativeUnitFromDestination> implements AdministrativeUnitFromDestinationPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}