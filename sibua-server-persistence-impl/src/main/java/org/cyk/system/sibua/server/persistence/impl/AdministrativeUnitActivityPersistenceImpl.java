package org.cyk.system.sibua.server.persistence.impl;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class AdministrativeUnitActivityPersistenceImpl extends AbstractPersistenceEntityImpl<AdministrativeUnitActivity> implements AdministrativeUnitActivityPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}