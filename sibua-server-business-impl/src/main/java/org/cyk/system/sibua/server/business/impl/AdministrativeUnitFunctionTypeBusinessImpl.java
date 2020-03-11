package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.AdministrativeUnitFunctionTypeBusiness;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitFunctionTypePersistence;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitFunctionType;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class AdministrativeUnitFunctionTypeBusinessImpl extends AbstractBusinessEntityImpl<AdministrativeUnitFunctionType, AdministrativeUnitFunctionTypePersistence> implements AdministrativeUnitFunctionTypeBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
