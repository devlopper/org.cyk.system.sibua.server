package org.cyk.system.sibua.server.business.impl;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.AdministrativeUnitActivityBusiness;
import org.cyk.system.sibua.server.persistence.api.AdministrativeUnitActivityPersistence;
import org.cyk.system.sibua.server.persistence.entities.AdministrativeUnitActivity;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class AdministrativeUnitActivityBusinessImpl extends AbstractBusinessEntityImpl<AdministrativeUnitActivity, AdministrativeUnitActivityPersistence> implements AdministrativeUnitActivityBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
