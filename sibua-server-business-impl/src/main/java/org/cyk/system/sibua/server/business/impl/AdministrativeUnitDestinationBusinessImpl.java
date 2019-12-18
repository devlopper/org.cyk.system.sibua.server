package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.AdministrativeUnitDestinationBusiness;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitDestinationPersistence;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitDestination;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class AdministrativeUnitDestinationBusinessImpl extends AbstractBusinessEntityImpl<AdministrativeUnitDestination, AdministrativeUnitDestinationPersistence> implements AdministrativeUnitDestinationBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
