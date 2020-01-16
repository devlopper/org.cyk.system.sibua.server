package org.cyk.system.sibua.server.business.impl.user;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.business.api.user.UserAdministrativeUnitBusiness;
import org.cyk.system.sibua.server.persistence.api.user.UserAdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserAdministrativeUnit;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;

@ApplicationScoped
public class UserAdministrativeUnitBusinessImpl extends AbstractBusinessEntityImpl<UserAdministrativeUnit, UserAdministrativeUnitPersistence> implements UserAdministrativeUnitBusiness,Serializable {
	private static final long serialVersionUID = 1L;
		
}
