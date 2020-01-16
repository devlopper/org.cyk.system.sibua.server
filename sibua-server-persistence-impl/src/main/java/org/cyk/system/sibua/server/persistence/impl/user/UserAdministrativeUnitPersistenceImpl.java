package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.user.UserAdministrativeUnitPersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserAdministrativeUnit;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class UserAdministrativeUnitPersistenceImpl extends AbstractPersistenceEntityImpl<UserAdministrativeUnit> implements UserAdministrativeUnitPersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}