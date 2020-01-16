package org.cyk.system.sibua.server.persistence.impl.user;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.system.sibua.server.persistence.api.user.UserTypePersistence;
import org.cyk.system.sibua.server.persistence.entities.user.UserType;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;

@ApplicationScoped
public class UserTypePersistenceImpl extends AbstractPersistenceEntityImpl<UserType> implements UserTypePersistence,Serializable {
	private static final long serialVersionUID = 1L;

	
}