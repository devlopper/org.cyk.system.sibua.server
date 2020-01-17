package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.AdministrativeUnitActivityTypeBusiness;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityTypePersistence;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivityType;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class AdministrativeUnitActivityTypeBusinessImpl extends AbstractBusinessEntityImpl<AdministrativeUnitActivityType, AdministrativeUnitActivityTypePersistence> implements AdministrativeUnitActivityTypeBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
